package somun.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import somun.common.biz.Codes;

public interface UserRepository extends CrudRepository<User,Integer> , PagingAndSortingRepository<User,Integer> {

    public Iterable<User> findByUserNm(User user);
    public List<User> findByUserNm(String userNm);
    public User findByUserNo(Integer userNo);
    public List<User> findByUserNoIn(List<Integer> userNo);
    public List<User> findByUserNmStartingWith(String userNm);
    public List<User> findByUserNmEndingWith(String userNm);
    public List<User> findByUserNmIsContaining(String userNm);
    public User findByUserHash(String userHash);


//    public List<User> findTop10(Pageable pageable);
    public List<User> findTop10ByUserNoGreaterThan(int usrNo);
//    public Page<User> findAll(Pageable pageable);

    public User findByUserIdAndUserProviderAndUserStat(String userId , String userProvider, Codes.USER_STAT userStat);



    @Query("select u from User u where u.userNm = ?1")
    public List<User> queryUserNm(String userNm);

    @Query("select u from User u where u.userNo = ?1")
    public List<User> queryUserNo(int userNo);

    @Query("select u from User u where u.userNm like ?1%")
    public List<User> queryUserNmEnd(String userNm);

    @Modifying
    @Query("UPDATE User u SET u.userStat = :userStat WHERE u.userNo = :userNo")
    public Integer updateUserStat(@Param("userNo") Integer userNo ,@Param("userStat") Codes.USER_STAT userStat);


}