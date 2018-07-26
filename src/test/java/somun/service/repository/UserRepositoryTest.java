package somun.service.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.biz.Codes;
import somun.common.util.LogUtil;
import somun.common.util.RandomUti;
import somun.service.repository.user.AddressRepository;
import somun.service.repository.user.UserRepository;
import somun.service.repository.vo.user.Address;
import somun.service.repository.vo.user.User;
import somun.service.repository.vo.user.UserReadOnly;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:application-context.xml")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    EntityManagerFactory emf;

  /*  @Test
    public void findByUserNoUpdate(){
        List<User> userList = userRepository.findByUserNo(2);
        userList.get(0).setUserDesc(RandomUti.randomString(10,"DESC_",null).getBytes());
        userList.get(0).getAddress().setAddDetail(RandomUti.randomString(10,"DET_",null));


        new LogUtil().printObjectList(userList);
    }
*/

    @Test
    public void findAllPageable(){
        Page<User> p = userRepository.findAll(new PageRequest(1, 5));
    }
    @Test
    public void findTop10(){
        new LogUtil().printObjectList(userRepository.findTop10ByUserNoGreaterThan(10));
    }

    @Test
    public void findByUserNo(){
        User userList = userRepository.findByUserNo(30);
        new LogUtil().printObject(userList);
    }

    @Test
    public void queryUserNo(){
        List<User> userList = userRepository.queryUserNo(30);
        new LogUtil().printObjectList(userList);
    }

    @Test
    public void queryUserNm(){
        List<User> userList = userRepository.queryUserNm("HCA");
        new LogUtil().printObjectList(userList);
    }


    @Test
    public void QueryUserNmLike(){

        new LogUtil().printObjectList(userRepository.findByUserNmStartingWith("user_nm1"));
        new LogUtil().printObjectList(userRepository.findByUserNmEndingWith("userunm11"));
        new LogUtil().printObjectList(userRepository.findByUserNmIsContaining("nm1"));

    }


    @Test
    public void findAll(){
        new LogUtil().printObjectList(userRepository.findAll());
    }

    @Test
    public void findOne(){
        new LogUtil().printObject(userRepository.findByUserNo(30));
    }


    @Test
    public void findAndUpdate() {
        Iterable<User> userRepositoryAll = userRepository.findAll();
        int cnt = 0;
        for (User u: userRepositoryAll) {

            cnt++;
            u.setCreateNo(cnt); // update 적용안됨
        }

    }


    @Test
    @Commit
    public void add() throws Exception {

//        1995-09-07T10:40:52Z
//
        Date date = new Date();

        User user = User.builder()
                .userId("userId")
                .userNm(RandomUti.randomString(3))
                .userDesc(RandomUti.randomString(10) )
                .createNo(RandomUti.randomNumber(3))
                .createDt(date)
                .userProvider("userProvider")
                .userHash(RandomUti.randomString(3))
                .userStat(Codes.USER_STAT.S9)
                .build();

        User user1 = userRepository.save(user);
        new LogUtil().printObject(user);

        // if (1==1) throw  new Exception("jjh_exption");

        Address address = Address.builder()
                                 .userNo(user1.getUserNo())
                                 .addr1(RandomUti.randomString(3))
                                 .add2(RandomUti.randomString(6))
                                 .build();


        address = addressRepository.save(address);
        new LogUtil().printObject(address);


//        MatcherAssert.assertThat(userList, Matchers.is(true));
    }

/*
    @Test
    @Commit
    public void EntityManagerFactoryTest() {

        EntityManager entityManager = emf.createEntityManager();
        User user = User.builder()
                .userId(RandomUti.randomString(10))
                .userNm(RandomUti.randomString(3))
                .userDesc(RandomUti.randomString(10).getBytes())
                .createNo(RandomUti.randomNumber(3))
                .createDt(new Date())
                .build();

        Address address = Address.builder()
                .addr1(RandomUti.randomString(3))
                .add2(RandomUti.randomString(6))
                .build();

        user.setAddress(address);

        entityManager.persist(user);
        //entityManager.flush();
    }
*/

    @Test
    public void findByUserIdAndUserProvider() {

        User user = User.builder()
                .userId("Curl")
                .userNm(RandomUti.randomString(3))
                .userDesc(RandomUti.randomString(10))
                .createNo(RandomUti.randomNumber(3))
                .createDt(new Date())
                .userProvider("Sunt")
                .build();

        user = Optional.ofNullable(
                    userRepository.findByUserIdAndUserProviderAndUserStat(user.getUserId(), user.getUserProvider(), Codes.USER_STAT.S1))
                .orElse(User.builder().build());

        new LogUtil().printObject(user);

    }

    @Test
    public void findByUserNoAndUserHash() {


        UserReadOnly byUserNoAndUserHash = userRepository.findByUserNoAndUserHash(4, "1074286626BOQZCKEPD");

        log.debug("#######################");
        new LogUtil().printObject(byUserNoAndUserHash);

    }




}

