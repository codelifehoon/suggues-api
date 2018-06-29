package somun.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.util.DateUtils;
import somun.service.repository.content.EventContent;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.content.EventLocation;
import somun.service.repository.content.EventLocationRepository;
import somun.service.repository.provider.ContentProvider;
import somun.service.repositoryClient.visitkoreaTour.VisitKoreaContetComb;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.DetailCommon;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.Header;
import somun.service.repositoryClient.visitkoreaTour.extraImage.Body;
import somun.service.repositoryClient.visitkoreaTour.extraImage.ExtraImage;
import somun.service.repositoryClient.visitkoreaTour.extraImage.Items;
import somun.service.repositoryClient.visitkoreaTour.introduceInfo.Introduce;

@Slf4j
@Service
public class ContentProviderManagerService {

    @Autowired
    EventContentRepository eventContentRepository;

    @Autowired
    EventLocationRepository eventLocationRepository;

    @Autowired
    EventContentService eventContentService;



    public EventContent mergeIntoVisitKoreaContetToEventContent(ContentProvider d)  {
        VisitKoreaContetComb vc = new Gson().fromJson(d.getContetComb(),VisitKoreaContetComb.class);

        if (  !"0000".equals(Optional.ofNullable(vc)
                                     .map(VisitKoreaContetComb::getDetailCommon)
                                     .map(DetailCommon::getHeader)
                                     .map(Header::getResultCode)
                                     .orElse("NULL"))
            ||!"0000".equals(Optional.ofNullable(vc)
                                     .map(VisitKoreaContetComb::getIntroduce)
                                     .map(Introduce::getHeader)
                                     .map(somun.service.repositoryClient.visitkoreaTour.introduceInfo.Header::getResultCode)
                                     .orElse("NULL"))
            ) {
            log.error(" http error exists:" + d.toString());
            return null;
        }

        Optional<EventContent> eventContentResult = Optional.ofNullable(eventContentRepository.findByRefContentKeyAndStatIn(d.getProviderKey(),Codes.EV_STAT.S2));


        EventContent eventContent = EventContent.builder().build();


        try {
            Date today = new Date();
            Integer userNo = Codes.CONTPROV.visitkorea.getProvNumber();

            EventLocation eventLocation = EventLocation.builder().longitude(vc.getItem().getMapx())
                                                       .latitude(Double.valueOf(vc.getItem().getMapy()))
                                                       .address( "(" + vc.getItem().getZipcode() +")" + vc.getItem().getAddr1())
                                                       .addressDtls(vc.getItem().getAddr2())
                                                       .useYn("Y")
                                                       .createNo(userNo)
                                                       .createDt(today)
                                                       .updateNo(userNo)
                                                       .updateDt(today)
                                                       .build();

            eventContent = EventContent.builder().userHash("batch")
                                             .title(vc.getItem().getTitle())
                                             .refContentKey(d.getProviderKey())
                                             .eventDesc(generatorDesc(vc))
                                             .eventStart(DateUtils.parse(vc.getIntroduce().getBody().getItems().getItem().getEventstartdate().toString()))
                                             .eventEnd(DateUtils.parse(vc.getIntroduce().getBody().getItems().getItem().getEventenddate().toString()))
                                             .repeatKind(Codes.EV_REPKIND.M1)
                                             .refPath(vc.getDetailCommon().getBody().getItems().getItem().getHomepage())
                                             .eventLocations(Arrays.asList(eventLocation))
                                             .stat(Codes.EV_STAT.S2)
                                               .createNo(userNo)
                                               .createDt(today)
                                               .updateNo(userNo)
                                               .updateDt(today)
                                             .build();
        } catch (ParseException e) {e.printStackTrace();}

        if (eventContentResult.isPresent()){
            return eventContentService.updateEventContent(eventContentResult.get().getEventContentNo(), eventContent);
        }else{
            return eventContentService.saveEventContent(eventContent);
        }

    }

    public String generatorDesc(VisitKoreaContetComb vc ){
//        Item introduceItem = vc.getIntroduce().getBody().getItems().getItem();
//        List<somun.service.repositoryClient.visitkoreaTour.repeatInfo.Item> repeatItemList = vc.getRepeat().getBody().getItems().getItem();
        List<somun.service.repositoryClient.visitkoreaTour.extraImage.Item> extraImageItemList = Optional.ofNullable(vc)
                                                                                                .map(VisitKoreaContetComb::getExtraImage)
                                                                                                .map(ExtraImage::getBody)
                                                                                                .map(Body::getItems)
                                                                                                .map(Items::getItem)
                                                                                                .orElse(new ArrayList<>());
        somun.service.repositoryClient.visitkoreaTour.detailCommon.Item detailCommonItem = vc.getDetailCommon().getBody().getItems().getItem();

        /*
        String introduce = String.join(
            "<b>행사</b>"
            , "<br>주최자 정보 : ", introduceItem.getSponsor1()
            , "<br>주최자 연락처 : ", introduceItem.getSponsor1tel()
            , "<br>주관사 연락처 : ", introduceItem.getSponsor2tel()
            , "<br>행사시작일 : ", introduceItem.getEventstartdate().toString()
            , "<br>행사종료일 : ", introduceItem.getEventenddate().toString()
            , "<br>공연시간 : ", introduceItem.getPlaytime()
            , "<br>행사장소 : ", introduceItem.getEventplace()
            , "<br>관람소요시간 : ", introduceItem.getSpendtimefestival()
            , "<br>관람가능연령 : ", introduceItem.getAgelimit()

        );

        String repeat = repeatItemList.stream()
                                      .map(d -> String.join(d.getInfoname(), d.getInfotext()))
                                      .reduce("", (old, next) -> String.join(old, next));
*/


        String detailCommon = String.join(""
            ,"<b>개요</b>"
            , "<br> : ", detailCommonItem.getOverview()
            , "<br> 주소 : ", detailCommonItem.getAddr1() , detailCommonItem.getAddr2()
            , "<br> 전화번호: ", detailCommonItem.getTel()
            , "<br> 사이트: ", detailCommonItem.getHomepage());



        String extraImage = extraImageItemList.stream()
                                  .map(d -> String.join("","<br><img src='",d.getOriginimgurl(),"'><br>"))
                                  .reduce("", (old, next) -> String.join(old, next));


        String firstimage = !StringUtils.isEmpty(detailCommonItem.getFirstimage()) ? "<br><img src='" + detailCommonItem.getFirstimage() + "'><br>" : "" ;
        String firstimage2 = !StringUtils.isEmpty(detailCommonItem.getFirstimage2()) ? "<br><img src='" + detailCommonItem.getFirstimage2() + "'><br>" : "" ;

        return String.join(""
            ,detailCommon
            ,firstimage
            ,firstimage2
            ,extraImage);
    }
}



