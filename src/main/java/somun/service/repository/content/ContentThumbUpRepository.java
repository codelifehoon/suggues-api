package somun.service.repository.content;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ContentThumbUpRepository extends CrudRepository<ContentThumbUp,Integer>{

    ContentThumbUp findFirstByEventContentNoAndUseYnAndUserNo(Integer eventContentNo, String y, Integer userNo);
    List<ContentThumbUp> findByEventContentNoInAndUseYn(List<Integer> eventContentNo, String y);
    List<ContentThumbUp> findByEventContentNoInAndUseYn(Integer[] eventContentNo, String y);

}
