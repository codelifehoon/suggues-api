package somun.common.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomUtiTest {
    @Test
    public void randomString() throws Exception {
        String str =  RandomUti.randomString(5);

        MatcherAssert.assertThat(str.length(), Matchers.is(5));
        log.debug(str);
    }

    @Test
    public void randomNumber() throws Exception {

        Integer randomNumber = RandomUti.randomNumber(5);
        log.debug(randomNumber.toString());
        MatcherAssert.assertThat(randomNumber,Matchers.greaterThan(0));

    }

    @Test
    public void randomHash() {

        log.debug(new String("AAA"+"BBB").hashCode() + String.valueOf(RandomUti.randomString(9)));

    }

}