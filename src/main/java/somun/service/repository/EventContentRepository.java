package somun.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import somun.common.biz.Codes;

public interface EventContentRepository extends CrudRepository<EventContent,Integer> , PagingAndSortingRepository<EventContent,Integer> {



    List<EventContent> findByStat(Codes.EV_STAT stat);
    Page<EventContent> findByStat(Codes.EV_STAT stat, Pageable pageable);
    List<EventContent> findByEventContentNoIn(List<Integer> eventContentNo,Codes.EV_STAT stat);


}
