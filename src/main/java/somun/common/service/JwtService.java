package somun.common.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import somun.config.properties.SomunProperties;

@Slf4j
@Service
public class JwtService {

    @Autowired
    SomunProperties somunProperties;

    public  Object parseJwt(String key, String jwtString)  {

        Object returnObj = null ;
        try{
        Jws<Claims> jws = Jwts.parser()
                              .setSigningKey(getJwtSecretBytes())
                              .parseClaimsJws(jwtString);
            returnObj = jws.getBody().get(key);

        } catch (RuntimeException e){log.debug("########"); log.debug(e.toString());}

        return returnObj;
    }

    public  String createJwt(String key, Object data)  {

        String jwt = Jwts.builder()
                         .setHeaderParam("typ", "JWT")
                         .setHeaderParam("regDate", System.currentTimeMillis())
//                         .setSubject("")
                         .claim(key, data)
                         .signWith(SignatureAlgorithm.HS256, getJwtSecretBytes())
                         .compact();


        return jwt;
    }

    private byte[] getJwtSecretBytes()  {
        try {
            return somunProperties.getJwtSecret().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }

        return null;
    }
}
