package somun.service.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;

import lombok.extern.slf4j.Slf4j;
import somun.common.util.RandomUti;
import somun.service.repository.user.User;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:application-context.xml")
@Slf4j
public class JavaLamdaTest {

    public interface ResortService {
        public List<String> findGuestNamesByCompany(String company);
    }

    public class ResortServiceImpl implements ResortService {


        @Override
        public List<String> findGuestNamesByCompany(String company) {
            return findJava8(company);
        }


        private List<String> findJava8(String company) {
            List<User> all = findAllGuest();

            return all.stream().filter(u -> company.equals(u.getUserNm()))
                    .sorted(Comparator.comparing(u -> u.getUserNo()))
                    .map(g -> g.getUserNm())
                    .collect(Collectors.toList());


        }


        private List<String> findGuava(final String company) {
            List<User> all = findAllGuest();

            List<User> sorted = FluentIterable.from(all)
                    .filter(new Predicate<User>() {
                        @Override
                        public boolean apply(User input) {
                            return company.equals(input.getUserNm());
                        }
                    })
                    .toSortedList(Ordering.natural().onResultOf(new Function<User, Integer>() {
                        @Override
                        public Integer apply(User input) {
                            return input.getUserNo();
                        }
                    }));

            return FluentIterable.from(sorted)
                    .transform(new Function<User, String>() {
                        @Override
                        public String apply(User input) {
                            return input.getUserNm();
                        }
                    }).toList();

        }


        public List<String> findoldJava(String company) {
            List<User> all = findAllGuest();
            List<User> filtered = filter(all, company); // (A)
            sort(filtered); // (B)
            return mapNames(filtered); // (C)
        }


        // (A) company 속성값이 특정한 값과 일치하는 Guest 객체만 필터링
        private List<User> filter(List<User> guests, String company) {
            List<User> filtered = new ArrayList<>();
            for (User guest : guests) {
                if (company.equals(guest.getUserNm())) {
                    filtered.add(guest);
                }
            }
            return filtered;
        }

        // (B) grade 속성값을 기준으로 Guest 객체를 오름차순으로 정렬
        private void sort(List<User> guests) {
            Collections.sort(guests, new Comparator<User>() {
                public int compare(User o1, User o2) {
                    return Integer.compare(o1.getUserNo(), o1.getUserNo());
                }
            });
        }

        // (C) name 속성만 추출해 List<String>으로 변환
        private List<String> mapNames(List<User> guests) {
            List<String> names = new ArrayList<>();
            for (User guest : guests) {
                names.add(guest.getUserNm());
            }
            return names;
        }


        private List<User> findAllGuest() {
            List<User> userList = new ArrayList<>();
            for (int i = 0; i < 10; i++)
                userList.add(User.builder().userNo(RandomUti.randomNumber(3)).userNm(RandomUti.randomString(10)).build());

            userList.add(User.builder().userNo(RandomUti.randomNumber(3)).userNm("HHH").build());
            userList.add(User.builder().userNo(RandomUti.randomNumber(3)).userNm("HHH").build());


            return userList;
        }


    }


    @Test
    public void lamdaTest() {

        int x = 2;

//        (x)->{ System.out.println('a');};


        int counter = 0; // Free Variable

        new Thread(() -> System.out.println(counter)); // OK
        new Thread(() -> System.out.println("2323232")); // OK

        //User user = null;
        User user = User.builder().userNm("NotNull").build();
        Optional<User> optUser = Optional.ofNullable(user);
        String value =
                //optUser.map(User::getUserNm).orElse("NULL");
                optUser.map(User::getUserNm).orElse("NULL");

        //optUser.ifPresent(System.out::println);

        log.debug("##### value:" + value);

    }

    @Test
    public void lamdaRefactoringTest() {
        ResortService resortService = new ResortServiceImpl();
        List<String> names = resortService.findGuestNamesByCompany("HHH");

        log.debug("####" + names.toString());
        MatcherAssert.assertThat(names.size(), Matchers.is(2));


    }

    @Test
    public void codeTest() {

        String company = "HHH";
        ResortServiceImpl resortService = new ResortServiceImpl();
        List<User> all = resortService.findAllGuest();

        List<User> result;
        result = all.stream()
                .filter(u -> company.equals(u.getUserNm()))
                .collect(Collectors.toList());
        log.debug("####" + result.toString());

        result = all.stream().filter(u -> company.equals(u.getUserNm()))
                .sorted(Comparator.comparing(u -> u.getUserNo()))
                .collect(Collectors.toList());
        log.debug("####" + result.toString());


        result = all.stream().filter(u -> company.equals(u.getUserNm()))
                .sorted(Comparator.comparing(u -> u.getUserNo()))
                .map(u -> {
                    return User.builder()
                            .userNm(u.getUserNm())
                            .build();
                })
                .collect(Collectors.toList());
        log.debug("####" + result.toString());


        List<String> stringList = all.stream().filter(u -> company.equals(u.getUserNm()))
                .sorted(Comparator.comparing(u -> u.getUserNo()))
                .map(u -> u.getUserNm())
                .collect(Collectors.toList());
        log.debug("####" + stringList.toString());

        stringList = all.parallelStream().filter(u -> company.equals(u.getUserNm()))
                .sorted(Comparator.comparing(User::getUserNo))
                .map(User::getUserNm)
                .collect(Collectors.toList());
        log.debug("####" + stringList.toString());


        // filter
        Stream<User> stream = all.stream();
        java.util.function.Predicate<User> filterFuntion = u -> company.equals(u.getUserNm());
        Stream<User> filteredStream = stream.filter(filterFuntion);

        //sort
        Comparator<User> sortFunc = Comparator.comparing(User::getUserNo);
        Stream<User> sortedStream = filteredStream.sorted(sortFunc);

        //mapping
        java.util.function.Function<User, String> mappFunc = User::getUserNm;
        Stream<String> mappStream = sortedStream.map(mappFunc);

        log.debug("####" + mappStream.collect(Collectors.toList()));


    }

    @Test
    public void restCallTest() {

//        http://localhost:8080/User/User/FindAll/pageAuto


        List<String> strList = new ArrayList<>();
        for (int i=0;i<100;i++) strList.add("http://localhost:8080/User/User/FindAll/pageAuto?cnt=" + i);

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");

        List<String> collect =
                strList.parallelStream()    // 8cpu: 12s  20cpu: 14s?
//                strList.stream()        // 30s
                .map(s -> {

                    String forObject = "";
                    try {
                        RestTemplate restTemplate = new RestTemplate();
                        forObject  ="";
                        forObject = restTemplate.getForObject(s, String.class);
                        log.debug(forObject.toString());

                    } catch(Exception e){
                            e.printStackTrace();
                    }

                    return  forObject;

                })
                .collect(Collectors.toList());

        log.debug("####" + collect.toString());


    }

    @Test
    public void parallelStreamTest() {

//        http://localhost:8080/User/User/FindAll/pageAuto

        List<String> strList = new ArrayList<>();
        for (int i=0;i<300;i++) strList.add("http://localhost:8080/User/User/FindAll/pageAuto?cnt=" + i);

        List<String> collect =
                strList.parallelStream()    // 8cpu: 13s
                // strList.stream()        // 30s
                        .map(s -> {
                            try {
                                log.debug("Starting " + Thread.currentThread().getName()
                                                        + ", index=" + s
                                                        );


                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return  s;

                        })
                        .collect(Collectors.toList());

        log.debug("####" + collect.toString());


    }




    @Test
    public void functionTypeUseTest(){


        JavaLamdaTest javaLamdaTest = new JavaLamdaTest();
        Integer weight = 8;

        javaLamdaTest.printWeighted((IntUnaryOperator)i -> i*8,weight);

        IntUnaryOperator it = i -> i*9;
        javaLamdaTest.printWeighted(it,weight);



        //javaLamdaTest.printWeighted((CustomLamdaFunction)i -> i*8,weight);

        CustomLamdaFunction clf1 = new CustomLamdaFunction<Integer>(){
                    @Override
                    public int someProcess(Integer integer) {
                        return integer*10;
                    }
                } ;
        javaLamdaTest.printWeighted(clf1,weight);


        CustomLamdaFunction<Integer> clf2 = i -> i*11;
        javaLamdaTest.printWeighted(clf2,weight);

        CustomLamdaFunction<Integer> clf3 = Integer::intValue;
        javaLamdaTest.printWeighted(clf3,weight);


    }

    public void printWeighted(IntUnaryOperator calc, int weight) {
        System.out.println(calc.applyAsInt(weight));
    }
    public void printWeighted(CustomLamdaFunction calc, int weight) { System.out.println(calc.someProcess(weight)); }



    @FunctionalInterface
    public interface CustomLamdaFunction<T> {

        int someProcess(T t);

    }


    public void lamdaDifferentThisTest() {
        Runnable anonClass = new Runnable(){
            @Override
            public void run() {
                verifyRunnable(this);
            }
        };

        anonClass.run();

        Runnable lambda = () -> verifyRunnable(this);
        lambda.run();
    }

    private void verifyRunnable(Object obj) {
        System.out.println(obj instanceof Runnable);
    }



}



