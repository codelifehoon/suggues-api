package somun.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.config.properties.SomunProperties;
import somun.service.repository.content.ContentAlarmRepository;
import somun.service.repository.content.ContentCommentRepository;
import somun.service.repository.content.ContentStorageRepository;
import somun.service.repository.content.ContentThumbUpRepository;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.content.EventLocationRepository;
import somun.service.repository.user.UserRepository;
import somun.service.repository.vo.EventContentWithUser;
import somun.service.repository.vo.WebCertInfo;
import somun.service.repository.vo.content.ContentAlarm;
import somun.service.repository.vo.content.ContentThumbUp;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.function.SearchIndexComb;
import somun.service.repository.vo.function.SearchIndexCombPage;
import somun.service.repository.vo.user.User;

@Slf4j
@Service
public class ContentSearchService {


    @Autowired
    EventContentRepository  eventContentRepository;

    @Autowired
    SomunProperties somunProperties;

    @Autowired
    ContentThumbUpRepository contentThumbUpRepository;

    @Autowired
    ContentAlarmRepository contentAlarmRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContentCommentRepository contentCommentRepository;

    @Autowired
    EventLocationRepository eventLocationRepository;

    @Autowired
    ContentStorageRepository contentStorageRepository;



    public Page<EventContentWithUser>  getIntergratSearchDetail(Pageable pageable, WebCertInfo webCertInfo, SearchIndexComb searchQuery) {

        Page<EventContent> eventContents  = searchTotalSearchIndex(pageable, searchQuery);

        return getEventContentAndThumbAndAlarmAndUser(webCertInfo, eventContents);
    }

    public Page<EventContentWithUser>  getIntergratSearchDefault(PageRequest defaultPageable,WebCertInfo webCertInfo,Date searchDate) {

        Page<EventContent> eventContentPage;
        eventContentPage = eventContentRepository.findByStatAndEventStartLessThanEqualAndEventEndGreaterThanEqual(Codes.EV_STAT.S2 , searchDate, searchDate, defaultPageable);
        return getEventContentAndThumbAndAlarmAndUser(webCertInfo, eventContentPage);

    }



    private Page<EventContentWithUser> getEventContentAndThumbAndAlarmAndUser(WebCertInfo webCertInfo, Page<EventContent> eventContentPage) {

        List<EventContent> eventContents = eventContentPage.getContent();
        List<Integer> eventContentNoList = eventContents.stream().map(EventContent::getEventContentNo).collect(Collectors.toList());
        List<Integer> userNos = eventContents.stream().map(EventContent::getCreateNo).collect(Collectors.toList());
        HashMap<Integer, ContentThumbUp> contentThumbUpHashMap = contentThumbUpRepository.findByEventContentNoInAndUseYn(eventContentNoList, "Y")
                                                                                         .stream()
                                                                                         .collect(Collectors.toMap(ContentThumbUp::getEventContentNo,
                                                                                                                   Function.identity(),
                                                                                                                   (o, n) -> o,
                                                                                                                   HashMap::new));
        HashMap<Integer, ContentAlarm> contentAlarmHashMap = contentAlarmRepository.findByEventContentNoInAndUseYn(eventContentNoList, "Y")
                                                                                   .stream()
                                                                                   .collect(Collectors.toMap(ContentAlarm::getEventContentNo,
                                                                                                             Function.identity(),
                                                                                                             (o, n) -> o,
                                                                                                             HashMap::new));
        HashMap<Integer, User> userHashMap = userRepository.findByUserNoIn(userNos)
                                                           .stream().collect(Collectors.toMap(User::getUserNo,
                                                                                              Function.identity(),
                                                                                              (o, n) -> o,
                                                                                              HashMap::new));
        // 1.binding VOs to EventContentWithUser
        // 2.convert  VOs to page Object
        return eventContentPage.map(d -> {
            EventContentWithUser eventContentWithUser = EventContentWithUser.builder()
                                                                            .eventContent(d)
                                                                            .contentThumbUp(Optional.ofNullable(contentThumbUpHashMap.get(d.getEventContentNo()))
                                                                                                    .orElse(ContentThumbUp.builder()
                                                                                                                          .build())
                                                                            )
                                                                            .contentAlarm(Optional.ofNullable(contentAlarmHashMap.get(d.getEventContentNo()))
                                                                                                  .orElse(ContentAlarm.builder()
                                                                                                                      .build())
                                                                            )
                                                                            .user(userHashMap.get(d.getCreateNo()))
                                                                            .commentCnt(contentCommentRepository.countByEventContentNoAndStat(d.getEventContentNo(),Codes.EV_STAT.S2))
                                                                            .isEqualLoginUser(webCertInfo.getUser().getUserNo() == d.getCreateNo())
                                                                            .build();
            //  do location-value each fetch if It can be a lot of value
            eventContentWithUser.getEventContent()
                                .setEventLocations(eventLocationRepository.findByEventContentNoAndUseYn(d.getEventContentNo(),"Y"));

            eventContentWithUser.getEventContent().setContentStorages(contentStorageRepository
                                     .findByActivityRefNoAndActivityCodeAndStorageCodeAndStatNot(d.getEventContentNo()
                                         ,Codes.ACTIVITY_CODE.CONTENT
                                         ,Codes.STORAGE_CODE.IMAGE
                                         ,Codes.STORAGE_STAT.S4));

            return eventContentWithUser;
        });
    }

    private Page<EventContent> searchTotalSearchIndex(Pageable pageable,SearchIndexComb searchQuery) {


        String url = somunProperties.getSearchApiServer() + String.format("/Engine/V1/SE/searchTotalSearchIndex?page=%d&size=%d"
            ,pageable.getPageNumber()
            ,pageable.getPageSize());


        SearchIndexCombPage body = new RestTemplate().exchange(url, HttpMethod.POST
                                                        , new HttpEntity(searchQuery, new HttpHeaders())
                                                        , SearchIndexCombPage.class).getBody();

        Page<SearchIndexComb> combs = new PageImpl<>(body.getContent(),
                                                     new PageRequest(pageable.getPageNumber(), pageable.getPageSize()),
                                                     body.getTotalElements());


        List<Integer> eventContentNoList = combs.getContent().stream().map(SearchIndexComb::getEventContentNo).collect(Collectors.toList());

        HashMap<Integer, EventContent> eventContentHashMap = eventContentRepository.findByEventContentNoInAndStat(eventContentNoList, Codes.EV_STAT.S2)
                                                                                   .stream().collect(Collectors.toMap(EventContent::getEventContentNo
                                                                                                ,Function.identity()
                                                                                                ,(o, n) -> o
                                                                                                ,HashMap::new));

        return  combs.map(d -> eventContentHashMap.get(d.getEventContentNo()));


    }

}
