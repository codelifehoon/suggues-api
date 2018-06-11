package somun.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
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

    @Test
    public void messageDigest() throws NoSuchAlgorithmException {

        String  originalString = "{\"endpoint\":\"https://fcm.googleapis" +
            ".com/fcm/send/c1LeCqonfng:APA91bE25zo6Isg-StYKxxBadNEqxNFkcxYto8k17J_s8zL3USJjHrMFHu50XFrR_gKfi" +
            "-PIw5HTo5rd3AnOMuZpYNiKDryTpyREwWFkdWQJ_pbDmRHBAL-WIERsLI4LKXDuiRdtWCJO\",\"expirationTime\":null," +
            "\"keys\":{\"p256dh\":\"BEkVq-tUCzlU-9QjzyBWYkwiiBXHlLT9gni7a2kOAdger83HbX3tpNq-jC3aP5jeO0W1-nv0icmILUHAhPKAvf8\"," +
            "\"auth\":\"F9wA_qHmv0YpODLODOzp7g\"}}";

        String  originalString2 = "{\"endpoint\":\"https://fcm.googleapis" +
            ".com/fcm/send/c1LeCqonfng:APA91bE25zo6Isg-StYKxxBadNEqxNFkcxYto8k17J_s8zL3USJjHrMFHu50XFrR_gKfi" +
            "-PIw5HTo5rd3AnOMuZpYNiKDryTpyREwWFkdWQJ_pbDmRHBAL-WIERsLI4LKXDuiRdtWCJO\",\"expirationTime\":null," +
            "\"keys\":{\"p256dh\":\"BEkVq-tUCzlU-9QjzyBWYkwiiBXHlLT9gni7a2kOAdger83HbX3tpNq-jC3aP5jeO0W1-nv0icmILUHAhPKAvf8\"," +
            "\"auth\":\"F9wA_qHmv0YpODLODOzp7g\"}}";


        byte[] encodedhash1 = MessageDigest.getInstance("SHA-256").digest(originalString.getBytes(StandardCharsets.UTF_8));
        byte[] encodedhash2 = MessageDigest.getInstance("SHA-256").digest(originalString2.getBytes(StandardCharsets.UTF_8));



        assertThat(bytesToHex(encodedhash1)).isEqualTo(bytesToHex(encodedhash2));

    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
