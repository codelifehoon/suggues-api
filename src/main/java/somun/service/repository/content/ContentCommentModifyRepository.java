package somun.service.repository.content;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentCommentModifyRepository extends CrudRepository<ContentComment,Integer>{


    @Modifying
    @Query("UPDATE  ContentComment u SET " +
               "u.commentDesc = :#{#contentComment.commentDesc} " +
               ",u.updateNo=:#{#contentComment.updateNo}" +
               ",u.updateDt=:#{#contentComment.updateDt}" +
               " WHERE u.contentCommentNo = :#{#contentComment.contentCommentNo}" +
               " and u.userNo = :#{#contentComment.userNo}"
               )
    Integer updateContentComment(@Param("contentComment") ContentComment contentComment);

/*
    @Modifying
    @Query("UPDATE  ContentComment u SET " +
               "u.stat = :#{#contentComment.stat} " +
               ",u.commentDesc = :#{#contentComment.commentDesc} " +
               ",u.updateNo=:#{#contentComment.updateNo}" +
               ",u.updateDt=:#{#contentComment.updateDt}" +
               " WHERE u.contentCommentNo = :#{#contentComment.contentCommentNo}" +
               " and u.commentPw = :#{#contentComment.commentPw}"
    )
    Integer updateContentCommentNotMember(@Param("contentComment") ContentComment contentComment);
*/

    @Modifying
    @Query("UPDATE  ContentComment u SET " +
               "u.stat = :#{#contentComment.stat} " +
               ",u.updateNo=:#{#contentComment.updateNo}" +
               ",u.updateDt=:#{#contentComment.updateDt}" +
               " WHERE u.contentCommentNo = :#{#contentComment.contentCommentNo}" +
               " and u.userNo = :#{#contentComment.userNo}"
    )
    Integer updateContentCommentStat(@Param("contentComment") ContentComment contentComment);


}
