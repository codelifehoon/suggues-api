package somun.common.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;


public class RandomUtiTest {
    @Test
    public void randomString() throws Exception {
        String str =  RandomUti.randomString(5);

        MatcherAssert.assertThat(str.length(), Matchers.is(5));
        System.out.println(str);
    }

    @Test
    public void randomNumber() throws Exception {

        Integer randomNumber = RandomUti.randomNumber(5);
        System.out.println(randomNumber.toString());
        MatcherAssert.assertThat(randomNumber,Matchers.greaterThan(0));

    }

    @Test
    public void randomHash() {
        /*System.out.println(new String("AAA"+"BBB").hashCode() + String.valueOf(RandomUti.randomString(9)));

        var x = new HashMap<String,Integer>();
        x.put("value",1);
        System.out.println(x);
*/
    }

}