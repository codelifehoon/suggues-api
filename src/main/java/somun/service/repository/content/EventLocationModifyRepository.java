package somun.service.repository.content;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import somun.service.repository.vo.content.EventLocation;

public interface EventLocationModifyRepository extends CrudRepository<EventLocation,Integer>{
    @Modifying
    @Query("UPDATE  EventLocation u SET " +
               "u.useYn = :#{#eventLocation.useYn} " +
               ",u.updateNo=:#{#eventLocation.updateNo}" +
               ",u.updateDt=:#{#eventLocation.updateDt}" +
               " WHERE u.eventContentNo = :#{#eventLocation.eventContentNo}" +
               " and u.createNo = :#{#eventLocation.createNo}"
    )
    Integer updateContentLocationStat(@Param("eventLocation") EventLocation eventLocation);
}
