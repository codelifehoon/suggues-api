package somun.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Utils;
import somun.common.biz.Codes;
import somun.service.repository.content.ContentAlarmRepository;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.function.PushHistoryRepository;
import somun.service.repository.function.PushSubscriptionRepository;
import somun.service.repository.user.UserRepository;
import somun.service.repository.vo.Payload;
import somun.service.repository.vo.content.ContentAlarm;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.function.PushHistory;
import somun.service.repository.vo.function.PushSubscription;
import somun.service.repository.vo.user.User;

@Slf4j
@Service
public class SendAlarmService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentAlarmRepository contentAlarmRepository;

    @Autowired
    private PushHistoryRepository pushHistoryRepository;

    @Autowired
    private PushSubscriptionRepository pushSubscriptionRepository;

    @Autowired
    private EventContentRepository eventContentRepository;


    // Base64 string server public/private key (gen: https://web-push-codelab.glitch.me/)
    // pushServer publicKey
    private  static final String vapidPublicKey = "BMRQd_C2NL8RDqrbxqHweX3g32j218yub56JjM8mE1A3I8jweO9MBBtfR65jHjhKrNOOeFhZx3bp2majGlN68qk";
    // pushServer privateKey
    private  static final String vapidPrivateKey = "knhJQfCDigLWb9k0GcWapwZPQRoHOylciRFNGZ8hR6g";

    private  static final String vapidAdmin = "mailto:codelifehoon@gmail.com";



    public void sendAlarmMassage() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date toDay = new Date();
        Date toDayPlus3 = sdf.parse(sdf.format(new Date(toDay.getTime() + (1000 * 60 * 60 * 24 * 3))));


        PageRequest pageable = new PageRequest(0, Integer.MAX_VALUE, new Sort(Sort.Direction.DESC, "eventContentNo")); //현재페이지, 조회할 페이지수, 정렬정보
        Page<EventContent> eventContents =  eventContentRepository.findByStatAndEventStartLessThanEqualAndEventEndGreaterThanEqual(Codes.EV_STAT.S2 , toDayPlus3, toDayPlus3, pageable);

        int[] contentNos = eventContents.getContent().stream().mapToInt(EventContent::getEventContentNo).toArray();


        List<ContentAlarm> contentAlarmList = contentAlarmRepository.findByEventContentNoInAndUseYn(contentNos, "Y");
        int[] alarmReqUserNos = contentAlarmList.stream().mapToInt(ContentAlarm::getUserNo).distinct().toArray();


        List<User> usersInfo = userRepository.findByUserNoInAndUserStat(alarmReqUserNos, Codes.USER_STAT.S1);
        List<PushSubscription> pushSubscriptions = pushSubscriptionRepository.findByUserNoInAndUseYn(alarmReqUserNos, "Y");

        Map<Integer, User> userMap = usersInfo.stream().collect(Collectors.toMap(User::getUserNo, Function.identity()));
        Map<Integer, PushSubscription> pushSubscriptionMap = pushSubscriptions.stream().collect(Collectors.toMap(PushSubscription::getUserNo,Function.identity()));



        long sendCount = contentAlarmList.stream()
                                     .filter(d->userMap.containsKey(d.getUserNo()) && pushSubscriptionMap.containsKey(d.getUserNo()))
                                     .map(d -> {

                                         Payload payload = Payload.builder()
                                                .title("TITLE")
                                                .message("comming soon!")
                                                .link("http://11st.co.kr")
                                                .build();
                                         PushSubscription pushSubscription = pushSubscriptionMap.get(d.getUserNo());
                                         Optional<HttpResponse> response = this.sendWebPush(pushSubscription, payload);

                                         String statusCode = response.map(hr -> String.valueOf(hr.getStatusLine().getStatusCode()))
                                                            .orElse("exception : getPushSubscriptionNo" + pushSubscription.getPushSubscriptionNo());


                                         String resultContent  = response.map(hr -> {
                                                                                         try {
                                                                                             return IOUtils.toString(hr.getEntity().getContent(), StandardCharsets.UTF_8);
                                                                                         } catch (IOException e) {
                                                                                             e.printStackTrace();
                                                                                             return e.getMessage();
                                                                                         }
                                                                                     })
                                                                      .orElse("");


                                         pushHistoryRepository.save(PushHistory.builder()
                                                                                   .eventContentNo(d.getEventContentNo())
                                                                                   .userNo(d.getUserNo())
                                                                                   .createNo(999)
                                                                                   .createDt(toDay)
                                                                                   .pushPayload(this.getPayload(payload))
                                                                                   .pushSubscriptionNo(pushSubscription.getPushSubscriptionNo())
                                                                                   .pushStatusCode(statusCode)
                                                                                   .pushResultContent(resultContent)
                                                                                   .build());


                                         return "201".equals(statusCode);
                                     }).filter(d->d).count();

        if (contentAlarmList.size() !=sendCount ){

            log.error("respect message count :%d , actually %d",contentAlarmList.size(),sendCount);
        }


    }



    /**
     * Some dummy payload (a JSON object)
     *
     * @return
     */
    private String getPayload(Payload payload) {


        JsonObject jsonObjectSub = new JsonObject();
        jsonObjectSub.addProperty("message",payload.getMessage());
        jsonObjectSub.addProperty("link",payload.getLink());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", payload.getTitle());
        jsonObject.addProperty("body", jsonObjectSub.toString());
        jsonObject.addProperty("icon", "/images/icons/icon-144x144.png");
        jsonObject.addProperty("badge", "/images/icons/icon-144x144.png");

        return jsonObject.toString();
    }

     private Optional<HttpResponse>  sendWebPush(PushSubscription pushSubscription,Payload payload)  {
        Security.addProvider(new BouncyCastleProvider());
         Optional<HttpResponse> httpResponse;

             // Deserialize subscription object
        Subscription subscription = ( new Gson()).fromJson(pushSubscription.getEndPoint(), Subscription.class );

        // Construct notification
         Notification notification = null;
         try {
             notification = new Notification(
                 subscription.endpoint,
                 subscription.keys.p256dh,
                 subscription.keys.auth,
                 getPayload(payload)
             );


         // Construct push service
//        KeyPair keyPair = TestUtils.readVapidKeys();

        PushService pushService = new PushService();
//        pushService.setKeyPair(keyPair);
        pushService.setPublicKey(Utils.loadPublicKey(this.vapidPublicKey));
        pushService.setPrivateKey(Utils.loadPrivateKey(this.vapidPrivateKey));
        pushService.setSubject(vapidAdmin);

        httpResponse = Optional.of(pushService.send(notification));
        System.out.println(httpResponse.get().getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(httpResponse.get().getEntity().getContent(), StandardCharsets.UTF_8));

        return httpResponse;
         }

         catch( IOException |ExecutionException | GeneralSecurityException | InterruptedException | JoseException e) {
             log.error("######" + pushSubscription.toString());
             e.printStackTrace();

             return Optional.empty();
         }
     }


}
