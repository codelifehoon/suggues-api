package somun.service.repository.content;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import somun.common.biz.Codes;
import somun.service.repository.vo.content.EventContent;

public interface EventContentRepository extends CrudRepository<EventContent,Integer> , PagingAndSortingRepository<EventContent,Integer> {



    List<EventContent> findByStat(Codes.EV_STAT stat);
    Page<EventContent> findByStatAndEventEndGreaterThanEqual(Codes.EV_STAT stat, Pageable pageable, Date eventEnd);
    Page<EventContent> findByStatAndEventStartLessThanEqualAndEventEndGreaterThanEqual(Codes.EV_STAT stat,Date date,Date date2, Pageable pageable);

    List<EventContent> findByEventContentNoIn(List<Integer> eventContentNo,Codes.EV_STAT stat);

    @Query(value = "select * , match(a.event_desc_text) against( :eventDesc) as score "
                    + " from event_content  a where match(a.event_desc_text) against( :eventDesc) and a.stat =  :stat /* #pageable*/"
           // spring jpa bug..(https://stackoverflow.com/questions/38349930/spring-data-and-native-query-with-pagination)
           ,countQuery="select count(*) from event_content  a  where match(a.event_desc_text) against( :eventDesc) and a.stat =  :stat"
        ,nativeQuery = true
    )
    Page<EventContent> findAllContent(@Param("eventDesc")String eventDesc,@Param("stat")String stat, Pageable pageable);

    @Query(value = "select * , match(a.event_desc_text) against( :eventDesc) as score "
        + " from event_content  a where match(a.event_desc_text) against( :eventDesc) and a.stat =  :stat /* #pageable*/"
        +" and (:eventDate is null or :eventDate  between a.event_start and a.event_end)"
           // spring jpa bug..(https://stackoverflow.com/questions/38349930/spring-data-and-native-query-with-pagination)
        ,countQuery="select count(*) from event_content  a  where match(a.event_desc_text) against( :eventDesc) and a.stat =  :stat"
        +" and (:eventDate is null or :eventDate  between a.event_start and a.event_end)"
        ,nativeQuery = true
    )
    Page<EventContent> findAllContent(@Param("eventDesc")String eventDesc,@Param("stat")String stat,@Param("eventDate")Date eventDate, Pageable pageable);

    // index 생성을 위한 쿼리
    Page<EventContent> findAllByUpdateDtGreaterThanEqualAndUpdateDtLessThanEqualAndStat(Date  indexStartDate, Date  indexEndDate, Codes.EV_STAT stat, Pageable pageable);


    // provider에서 제공한 content를 찾는쿼리
    EventContent findByRefContentKeyAndStatIn(String providerKey, Codes.EV_STAT s2);




}
