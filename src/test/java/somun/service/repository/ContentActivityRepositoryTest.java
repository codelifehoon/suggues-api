package somun.service.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.biz.Codes;
import somun.common.util.LogUtil;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class ContentActivityRepositoryTest {

    @Autowired
    ContentActivityRepository contentActivityRepository;

    @Test
    public void findAll(){
        new LogUtil().printObjectList(contentActivityRepository.findAll());
    }

    @Test
    public void searchContetActivity_write_me(){
        PageRequest pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "contentActivityNo")); //현재페이지, 조회할 페이지수, 정렬정보
        Codes.ACTIVITY_STAT[] stats = new Codes.ACTIVITY_STAT[] {Codes.ACTIVITY_STAT.S2 , Codes.ACTIVITY_STAT.Y};

        Page<ContentActivity> activityList = contentActivityRepository.findByCreateNoAndActivityStatIn(new Integer(30), stats
            , pageable);

        new LogUtil().printObjectList(activityList);
        assertThat(activityList).isNotNull();
    }

}

