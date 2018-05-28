package somun.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentThumbUpRepository extends CrudRepository<ContentThumbUp,Integer>{

    @Modifying
    @Query("UPDATE  ContentThumbUp u SET u.useYn = :useYn WHERE u.userNo = :userNo and u.contentThumbupNo = :contentThumbupNo")
    Integer updateContentThumbUpStat(@Param("contentThumbupNo") Integer contentThumbupNo, @Param("userNo") Integer userNo, @Param("useYn") String useYn);
    

    ContentThumbUp findFirstByEventContentNoAndUseYnAndUserNo(Integer eventContentNo, String y, Integer userNo);
    List<ContentThumbUp> findByEventContentNoInAndUseYn(List<Integer> eventContentNo, String y);

}
