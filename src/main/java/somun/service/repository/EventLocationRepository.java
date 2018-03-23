package somun.service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EventLocationRepository extends CrudRepository<EventLocation,Integer>{

    public List<EventLocation> findByEventContentNo(int eventContentNo);
}
