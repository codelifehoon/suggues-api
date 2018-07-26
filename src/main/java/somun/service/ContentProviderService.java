package somun.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.service.repository.provider.ContentProviderRepository;
import somun.service.repository.vo.provider.ContentProvider;
import somun.service.repositoryClient.visitkoreaTour.VisitKoreaContetComb;

@Slf4j
@Service
public class ContentProviderService {

    @Autowired
    ContentProviderRepository contentProviderRepository;

    public int mergeIntoVisitKoreaContet(List<VisitKoreaContetComb> contetCombs) {

        Date date = new Date();

        List<ContentProvider> contentProviderList = contetCombs.stream().map(d -> {
            ContentProvider contentProvider = ContentProvider.builder()
                                  .provider(Codes.CONTPROV.visitkorea)
                                  .providerKey(d.getProviderKey(Codes.CONTPROV.visitkorea))
                                  .providerModifiedtime(d.getItem().getModifiedtime().toString())
                                  .contetComb(new Gson().toJson(d))
                                  .stat(Codes.CONTPROV_STAT.S0)
                                  .createDt(date)
                                  .createNo(Codes.CONTPROV.visitkorea.getProvNumber())
                                  .updateDt(date)
                                  .updateNo(Codes.CONTPROV.visitkorea.getProvNumber())
                                  .build();

            return addOrUpdateVisitKoreaContet(contentProvider);
        }).collect(Collectors.toList());


        return contentProviderList.size();
    }


    public ContentProvider addOrUpdateVisitKoreaContet(ContentProvider contentProvider) {

        // 기존에 존재하는지 확인
        ContentProvider result = null;
        Codes.CONTPROV_STAT[] stats = new Codes.CONTPROV_STAT[] {Codes.CONTPROV_STAT.S0 , Codes.CONTPROV_STAT.S1, Codes.CONTPROV_STAT.S2};
        ContentProvider contentResult = Optional.ofNullable(contentProviderRepository.findByProviderKeyAndStatIn(contentProvider.getProviderKey(), stats))
                                                .orElse(ContentProvider.builder().build());
        // 기존 정보가 전재하고 수기정일자가 기존에 저장된 정보와 다르다면 update 진행
        if (contentProvider.getProviderKey().equals(contentResult.getProviderKey())){
            if (!contentProvider.getProviderModifiedtime().equals(contentResult.getProviderModifiedtime())){
                // 기존 정보 수정
                contentProvider.setSeq(contentResult.getSeq());
                contentProvider.setStat(Codes.CONTPROV_STAT.S1);

                result = contentProviderRepository.save(contentProvider);
            }else{
                //가져온 VisitKoreaContet가 변경이 없음
                log.debug("####가져온 VisitKoreaContet가 변경이 없음");

            }


        } else{
                // 정보추가
                result = contentProviderRepository.save(contentProvider);
        }

        // 존재하지 않는다면 신규로 등록

        return result;

    }
}

