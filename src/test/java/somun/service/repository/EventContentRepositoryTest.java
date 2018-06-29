package somun.service.repository;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.biz.Codes;
import somun.common.util.DateUtils;
import somun.service.repository.content.EventContent;
import somun.service.repository.content.EventContentModifyRepository;
import somun.service.repository.content.EventContentRepository;
import somun.service.repositoryComb.EventContentWithUser;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EventContentRepositoryTest {

    @Autowired
    EventContentRepository eventContentRepository;
    @Autowired
    private EventContentModifyRepository eventContentModifyRepository;


    @Test
    public  void findByStat_with_pageable () {

        PageRequest pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "eventContentNo")); //현재페이지, 조회할 페이지수, 정렬정보

        Page<EventContent> listPage = eventContentRepository.findByStatAndEventEndGreaterThanEqual(Codes.EV_STAT.S2, pageable, DateUtils.addDayDate("yyyyMMdd", 0));


        Page<EventContentWithUser> eventContentWithUsers = listPage.map(d -> {
            return EventContentWithUser.builder()
                                       .eventContent(d)
                                       .build();
        });


        assertThat(eventContentWithUsers).isNotNull();
    }

    @Test
    public  void findAllContent_with_pageable () {

        PageRequest pageable = new PageRequest(1, 3); //현재페이지, 조회할 페이지수, 정렬정보

//        Page<EventContent> listPage = eventContentRepository.findAllContent("#입력해주세요#",Codes.EV_STAT.S2,pageable);
//        Page<EventContent> listPage = eventContentRepository.findAllContent("#입력해주세요#",pageable);

        Page<EventContent> listPage = eventContentRepository.findAllContent("혹등고래인",Codes.EV_STAT.S2.toString(),pageable);


        assertThat(listPage).isNotNull();

    }

    @Test
    public  void findByStatAndEventStartGreaterThanEqualAndEventEndLessThanEqual_with_pageable () {

        PageRequest pageable = new PageRequest(1, 3); //현재페이지, 조회할 페이지수, 정렬정보


        Page<EventContent> listPage = eventContentRepository.findByStatAndEventStartLessThanEqualAndEventEndGreaterThanEqual(Codes.EV_STAT.S2 , new Date(), new Date(), pageable);


        assertThat(listPage).isNotNull();

    }

    @Test
    public void updateStat(){

        eventContentModifyRepository.updateContentStat(EventContent.builder()
                                                            .eventContentNo(108)
                                                            .updateDt(new Date())
                                                            .updateNo(999)
                                                            .stat(Codes.EV_STAT.S4)
                                                            .build());
    }


    @Test
    @Commit
    public void dynamicUpdateContent(){

//        eventContentRepository.save(EventContent.builder()
//                                                             .eventContentNo(108)
//                                                             .updateDt(new Date())
//                                                             .updateNo(100)
//                                                             .stat(Codes.EV_STAT.S4)
//                                                             .build());
    }




}
