package somun.service.repositoryClient;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.service.HandlebarsTemplateLoader;
import somun.common.util.DateUtils;
import somun.service.EventContentService;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.content.EventLocation;
import somun.service.repository.vo.provider.ContentProvider;
import somun.service.repositoryClient.visitkoreaTour.ProviderContetComb;
import somun.service.repositoryClient.visitkoreaTour.VisitKoreaContetComb;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.DetailCommon;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.Header;
import somun.service.repositoryClient.visitkoreaTour.extraImage.Body;
import somun.service.repositoryClient.visitkoreaTour.extraImage.ExtraImage;
import somun.service.repositoryClient.visitkoreaTour.extraImage.Item;
import somun.service.repositoryClient.visitkoreaTour.extraImage.Items;
import somun.service.repositoryClient.visitkoreaTour.introduceInfo.Introduce;

@Slf4j
@Service
public class ContentProviderVisitKoreaService extends  ContentProviderService{

    @Autowired
    EventContentRepository eventContentRepository;

    @Autowired
    EventContentService eventContentService;

    @Autowired
    HandlebarsTemplateLoader handlebarsTemplateLoader;


    @Override
    public ContentProvider contentProviderBuilder(ProviderContetComb d) {
        Date date = new Date();
        VisitKoreaContetComb v = (VisitKoreaContetComb)d;

        return ContentProvider.builder()
                       .provider(v.getContProv())
                       .providerKey(v.getProviderKey())
                       .providerModifiedtime(v.getItem().getModifiedtime().toString())
                       .contetComb(new Gson().toJson(v))
                       .stat(Codes.CONTPROV_STAT.S0)
                       .createDt(date)
                       .createNo(v.getContProv().getProvNumber())
                       .updateDt(date)
                       .updateNo(v.getContProv().getProvNumber())
                       .build();

    }


    @Override
    public EventContent mergeIntoProviderContetToEventContent(ContentProvider d)  {
        VisitKoreaContetComb vc = new Gson().fromJson(d.getContetComb(), VisitKoreaContetComb.class);

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
            log.error(" VisitKorea json error:" + d.toString());
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
        } catch (ParseException | NullPointerException e) { e.printStackTrace(); log.error(d.toString()); log.error("####################"); }

        if (eventContentResult.isPresent()){
            return eventContentService.updateEventContent(eventContentResult.get().getEventContentNo(), eventContent);
        }else{
            return eventContentService.saveEventContent(eventContent);
        }

    }

    private String generatorDesc(VisitKoreaContetComb vc){
//        Item introduceItem = vc.getIntroduce().getBody().getItems().getItem();
//        List<somun.service.repositoryClient.visitkoreaTour.repeatInfo.Item> repeatItemList = vc.getRepeat().getBody().getItems().getItem();
        List<Item> extraImageItemList = Optional.ofNullable(vc)
                                                .map(VisitKoreaContetComb::getExtraImage)
                                                .map(ExtraImage::getBody)
                                                .map(Body::getItems)
                                                .map(Items::getItem)
                                                .orElse(new ArrayList<>());
        somun.service.repositoryClient.visitkoreaTour.detailCommon.Item detailCommonItem = vc.getDetailCommon().getBody().getItems().getItem();


        HashMap<String, Object> map = new HashMap<>();

        map.put("OVERVIEW",detailCommonItem.getOverview());
        map.put("ADDR1",detailCommonItem.getAddr1());
        map.put("ADDR2",detailCommonItem.getAddr2());
        map.put("TEL",detailCommonItem.getTel());
        map.put("HOMEPAGE",detailCommonItem.getHomepage());
        map.put("FIRSTIMAGE1",detailCommonItem.getFirstimage());
        map.put("FIRSTIMAGE2",detailCommonItem.getFirstimage2());
        map.put("EXTRAIMAGEITEMLIST",extraImageItemList.stream().map(Item::getOriginimgurl).toArray());

        try {
            return handlebarsTemplateLoader.getTemplate("visitkorea")
                                           .apply(handlebarsTemplateLoader.getContext(map));
        } catch (IOException e) {
            e.printStackTrace();
            return  "";
        }

    }
}


