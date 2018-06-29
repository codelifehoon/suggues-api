package somun.service.repository.provider;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.biz.Codes;
import somun.service.repositoryClient.visitkoreaTour.VisitKoreaContetComb;
import somun.service.repositoryClient.visitkoreaTour.commonInfo.Item;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class ContentProviderTest {


    @Autowired
    ContentProviderRepository contentProviderRepository;

    @Test
    @Commit
    public void saveTest(){
        VisitKoreaContetComb build = VisitKoreaContetComb.builder()
                                                    .item(Item.builder()
                                                              .addr1("addr1")
                                                              .build())
                                                         .build();
        String json = new Gson().toJson(build);

        ContentProvider contentProvider = ContentProvider.builder()
                                               .provider(Codes.CONTPROV.visitkorea)
                                               .contetComb(json)
                                               .createDt(new Date())
                                               .createNo(Codes.CONTPROV.visitkorea.getProvNumber())
                                               .updateDt(new Date())
                                               .updateNo(Codes.CONTPROV.visitkorea.getProvNumber())
                                               .stat(Codes.CONTPROV_STAT.S0)
                                               .build();
        ContentProvider save = contentProviderRepository.save(contentProvider);
        assertThat(save).isNotNull();
    }
}
