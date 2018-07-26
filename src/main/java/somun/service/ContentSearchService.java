package somun.service;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.config.properties.SomunProperties;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.function.SearchIndexComb;
import somun.service.repository.vo.function.SearchIndexCombPage;
import somun.service.repository.vo.user.User;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Service
public class ContentSearchService {


    @Autowired
    EventContentRepository  eventContentRepository;

    @Autowired
    SomunProperties somunProperties;

    public Page<EventContent> searchTotalSearchIndex(Pageable pageable,SearchIndexComb searchQuery) {


        String url = somunProperties.getSearchApiServer() + String.format("/Engine/V1/SE/searchTotalSearchIndex?page=%d&size=%d"
            ,pageable.getPageNumber()
            ,pageable.getPageSize());


        SearchIndexCombPage body = new RestTemplate().exchange(url, HttpMethod.POST
                                                        , new HttpEntity(searchQuery, new HttpHeaders())
                                                        , SearchIndexCombPage.class).getBody();

        Page<SearchIndexComb> combs = new PageImpl<>(body.getContent(),
                                                     new PageRequest(pageable.getPageNumber(), pageable.getPageSize()),
                                                     body.getTotalElements());


        List<Integer> eventContentNoList = combs.getContent().stream().map(SearchIndexComb::getEventContentNo).collect(Collectors.toList());

        HashMap<Integer, EventContent> eventContentHashMap = eventContentRepository.findByEventContentNoInAndStat(eventContentNoList, Codes.EV_STAT.S2)
                                                                                   .stream().collect(Collectors.toMap(EventContent::getEventContentNo
                                                                                                ,Function.identity()
                                                                                                ,(o, n) -> o
                                                                                                ,HashMap::new));


        return  combs.map(d -> eventContentHashMap.get(d.getEventContentNo()));

    }
}
