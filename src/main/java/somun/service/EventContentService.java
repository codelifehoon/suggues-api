package somun.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import somun.common.biz.Codes;
import somun.common.util.JsoupExtends;
import somun.exception.APIServerException;
import somun.service.repository.content.ContentActivityModifyRepository;
import somun.service.repository.content.ContentStorageModifyRepository;
import somun.service.repository.content.EventContentModifyRepository;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.content.EventLocationModifyRepository;
import somun.service.repository.vo.content.ContentActivity;
import somun.service.repository.vo.content.ContentStorage;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.content.EventLocation;

@Service

public class EventContentService {

    @Autowired
    EventContentModifyRepository eventContentModifyRepository;

    @Autowired
    EventLocationModifyRepository eventLocationModifyRepository;

    @Autowired
    ContentActivityModifyRepository  contentActivityModifyRepository;

    @Autowired
    EventContentRepository eventContentRepository;

    @Autowired
    ContentStorageModifyRepository contentStorageModifyRepository;


    @Transactional
    public EventContent saveEventContent(EventContent eventContent) {

        eventContent.setEventDescText(JsoupExtends.text(eventContent.getEventDesc()));
        eventContent.setEventDescThumbnails(JsoupExtends.imagesTagJsonList(eventContent.getEventDesc()));

        EventContent save = eventContentModifyRepository.save(eventContent);
        eventContent.setEventContentNo(save.getEventContentNo());


        // location save
        List<EventLocation> locations = addContentLocation(eventContent);
        save.setEventLocations(locations);

        // image save
        List<ContentStorage> storages = addContentImage(eventContent);

        save.setContentStorages(storages);

        contentActivityModifyRepository.save(ContentActivity.builder()
                                            .activityRefNo(save.getEventContentNo())
                                            .activityCode(Codes.ACTIVITY_CODE.CONTENT)
                                            .activityStat(Codes.getActivityCode(save.getStat()))
                                            .createDt(eventContent.getUpdateDt())
                                            .createNo(eventContent.getUpdateNo())
                                            .build());

        return save;
    }

    @Transactional
    public EventContent updateEventContent(Integer eventContentNo, EventContent eventContent) {

        EventContent findContent = eventContentRepository.findOne(eventContentNo);
        if (!findContent.getCreateNo().equals(eventContent.getUpdateNo())) throw new APIServerException("it's not same user.");

        eventContent.setEventContentNo(findContent.getEventContentNo());
        eventContent.setUserHash(findContent.getUserHash());
        eventContent.setStat(findContent.getStat());
        eventContent.setCreateDt(findContent.getCreateDt());
        eventContent.setCreateNo(findContent.getCreateNo());
        eventContent.setEventDescText(JsoupExtends.text(eventContent.getEventDesc()));
        eventContent.setEventDescThumbnails(JsoupExtends.imagesTagJsonList(eventContent.getEventDesc()));
        eventContentModifyRepository.save(eventContent);


        List<EventLocation> locations = addContentLocation(eventContent);

        findContent.setEventLocations(locations);


        // old image list stat change
        contentStorageModifyRepository.updateContentStorageStat(ContentStorage.builder()
                                                                             .activityRefNo(eventContent.getEventContentNo())
                                                                             .activityCode(Codes.ACTIVITY_CODE.CONTENT)
                                                                              .storageCode(Codes.STORAGE_CODE.IMAGE)
                                                                              .stat(Codes.STORAGE_STAT.S4)
                                                                              .createNo(eventContent.getUpdateNo())
                                                                              .updateNo(eventContent.getUpdateNo())
                                                                              .updateDt(eventContent.getUpdateDt())
                                                                              .build());

        // add new image list
        List<ContentStorage> storages = addContentImage(eventContent);

        findContent.setContentStorages(storages);





        return findContent;
    }




    private List<EventLocation> addContentLocation(EventContent eventContent) {
        List<EventLocation> eventLocations = Optional.ofNullable(eventContent.getEventLocations())
                                                     .orElse(new ArrayList<>())
                                                     .stream().map(d -> {
                d.setEventContentNo(eventContent.getEventContentNo());
                d.setCreateDt(eventContent.getUpdateDt());
                d.setCreateNo(eventContent.getUpdateNo());
                return d;
            }).collect(Collectors.toList());
        List<EventLocation> locations = new ArrayList<>();
        eventLocationModifyRepository.save(eventLocations).forEach(locations::add);
        return locations;
    }

    private List<ContentStorage> addContentImage(EventContent eventContent) {
        List<ContentStorage> contentStorages = Optional.ofNullable(eventContent.getContentStorages())
                                                       .orElse(new ArrayList<>())
                                                       .stream().map(d -> {
                d.setActivityRefNo(eventContent.getEventContentNo());
                d.setActivityCode(Codes.ACTIVITY_CODE.CONTENT);
                d.setStorageCode(Codes.STORAGE_CODE.IMAGE);
                d.setStat(Codes.STORAGE_STAT.S2);
//                d.setStorageValue(d.getStorageValue());
                d.setCreateDt(eventContent.getUpdateDt());
                d.setCreateNo(eventContent.getUpdateNo());
                d.setUpdateDt(eventContent.getUpdateDt());
                d.setUpdateNo(eventContent.getUpdateNo());

                return d;
            }).collect(Collectors.toList());
        List<ContentStorage> storages = new ArrayList<>();
        contentStorageModifyRepository.save(contentStorages).forEach(storages::add);
        return storages;
    }


}
