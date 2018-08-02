package webpush.push;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Security;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.springframework.test.annotation.Commit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Utils;


@Slf4j
public class WebPushTest {

/*
deleted firebase project

<script src="https://www.gstatic.com/firebasejs/5.0.4/firebase.js"></script>
    <script>
        // Initialize Firebase
        var config = {
        apiKey: "AIzaSyACCCHNoEhWS7c9fVXoo2QE9qB2XlwlM-E",
    authDomain: "webpushtest-6f991.firebaseapp.com",
    databaseURL: "https://webpushtest-6f991.firebaseio.com",
    projectId: "webpushtest-6f991",
    storageBucket: "webpushtest-6f991.appspot.com",
    messagingSenderId: "525066360507"
};
  firebase.initializeApp(config);
</script>
*/

    String m_userPublicKey = "BOOxNhKjrgoC5oefm7ewi8tTpCJVBRHAJ9BnzdVdxxGmi2bbdMURKZotDsZC6wymFesSVMpIaD8Am7GZ6O5iqo0";
    String m_userAuth = "j3F7QcldKGFglBtGTeh7pg==";

    // Base64 string server public/private key (gen: https://web-push-codelab.glitch.me/)
    // pushServer publicKey
    String m_vapidPublicKey = "";
    // pushServer privateKey
    String m_vapidPrivateKey = "";


    @Before
    public  void init()
    {

        log.debug("##### init()");
    }
    //public static void addSecurityProvider() { Security.addProvider(new BouncyCastleProvider()); }

    @Ignore
    public void testPushChromeVapid_old() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        String endpoint = "https://fcm.googleapis.com/fcm/send/fx0XVBJSswc:AP…J2LCijxJVGGdEBwwzo-fJNf3pxDI5PGrYLnxF8mEptiFXrHBP";

        // Base64 string user public key/auth
        String userPublicKey = m_userPublicKey;
        String userAuth = m_userAuth;

        // Base64 string server public/private key
        String vapidPublicKey = m_vapidPublicKey;
        String vapidPrivateKey = m_vapidPrivateKey;

        // Construct notificationz
        Notification notification = new Notification(endpoint, userPublicKey, userAuth, getPayload());

        // Construct push service
        PushService pushService = new PushService();
        pushService.setSubject("mailto:codelifehoon@gmail.com");
        pushService.setPublicKey(Utils.loadPublicKey(vapidPublicKey));
        pushService.setPrivateKey(Utils.loadPrivateKey(vapidPrivateKey));

        // Send notification!
        HttpResponse httpResponse = pushService.send(notification);
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));



    }


    /**
     * Some dummy payload (a JSON object)
     *
     * @return
     */
    private String getPayload() {


        JsonObject jsonObjectSub = new JsonObject();
        jsonObjectSub.addProperty("message","안녕하세요. 이것은 푸시 메세지 입니다  줄바꿈 되나요?");
        jsonObjectSub.addProperty("link","https://google.com");


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", " HI!");
        jsonObject.addProperty("body", jsonObjectSub.toString());
        jsonObject.addProperty("icon", "/images/icons/icon-144x144.png");
        jsonObject.addProperty("badge", "/images/icons/icon-144x144.png");


        return jsonObject.toString();
    }


    @Ignore
    public  void pushSend_devCode() throws IOException {
//        File path = new File(".");
//        System.out.println(path.getAbsolutePath());
/*

        String path = WebPushTest.class.getResource("").getPath(); // 현재 클래스의 절대 경로를 가져온다.
        System.out.println(path); //--> 절대 경로가 출력됨

        FileInputStream serviceAccount = new FileInputStream(path + "webpushtest-6f991-firebase-adminsdk-i0y3k-2b7989e939.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://webpushtest-6f991.firebaseio.com")
            .build();

        FirebaseApp.initializeApp(options);
        Subscription s = new Subscription();
        s.endpoint = "";
        s.keys.p256dh = "";
        s.keys.auth="";
*/

    }




    @Ignore
    @Commit
    public void webpushTestPush() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        Gson gson = new Gson();
        String vapidPublicKey = m_vapidPublicKey;
        String vapidPrivateKey = m_vapidPrivateKey;
//       file에서 json 읽기
//        https://stackoverflow.com/questions/29965764/how-to-parse-json-file-with-gson

        // Deserialize subscription object
        Subscription subscription = gson.fromJson(
            "{\"endpoint\":\"https://fcm.googleapis" +
                ".com/fcm/send/c1LeCqonfng:APA91bE25zo6Isg-StYKxxBadNEqxNFkcxYto8k17J_s8zL3USJjHrMFHu50XFrR_gKfi" +
                "-PIw5HTo5rd3AnOMuZpYNiKDryTpyREwWFkdWQJ_pbDmRHBAL-WIERsLI4LKXDuiRdtWCJO\",\"expirationTime\":null," +
                "\"keys\":{\"p256dh\":\"BEkVq-tUCzlU-9QjzyBWYkwiiBXHlLT9gni7a2kOAdger83HbX3tpNq-jC3aP5jeO0W1-nv0icmILUHAhPKAvf8\"," +
                "\"auth\":\"F9wA_qHmv0YpODLODOzp7g\"}}",
            Subscription.class
        );

        // Construct notification
        Notification notification = new Notification(
            subscription.endpoint,
            subscription.keys.p256dh,
            subscription.keys.auth,
            getPayload()
        );


        // Construct push service
//        KeyPair keyPair = TestUtils.readVapidKeys();

        PushService pushService = new PushService();
//        pushService.setKeyPair(keyPair);
        pushService.setPublicKey(Utils.loadPublicKey(vapidPublicKey));
        pushService.setPrivateKey(Utils.loadPrivateKey(vapidPrivateKey));
        pushService.setSubject("mailto:codelifehoon@gmail.com");

        // Send notification!
        HttpResponse httpResponse = pushService.send(notification);

        System.out.println(httpResponse.getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
    }



}
