package somun.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventLocationRepository extends CrudRepository<EventLocation,Integer>{

    public List<EventLocation> findByEventContentNoAndUseYn(Integer eventContentNo, String useYn);
    public List<EventLocation> findByEventContentNoAndUseYn(List<Integer> eventContentNo,String useYn);


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
