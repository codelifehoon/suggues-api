package somun.service.repository.content;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EventLocationRepository extends CrudRepository<EventLocation,Integer>{

    public EventLocation findFirstByUseYnAndEventContentNo(String useYn, Integer eventContentNo );
    public List<EventLocation> findByEventContentNoAndUseYn(Integer eventContentNo, String useYn);
    public List<EventLocation> findByEventContentNoAndUseYn(List<Integer> eventContentNo,String useYn);

}
