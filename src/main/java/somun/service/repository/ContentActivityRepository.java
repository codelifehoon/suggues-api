package somun.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import somun.common.biz.Codes;

public interface ContentActivityRepository extends CrudRepository<ContentActivity,Integer>,
                                                   PagingAndSortingRepository<ContentActivity,Integer> {



    Page<ContentActivity> findByCreateNoAndActivityStatIn(Integer CreateNo ,Codes.ACTIVITY_STAT[] stats, Pageable pageable);
    Page<ContentActivity> findByCreateNoAndActivityStatInAndActivityCode(Integer CreateNo ,Codes.ACTIVITY_STAT[] stats,Codes.ACTIVITY_CODE activityCode, Pageable pageable);


    @Modifying
    @Query("UPDATE  ContentActivity u SET " +
               "u.activityStat = :#{#contentActivity.activityStat} " +
               ",u.updateNo=:#{#contentActivity.updateNo}" +
               ",u.updateDt=:#{#contentActivity.updateDt}" +
               " WHERE u.activityRefNo = :#{#contentActivity.activityRefNo}" +
               " and u.activityCode = :#{#contentActivity.activityCode}" +
               " and u.createNo = :#{#contentActivity.updateNo}"
    )
    Integer updateContentActivityStat(@Param("contentActivity") ContentActivity contentActivity);

}
