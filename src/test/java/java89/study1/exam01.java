package java89.study1;

public class exam01 {

    public void  test(){
        return;
    }
}

/*

@Data
@Builder
class Address {

    private String street;
    private String city;
    private String zipcode;
}
@Data
@Builder
class Order {
    private Long id;
    private Date date;
    private Member member;

}

@Data
@Builder
class Member {
    private Long id;
    private String name;
    private Address address;
}


@Data
@AllArgsConstructor
class Product {
    private int id;
    private boolean usable;
    private String category;
    private int price;
}

@Data
@AllArgsConstructor
@ToString
class Pair {
    Integer min;
    Integer max;
}

@Data
@AllArgsConstructor
class Student {
    String name;
    int age;
    int gradYear;
    int gradScore;
}


@Slf4j
public class exam01 {

    List<Student> students;
    List<Product> products;

    @Before
    public void setUp() throws Exception {
        this.students = List.of(
            new Student("tomy Lee", 18, 2014, 420),
            new Student("tina Roo", 19, 2013, 120),
            new Student("shin Kim", 18, 2014, 920),
            new Student("jane Kim", 19, 2013, 350)
        );

        this.products= Arrays.asList(
            new Product(1, true, "outdoor", 50_000),
            new Product(2, true, "outdoor", 38_000),
            new Product(3, true, "mobile", 250_000),
            new Product(4, true, "mobile", 85_000),
            new Product(5, true, "mobile", 99_000),
            new Product(6, true, "mobile", 75_000),
            new Product(7, true, "furniture", 350_000),
            new Product(8, false, "furniture", 210_000),
            new Product(9, false, "furniture", 58_000),
            new Product(10, false, "mobile", 120_000)
        );


    }


    @Test
    public void testName() {

        var x = new HashMap<String,Integer>();
        x.put("value",1);
        System.out.println(x);

        assertThat(1).isEqualTo(1);
    }

    @Test
    public void greet() {
        List<String> names = new ArrayList<String>(Arrays.asList("Caesium","Luras","Hercle"));

        log.debug(names
            .stream()
            .map(name -> name + " ")
            .reduce("Welcome ", (old, next) -> old + next) + "!");
    }

    @Test
    public void calcMax(){
        int min = 0;
        for (Student s : students) {
            if (s.getGradYear() != 2014)
                continue;
            int score = s.getGradScore();
            if (score > min)
                min = score;
        }

        assertThat(min).isEqualTo(920);
    }

    @Test
    public void calcMaxLamda(){
       final int min = this.students.stream()
                                 .filter(d -> d.gradYear == 2014)
                                 .mapToInt(Student::getGradScore)
                                 .max()
                                 .getAsInt();
        assertThat(min).isEqualTo(920);


    }

    @Test
    public void calcMaxMin(){


        Pair reduce = this.students.stream()
                                   .map(d -> new Pair(d.gradScore, d.gradScore))
                                   .reduce(new Pair(Integer.MAX_VALUE,0), (old, next) ->
                                           {
                                               return new Pair(Math.min(old.getMin(), next.getMin())
                                                   ,Math.max(old.getMax(), next.getMax()));
                                           }
                                   );


        log.debug(reduce.toString());
    }

    @Test
    public  void predicateInterface(){
        Predicate<Integer> p = (n) -> 5 <= n;

        System.out.println(p.test(3));
        System.out.println(p.test(6));
    }

    @Test
    public  void functionInterface() {
        Function<String,Integer> f = (s) ->
        { if (s.length()> 10) {return s.length();}
            return 0;
        };
        System.out.println(f.apply("ADSDSSD"));
    }

    @Test
    public  void functionSupplier() {

        Supplier<Integer> f = () -> RandomUti.randomNumber(1000);

        System.out.println(f.get());
    }

    @Test
    public  void functionUnaryOperator() {

        UnaryOperator<Integer> f = (s) -> s*2;

        System.out.println(f.apply(10));
    }

    @Test
    public  void functionBinaryOperator() {

        BinaryOperator<String> f = (t1,t2) -> t1 + t2 ;

        System.out.println(f.apply("10"," JAVA"));
    }

    @Test
    public void  test1(){
        var collect = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
                                            .stream()
                                            .filter(i -> (i % 2) == 0)
                                            .map(i -> "[" + i + "]")
                                            .collect(Collectors.toList());
        System.out.println(collect);
    }


    @Test
    public void exampleProduct(){

        Order order = Order.builder()
                           .member(Member.builder()
                                         .address(Address.builder()
                                                         .city("Daegu")
                                                         .build())
                                         .build())
                           .build();

        String city = Optional.ofNullable(order)
                               .map(Order::getMember)
                               .map(Member::getAddress)
                               .map(Address::getCity)
                               .orElse("Seoul");

        assertThat(city).isEqualTo("Daegu");
    }

    @Test
    public void findAny(){
//        Optional<Student> student = students.stream().filter(d -> d.age >= 19).findAny();
//        Optional<Student> student1 = students.stream().filter(d -> d.age >= 19).findFirst();

        Optional<Student> student = students.stream().findAny();
        Optional<Student> student1 = students.parallelStream().findFirst();
        assertThat(student).isEqualTo(student1);

        OptionalInt first = IntStream.range(0, 100000).parallel().filter(d-> d> 50000).findFirst();
        OptionalInt any = IntStream.range(0, 100000).parallel().filter(d-> d> 50000).findAny();
        assertThat(first).isEqualTo(any);

    }


    @Test
    public void sortTest(){

        var sortCollect = products.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());

        log.debug(String.valueOf(sortCollect));
        System.out.println(sortCollect);
    }


    @Test
    public void objectToIntList(){

        System.out.println(this.products
                               .stream()
                               .map(d->d.getId()%2>0)
                               .filter(d->d)
                               .count());


    }

}
*/
