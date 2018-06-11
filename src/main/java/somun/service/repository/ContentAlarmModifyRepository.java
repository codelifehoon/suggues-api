package somun.service.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentAlarmModifyRepository extends CrudRepository<ContentAlarm,Integer>{

    @Modifying
    @Query("UPDATE  ContentAlarm u SET u.useYn = :useYn WHERE u.userNo = :userNo and u.contentAlarmNo = :contentAlarmNo")
    public Integer updateContentAlarmStat(@Param("contentAlarmNo") Integer contentAlarmNo,
                                          @Param("userNo") Integer userNo,
                                          @Param("useYn") String useYn);


    @Modifying
    @Query("UPDATE  ContentAlarm u SET u.useYn = :#{#contentAlarm.useYn} " +
               ", u.updateNo = :#{#contentAlarm.updateNo}" +
               ", u.updateDt = :#{#contentAlarm.updateDt}" +
               " WHERE u.userNo = :#{#contentAlarm.updateNo} and u.contentAlarmNo = :#{#contentAlarm.contentAlarmNo}")
    public Integer updateContentAlarmStat(@Param("contentAlarm") ContentAlarm contentAlarm);




}
