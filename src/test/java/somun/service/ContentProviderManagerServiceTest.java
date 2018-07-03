package somun.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.util.RandomUti;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.provider.ContentProvider;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class)
public class ContentProviderManagerServiceTest {

    @Autowired
    ContentProviderManagerService contentProviderManagerService;


    @Autowired
    EventContentService eventContentService;

    String contetCombStr ;

    @Before
    public void setUp() throws Exception {
         this.contetCombStr = "{\"item\": {\"tel\": \"가파리사무소 064-794-7130\", \"cat1\": \"A02\", \"cat2\": \"A0207\", \"cat3\": " +
             "\"A02070200\", \"mapx\": 126.271738497, \"mapy\": \"33.1701766897\", \"addr1\": \"제주특별자치도 서귀포시 대정읍\", \"addr2\": \"가파도 " +
             "일원\", \"title\": \"가파도청보리축제 2018\", \"mlevel\": 6, \"zipcode\": \"63514\", \"areacode\": 39, \"contentid\": 712175, " +
             "\"readcount\": 72441, \"firstimage\": \"http://tong.visitkorea.or.kr/cms/resource/18/1999118_image2_1.jpg\", " +
             "\"createdtime\": 20090326164207, \"firstimage2\": \"http://tong.visitkorea.or.kr/cms/resource/18/1999118_image3_1.jpg\", " +
             "\"sigungucode\": 3, \"modifiedtime\": 20180413171019, \"contenttypeid\": 15}, \"introduce\": {\"body\": {\"items\": " +
             "{\"item\": {\"program\": \"\", \"agelimit\": \"전연령 가능함\", \"playtime\": \"2018.04.14~2018.05.14 / 일정은 변경될 수 있으니 전화 확인 요망\"," +
             " \"sponsor1\": \"가파도청보리축제위원회\", \"subevent\": \"- 농·수산물 판매<br />- 전기자동차 체험 등\", \"contentid\": 712175, \"placeinfo\": \"* " +
             "제주공항 → 평화로(구서부관광도로) → 모슬포항<br /> \\n* 중문관광단지 → 1132번지방도 → 모슬포항<br /> \\n* 서귀포시내 →1132번지방도 → 모슬포항\", \"eventplace\": \"제주 " +
             "서귀포시 대정읍 가파도 일원\", \"sponsor1tel\": \"가파리사무소 064-794-7130\", \"sponsor2tel\": \"가파리사무소 064-794-7130\", \"bookingplace\": " +
             "\"\", \"eventenddate\": 20180514, \"contenttypeid\": 15, \"eventstartdate\": 20180414, \"usetimefestival\": \"무료\", " +
             "\"spendtimefestival\": \"기간내 자유\", \"discountinfofestival\": \"\"}}, \"pageNo\": 1, \"numOfRows\": 10, \"totalCount\": 1}, " +
             "\"header\": {\"resultMsg\": \"OK\", \"resultCode\": \"0000\"}}, \"extraImage\": {\"body\": {\"items\": {\"item\": " +
             "[{\"contentid\": 712175, \"serialnum\": \"1999124_2\", \"originimgurl\": \"http://tong.visitkorea.or" +
             ".kr/cms/resource/24/1999124_image2_1.jpg\", \"smallimageurl\": \"http://tong.visitkorea.or" +
             ".kr/cms/resource/24/1999124_image3_1.jpg\"}, {\"contentid\": 712175, \"serialnum\": \"1999126_3\", \"originimgurl\": " +
             "\"http://tong.visitkorea.or.kr/cms/resource/26/1999126_image2_1.jpg\", \"smallimageurl\": \"http://tong.visitkorea.or" +
             ".kr/cms/resource/26/1999126_image3_1.jpg\"}, {\"contentid\": 712175, \"serialnum\": \"1999131_5\", \"originimgurl\": " +
             "\"http://tong.visitkorea.or.kr/cms/resource/31/1999131_image2_1.jpg\", \"smallimageurl\": \"http://tong.visitkorea.or" +
             ".kr/cms/resource/31/1999131_image3_1.jpg\"}, {\"contentid\": 712175, \"serialnum\": \"1999146_4\", \"originimgurl\": " +
             "\"http://tong.visitkorea.or.kr/cms/resource/46/1999146_image2_1.jpg\", \"smallimageurl\": \"http://tong.visitkorea.or" +
             ".kr/cms/resource/46/1999146_image3_1.jpg\"}, {\"contentid\": 712175, \"serialnum\": \"1999149_1\", \"originimgurl\": " +
             "\"http://tong.visitkorea.or.kr/cms/resource/49/1999149_image2_1.jpg\", \"smallimageurl\": \"http://tong.visitkorea.or" +
             ".kr/cms/resource/49/1999149_image3_1.jpg\"}]}, \"pageNo\": 1, \"numOfRows\": 10, \"totalCount\": 5}, \"header\": " +
             "{\"resultMsg\": \"OK\", \"resultCode\": \"0000\"}}, \"detailCommon\": {\"body\": {\"items\": {\"item\": {\"tel\": \"가파리사무소 " +
             "064-794-7130\", \"cat1\": \"A02\", \"cat2\": \"A0207\", \"cat3\": \"A02070200\", \"mapx\": 126.271738497, \"mapy\": " +
             "33.1701766897, \"addr1\": \"제주특별자치도 서귀포시 대정읍\", \"addr2\": \"가파도 일원\", \"title\": \"가파도청보리축제 2018\", \"mlevel\": 6, " +
             "\"telname\": \"가파도청보리축제위원회\", \"zipcode\": 63514, \"areacode\": 39, \"homepage\": \"<a href=\\\"http://70ni.seogwipo.go" +
             ".kr/index.php/contents/festival_seogwipo/gapado/introduce\\\" target=\\\"_blank\\\" title=\\\"새창: 가파도청보리축제 홈페이지로 " +
             "이동\\\">http://culture.jeju.go.kr</a>\", \"overview\": \"가파도는 대정읍 모슬포항에서 뱃길로 20여분, 남쪽으로 5.5㎞ 해상에 있는 섬으로 18만평의 청보리 물결 위로 " +
             "동쪽으로는 한라산을 비롯한 \\n5개산(산방산, 송악산, 고근산, 군산, 단산 )이 병풍처럼 둘러져 있으며 서쪽으로는 국토 최남단 마라도가 보이는 비경을 간직한 섬이다 \\n가파도의 \\n“청보리”는 국토 최남단의 " +
             "땅끝에서 가장 먼저 전해오는 봄소식으로 3월 초부터 \\n5월 초순까지 보리잎의 푸른 생명이 절정을 이루며, 특히 가파도 청보리의 품종 향맥은 \\n타 지역보다 2배 이상 자라는 제주의 향토 품종으로 전국에서 가장 먼저 " +
             "높고 푸르게 \\n자라나 해마다 봄이 되면 18만여평의 청보리밭 위로 푸른 물결이 굽이치는 장관을 \\n이룬다.<br>\", \"contentid\": 712175, \"firstimage\": \"http://tong" +
             ".visitkorea.or.kr/cms/resource/18/1999118_image2_1.jpg\", \"createdtime\": 20090326164207, \"firstimage2\": \"http://tong" +
             ".visitkorea.or.kr/cms/resource/18/1999118_image3_1.jpg\", \"sigungucode\": 3, \"modifiedtime\": 20180413171019, " +
             "\"contenttypeid\": 15}}, \"pageNo\": 1, \"numOfRows\": 10, \"totalCount\": 1}, \"header\": {\"resultMsg\": \"OK\", " +
             "\"resultCode\": \"0000\"}}}";
    }



    @Test
    public void generatorDesc()
    {
        ContentProvider contentProvider = ContentProvider.builder()
                                                         .contetComb(contetCombStr)
                                                         .providerKey("1234567")
                                                         .providerModifiedtime(RandomUti.randomString(10))
                                                         .build();
        EventContent eventContent = contentProviderManagerService.mergeIntoVisitKoreaContetToEventContent(contentProvider);
        assertThat(eventContent).isNotNull();


    }



}
