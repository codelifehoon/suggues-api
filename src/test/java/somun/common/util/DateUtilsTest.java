package somun.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DateUtilsTest {


    @Test
    public  void parse() throws ParseException {


        String  kst = "Sat May 19 2018 15:40:32 GMT+0900 (KST)";
            String strDate = null;
            try {
                /**
                 * 여기서 중요한데 객체생성시 파라미터 값으로 KST 표준 포맷 형태를 제대로
                 * 넘기지 않으면 제대로 값을 받아오지 못한다. 이것때문에 시간이 좀 걸렸는데 위 내용을
                 * 참조하기 바란다.
                 */
                SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

                /** 여기에 원하는 포맷을 넣어주면 된다 */
                SimpleDateFormat tranSimpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

                Date data = recvSimpleFormat.parse(kst);
                strDate = tranSimpleFormat.format(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(strDate);


    }
}
