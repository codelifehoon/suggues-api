package somun.service.repository.content;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import somun.common.biz.Codes;
import somun.service.repository.vo.content.ContentComment;

public interface ContentCommentRepository extends CrudRepository<ContentComment,Integer>{

    Integer countByEventContentNoAndStat(Integer eventContentNo, Codes.EV_STAT s2);

    List<ContentComment> findByEventContentNoAndStatOrderByCreateDtDesc(Integer eventContentNo, Codes.EV_STAT s2);
    ContentComment findByContentCommentNoAndStat(Integer contentCommentNo, Codes.EV_STAT s2);



}
