package somun.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import somun.service.repository.User;
import somun.service.repository.UserRepository;
import somun.service.repositoryComb.WebCertInfo;

@Service
public class WebCertService {

    @Autowired
    UserRepository userRwepository;

    public WebCertInfo webCertInfoBuild(String webCertInfoStr){

        webCertInfoStr = webCertInfoStr.replace("j:","");
        User u =  new Gson().fromJson(webCertInfoStr, User.class);

        u = userRwepository.findByUserHash(u.getUserHash());

        return WebCertInfo.builder().user(u).build();


    }

}
