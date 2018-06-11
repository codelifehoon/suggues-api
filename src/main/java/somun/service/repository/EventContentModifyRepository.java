package somun.service.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface EventContentModifyRepository extends CrudRepository<EventContent,Integer> , PagingAndSortingRepository<EventContent,Integer> {


    @Modifying
    @Query("UPDATE  EventContent u SET " +
               "u.stat = :#{#content.stat} " +
               ",u.updateNo=:#{#content.updateNo}" +
               ",u.updateDt=:#{#content.updateDt}" +
               " WHERE u.eventContentNo = :#{#content.eventContentNo}" +
               " and u.createNo = :#{#content.updateNo}"
    )
    Integer updateContentStat(@Param("content") EventContent content);




}
