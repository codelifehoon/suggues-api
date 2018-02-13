package somun.repository;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import somun.core.util.RandomUti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OptionalTest {

/*
    @Test
    public void optionalListTest(){

        List<User> allGuest = findAllGuest();
        allGuest.get(0).setAddress(Address.builder().addr1("서울시").build());

        List<User> userList = allGuest.stream().map(u -> {
            u.setAddress(
                    Optional.ofNullable(u.getAddress())
                            .orElse(Address.builder().build())
            );
            return u;
        }).collect(Collectors.toList());

        new LogUtil<User>().printObjectList(userList);

    }*/


    @Test
    public void optionalFilterTest(){
        User user = User.builder().build();
        user.setUserNo(101);

        Integer  userNo = Optional.ofNullable(user)
                .filter(u -> u.getUserNo() > 100)
                .map(User::getUserNo)
                .orElse(-1);

        log.debug("##### userNo:"+ userNo);
    }
/*

    @Test
    public void optionalTest()
    {
        List<User> allGuest = findAllGuest();

        User user = allGuest.get(0);

        String val = Optional.ofNullable(user)
                .map(o -> o.getAddress())
                .map(a -> a.getAddDetail())
                .orElse("empty");
        log.debug("#### val1:" + val);

        Address address = Optional.ofNullable(user)
                .map(o -> o.getAddress())
                .orElse(Address.builder().build());
        log.debug("#### val2:" + address.toString());


        User nullUser = null;
        nullUser = User.builder().build();
        nullUser.setAddress(Address.builder().build());

        String val3 = Optional.ofNullable(nullUser)
                .map(User::getAddress)
                .map(Address::getAddDetail)
                .orElse("empty");
        log.debug("#### val3:" + val3);



        nullUser = null;
        nullUser = User.builder().build();
        nullUser.setAddress(Address.builder().build());
        Optional<Address> address1 = Optional.ofNullable(
                Optional.ofNullable(nullUser)
                .map(o -> o.getAddress())
                .orElse(null)
        );
        log.debug("#### val4:" + address1.get());

    }
*/



    private List<User> findAllGuest() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            userList.add(User.builder().userNo(RandomUti.randomNumber(3)).userNm(RandomUti.randomString(10)).build());

        userList.add(User.builder().userNo(RandomUti.randomNumber(3)).userNm("HHH").build());
        userList.add(User.builder().userNo(RandomUti.randomNumber(3)).userNm("HHH").build());


        return userList;
    }
}

