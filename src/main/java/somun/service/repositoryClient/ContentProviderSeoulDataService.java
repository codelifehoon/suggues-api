package somun.service.repositoryClient;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.service.HandlebarsTemplateLoader;
import somun.common.util.DateUtils;
import somun.service.EventContentService;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.content.EventLocationRepository;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.content.EventLocation;
import somun.service.repository.vo.provider.ContentProvider;
import somun.service.repositoryClient.seoulData.RowItem;
import somun.service.repositoryClient.visitkoreaTour.ProviderContetComb;
import somun.service.repositoryClient.visitkoreaTour.SeoulDataContetComb;

@Slf4j
@Service
public class ContentProviderSeoulDataService extends ContentProviderService{


    @Autowired
    EventContentRepository eventContentRepository;

    @Autowired
    EventLocationRepository eventLocationRepository;

    @Autowired
    EventContentService eventContentService;

    @Autowired
    HandlebarsTemplateLoader handlebarsTemplateLoader;



    @Override
    public ContentProvider contentProviderBuilder(ProviderContetComb d) {
        Date date = new Date();
        SeoulDataContetComb v = (SeoulDataContetComb)d;

        return ContentProvider.builder()
                       .provider(v.getContProv())
                       .providerKey(v.getProviderKey())
                       .providerModifiedtime(v.getRowItem().getRCPTBGNDT())
                       .contetComb(new Gson().toJson(v))
                       .stat(Codes.CONTPROV_STAT.S0)
                       .createDt(date)
                       .createNo(v.getContProv().getProvNumber())
                       .updateDt(date)
                       .updateNo(v.getContProv().getProvNumber())
                       .build();
    }

    @Override
    public EventContent mergeIntoProviderContetToEventContent(ContentProvider d) {
        SeoulDataContetComb seoulDataContetComb = new Gson().fromJson(d.getContetComb(), SeoulDataContetComb.class);
        RowItem item = seoulDataContetComb.getRowItem();
        Optional<EventContent> eventContentResult = Optional.ofNullable(eventContentRepository.findByRefContentKeyAndStatIn(d.getProviderKey(),Codes.EV_STAT.S2));

        EventContent eventContent = EventContent.builder().build();


        try {
            Date today = new Date();
            Integer userNo = Codes.CONTPROV.seouldata.getProvNumber();


            EventLocation eventLocation = EventLocation.builder()
                                                       .latitude(Double.valueOf(StringUtils.isEmpty(item.getX()) ? "0" : item.getX()) )
                                                       .longitude(Double.valueOf(StringUtils.isEmpty(item.getY()) ? "0" : item.getY()))
                                                       .address("")
                                                       .addressDtls(item.getAREANM())
                                                       .useYn("Y")
                                                       .createNo(userNo)
                                                       .createDt(today)
                                                       .updateNo(userNo)
                                                       .updateDt(today)
                                                       .build();

            eventContent = EventContent.builder().userHash("batch")
                                       .title(item.getSVCNM())
                                       .refContentKey(d.getProviderKey())
                                       .eventDesc(generatorDesc(item))
                                       .eventStart(DateUtils.parse(item.getRCPTBGNDT()+"00"))
                                       .eventEnd(DateUtils.parse(item.getRCPTENDDT()+"00"))
                                       .repeatKind(Codes.EV_REPKIND.M1)
                                       .refPath(item.getSVCURL())
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

    private String generatorDesc(RowItem item){

        HashMap<String, String> map = new HashMap<>();



        map.put("SVCNM",item.getSVCNM());
        map.put("TELNO",item.getTELNO());
        map.put("SVCURL",item.getSVCURL());
        map.put("NOTICE",item.getNOTICE());
        map.put("DTLCONT",item.getDTLCONT());
        map.put("IMAGES",item.getIMGPATH());


        try {
            return handlebarsTemplateLoader.getTemplate("seouldata")
                                           .apply(handlebarsTemplateLoader.getContext(map));
        } catch (IOException e) {
            e.printStackTrace();
            return  "";
        }



    }
}
