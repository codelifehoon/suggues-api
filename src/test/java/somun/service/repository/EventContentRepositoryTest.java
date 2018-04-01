package somun.service.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.biz.Codes;
import somun.service.repositoryComb.EventContentWithUser;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EventContentRepositoryTest {

    @Autowired
    EventContentRepository eventContentRepository;

    @Test
    public  void findByStat_with_pageable () {

        PageRequest pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "eventContentNo")); //현재페이지, 조회할 페이지수, 정렬정보


        Page<EventContent> listPage = eventContentRepository.findByStat(Codes.EV_STAT.S2, pageable);


        Page<EventContentWithUser> eventContentWithUsers = listPage.map(d -> {
            return EventContentWithUser.builder()
                                       .eventContent(d)
                                       .build();
        });


        assertThat(eventContentWithUsers).isNotNull();
    }
}
