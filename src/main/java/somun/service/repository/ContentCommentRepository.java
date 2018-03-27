package somun.service.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentCommentRepository extends CrudRepository<ContentComment,Integer>{


    @Modifying
    @Query("UPDATE  ContentComment u SET " +
               "u.stat = :#{#contentComment.stat} " +
               ",u.commentDesc = :#{#contentComment.commentDesc} " +
               ",u.updateNo=:#{#contentComment.updateNo}" +
               ",u.updateDt=:#{#contentComment.updateDt}" +
               " WHERE u.contentCommentNo = :#{#contentComment.contentCommentNo}" +
               " and u.userNo = :#{#contentComment.userNo}"
               )
    Integer updateContentCommentMember(@Param("contentComment") ContentComment contentComment);


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
}
