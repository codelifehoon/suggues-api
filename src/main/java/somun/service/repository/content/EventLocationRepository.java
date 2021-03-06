package somun.service.repository.content;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import somun.service.repository.vo.content.EventLocation;

public interface EventLocationRepository extends CrudRepository<EventLocation,Integer>{


    public List<EventLocation> findByEventContentNoAndUseYn(Integer eventContentNo, String useYn);
    public List<EventLocation> findByEventContentNoInAndUseYn(List<Integer> eventContentNo,String useYn);

}
