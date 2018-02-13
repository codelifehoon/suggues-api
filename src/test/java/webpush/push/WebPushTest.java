package webpush.push;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.Security;
@Slf4j
public class WebPushTest {

    String m_endpoint = "https://fcm.googleapis.com/fcm/send/eA1F78kKJ_4:APA91bFoWonaCbNeVYS_OaARQf7pk_BGrZ4RCgxZTmtkj3pHMkbu_xEVQIliuYycKx0E16jrpLW1UNsE7cEPKKnzC2D_UVzu8I2A9-RCRyUpNN9Z0jEi5AHG0y-7xhz5gwNnc2xuTP-t";

    String m_userPublicKey = "BOOxNhKjrgoC5oefm7ewi8tTpCJVBRHAJ9BnzdVdxxGmi2bbdMURKZotDsZC6wymFesSVMpIaD8Am7GZ6O5iqo0";
    String m_userAuth = "j3F7QcldKGFglBtGTeh7pg==";

    // Base64 string server public/private key
    String m_vapidPublicKey = "BMRQd_C2NL8RDqrbxqHweX3g32j218yub56JjM8mE1A3I8jweO9MBBtfR65jHjhKrNOOeFhZx3bp2majGlN68qk";
    String m_vapidPrivateKey = "knhJQfCDigLWb9k0GcWapwZPQRoHOylciRFNGZ8hR6g";


    @Before
    public  void init()
    {

        log.debug("##### init()");
    }
    //public static void addSecurityProvider() { Security.addProvider(new BouncyCastleProvider()); }

    @Test
    public void testPushChromeVapid() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        String endpoint = "https://fcm.googleapis.com/fcm/send/eA1F78kKJ_4:APA91bFoWonaCbNeVYS_OaARQf7pk_BGrZ4RCgxZTmtkj3pHMkbu_xEVQIliuYycKx0E16jrpLW1UNsE7cEPKKnzC2D_UVzu8I2A9-RCRyUpNN9Z0jEi5AHG0y-7xhz5gwNnc2xuTP-t";

        // Base64 string user public key/auth
        String userPublicKey = m_userPublicKey;
        String userAuth = m_userAuth;

        // Base64 string server public/private key
        String vapidPublicKey = m_vapidPublicKey;
        String vapidPrivateKey = m_vapidPrivateKey;

        // Construct notification
        Notification notification = new Notification(endpoint, userPublicKey, userAuth, getPayload());

        // Construct push service
        PushService pushService = new PushService();
        pushService.setSubject("mailto:codelifehoon@gmail.com");
        pushService.setPublicKey(Utils.loadPublicKey(vapidPublicKey));
        pushService.setPrivateKey(Utils.loadPrivateKey(vapidPrivateKey));

        // Send notification!

        HttpResponse httpResponse = pushService.send(notification);
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));



    }


    /**
     * Some dummy payload (a JSON object)
     *
     * @return
     */
    private byte[] getPayload() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", " 안녕~");
        jsonObject.addProperty("message", "World");

        return jsonObject.toString().getBytes();
    }




}
