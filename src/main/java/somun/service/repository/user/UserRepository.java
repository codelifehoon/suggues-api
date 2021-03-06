package somun.service.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import somun.common.biz.Codes;
import somun.service.repository.vo.user.User;
import somun.service.repository.vo.user.UserForLogin;

public interface UserRepository extends CrudRepository<User,Integer> , PagingAndSortingRepository<User,Integer> {

    // 특정 필드를 조회 대상에서 제외를 위해서 별도의 inetrface를 전달 가능함
    public UserForLogin findByUserNoAndUserHash(Integer userNo, String userHash);

    public User findByUserNo(Integer userNo);

    public List<User> findByUserNoIn(List<Integer> userNo);
    public List<User> findByUserNoInAndUserStat(int[] userNo, Codes.USER_STAT userStat);

    public List<User> findByUserNmStartingWith(String userNm);
    public List<User> findByUserNmEndingWith(String userNm);
    public List<User> findByUserNmIsContaining(String userNm);
    public User findByUserHash(String userHash);


    public List<User> findTop10ByUserNoGreaterThan(int usrNo);

    public User findByUserIdAndUserProviderAndUserStat(String userId , String userProvider, Codes.USER_STAT userStat);




    @Query("select u from User u where u.userNm = ?1")
    public List<User> queryUserNm(String userNm);

    @Query("select u from User u where u.userNo = ?1")
    public List<User> queryUserNo(int userNo);

    @Query("select u from User u where u.userNm like ?1%")
    public List<User> queryUserNmEnd(String userNm);

}