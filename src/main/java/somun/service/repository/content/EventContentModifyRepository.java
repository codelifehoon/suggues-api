package somun.service.repository.content;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import somun.service.repository.vo.content.EventContent;

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
