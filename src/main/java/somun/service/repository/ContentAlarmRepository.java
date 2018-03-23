package somun.service.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentAlarmRepository extends CrudRepository<ContentAlarm,Integer>{

    @Modifying
    @Query("UPDATE  ContentAlarm u SET u.useYn = :useYn WHERE u.userNo = :userNo and u.contentAlarmNo = :contentAlarmNo")
    public Integer updateContentAlarmStat(@Param("contentAlarmNo") Integer contentAlarmNo , @Param("userNo") Integer userNo , @Param("useYn") String useYn );

}