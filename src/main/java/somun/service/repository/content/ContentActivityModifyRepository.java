package somun.service.repository.content;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ContentActivityModifyRepository extends CrudRepository<ContentActivity,Integer>,
                                                         PagingAndSortingRepository<ContentActivity,Integer> {


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

