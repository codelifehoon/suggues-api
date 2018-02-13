package somun.core.util;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

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

}