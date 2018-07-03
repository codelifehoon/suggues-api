package somun.service.repository.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import somun.common.biz.Codes;
import somun.service.repository.vo.user.User;

public interface UserModifyRepository extends CrudRepository<User,Integer> , PagingAndSortingRepository<User,Integer> {

    @Modifying
    @Query("UPDATE User u SET u.userStat = :userStat WHERE u.userNo = :userNo")
    public Integer updateUserStat(@Param("userNo") Integer userNo, @Param("userStat") Codes.USER_STAT userStat);


}