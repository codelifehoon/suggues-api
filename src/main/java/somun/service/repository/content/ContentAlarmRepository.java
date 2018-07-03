package somun.service.repository.content;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import somun.service.repository.vo.content.ContentAlarm;

public interface ContentAlarmRepository extends CrudRepository<ContentAlarm,Integer>{
    ContentAlarm findFirstByEventContentNoAndUseYnAndUserNo(Integer eventContentNo, String y, Integer userNo);
    List<ContentAlarm> findByEventContentNoInAndUseYn(List<Integer> eventContentNo, String y);
    List<ContentAlarm> findByEventContentNoInAndUseYn(int[] eventContentNo, String y);



}
