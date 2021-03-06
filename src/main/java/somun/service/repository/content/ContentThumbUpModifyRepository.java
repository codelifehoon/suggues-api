package somun.service.repository.content;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import somun.service.repository.vo.content.ContentThumbUp;

public interface ContentThumbUpModifyRepository extends CrudRepository<ContentThumbUp,Integer>{

    @Modifying
    @Query("UPDATE  ContentThumbUp u SET u.useYn = :useYn WHERE u.userNo = :userNo and u.contentThumbupNo = :contentThumbupNo")
    Integer updateContentThumbUpStat(@Param("contentThumbupNo") Integer contentThumbupNo,
                                     @Param("userNo") Integer userNo,
                                     @Param("useYn") String useYn);

}
