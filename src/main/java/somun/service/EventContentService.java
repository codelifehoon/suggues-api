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
import somun.service.repository.content.ContentActivity;
import somun.service.repository.content.ContentActivityModifyRepository;
import somun.service.repository.content.EventContent;
import somun.service.repository.content.EventContentModifyRepository;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.content.EventLocation;
import somun.service.repository.content.EventLocationModifyRepository;

@Service
@Transactional
public class EventContentService {

    @Autowired
    EventContentModifyRepository eventContentModifyRepository;

    @Autowired
    EventLocationModifyRepository eventLocationModifyRepository;

    @Autowired
    ContentActivityModifyRepository  contentActivityModifyRepository;

    @Autowired
    EventContentRepository eventContentRepository;

    public EventContent saveEventContent(EventContent eventContent) {

        eventContent.setEventDescText(JsoupExtends.text(eventContent.getEventDesc()));
        eventContent.setEventDescThumbnails(JsoupExtends.imagesTagJsonList(eventContent.getEventDesc()));

        EventContent save = eventContentModifyRepository.save(eventContent);
        List<EventLocation> eventLocations = Optional.ofNullable(eventContent.getEventLocations())
                                                     .orElse(new ArrayList<>())
                                                     .stream().map(d -> {
                                                                    d.setEventContentNo(save.getEventContentNo());
                                                                    d.setCreateDt(eventContent.getUpdateDt());
                                                                    d.setCreateNo(eventContent.getUpdateNo());
                                                                    return d;
                                                                })
                                                     .collect(Collectors.toList());
        List<EventLocation> locations = new ArrayList<>();
        eventLocationModifyRepository.save(eventLocations).forEach(locations::add);
        save.setEventLocations(locations);
        contentActivityModifyRepository.save(ContentActivity.builder()
                                            .activityRefNo(save.getEventContentNo())
                                            .activityCode(Codes.ACTIVITY_CODE.CONTENT)
                                            .activityStat(Codes.getActivityCode(save.getStat()))
                                            .createDt(eventContent.getUpdateDt())
                                            .createNo(eventContent.getUpdateNo())
                                            .build());

        return save;
    }

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
        List<EventLocation> eventLocations = Optional.ofNullable(eventContent.getEventLocations())
                                                     .orElse(new ArrayList<>())
                                                     .stream().map(d -> {
                d.setEventContentNo(eventContent.getEventContentNo());
                d.setCreateDt(eventContent.getUpdateDt());
                d.setCreateNo(eventContent.getUpdateNo());
                return d;
            }).collect(Collectors.toList());
        List<EventLocation> locations = new ArrayList<>();

        eventLocationModifyRepository.updateContentLocationStat(EventLocation.builder()
                                                                                                .eventContentNo(eventContent.getEventContentNo())
                                                                                                .createNo(eventContent.getUpdateNo())
                                                                                                .useYn("N")
                                                                                                .updateNo(eventContent.getUpdateNo())
                                                                                                .updateDt(eventContent.getUpdateDt())
                                                                                                .build());
        eventLocationModifyRepository.save(eventLocations).forEach(locations::add);
        findContent.setEventLocations(locations);

        return findContent;
    }
}
