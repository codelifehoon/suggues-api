package somun.service.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import somun.service.repository.user.UserRepository;
import somun.service.repository.vo.WebCertInfo;
import somun.service.repository.vo.user.User;

@Service
public class WebCertService {

    @Autowired
    UserRepository userRwepository;

    public WebCertInfo webCertInfoBuild(String webCertInfoStr){

        webCertInfoStr = webCertInfoStr.replace("j:","");
        User u =  Optional.ofNullable(new Gson().fromJson(webCertInfoStr, User.class))
                        .orElse(User.builder()
                                   .userNo(0)
                                   .build());

        u = Optional.ofNullable(userRwepository.findByUserHash(u.getUserHash()))
                    .orElse(User.builder()
                                .userNo(0)
                                .build());

        return WebCertInfo.builder().user(u).build();


    }

}
