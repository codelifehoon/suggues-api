package somun.service.repository.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import somun.common.biz.Codes;
import somun.service.repository.vo.content.ContentActivity;

public interface ContentActivityRepository extends CrudRepository<ContentActivity,Integer>,
                                                   PagingAndSortingRepository<ContentActivity,Integer> {



    Page<ContentActivity> findByCreateNoAndActivityStatIn(Integer CreateNo ,Codes.ACTIVITY_STAT[] stats, Pageable pageable);
    Page<ContentActivity> findByCreateNoAndActivityStatInAndActivityCode(Integer CreateNo ,Codes.ACTIVITY_STAT[] stats,Codes.ACTIVITY_CODE activityCode, Pageable pageable);

}
