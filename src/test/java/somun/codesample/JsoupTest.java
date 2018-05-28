package somun.codesample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.google.gson.Gson;

public class JsoupTest {

    String htmlSample = "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: inherit;font-family: 나눔고딕, " +
        "nanumgothic, se_NanumGothic, AppleSDGothicNeo, sans-serif, simhei;\">혹등고래인 '모모'가 자신의 삶의 의미를 찾아 떠나 최종적으로는 강에 도달하는 모험을 담은 책입니다" +
        ".</span><br>&nbsp;</p>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p style=\"text-align:center;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<img src=\"https://postfiles.pstatic.net/MjAxODA1MDRfMTMg/MDAxNTI1MzY5MzQ3MTg5.gfv-selr_JH-0ZaeOAqYIexAbAsbOoVhYspy-moggX0g" +
        ".PCKK51zrOMN63482IjQBIUjT-ipyv9gB0PZTUheAossg.JPEG.fslin_/1.jpg?type=w773\" alt=\"\" style=\"float:none;height: auto;width: " +
        "600px\"/>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:center;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<blockquote style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: 12px;font-family: " +
        "나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">보통의 일반적인 삶에 의문을 품는다는 것.</span><br><span style=\"color: inherit;" +
        "background-color: initial;font-size: 12px;font-family: 나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">고래들에겐 당연한 일들이 " +
        "모모에겐 고통스럽기만 하다.</span></blockquote>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: inherit;font-family: 나눔고딕, " +
        "nanumgothic, se_NanumGothic, AppleSDGothicNeo, sans-serif, simhei;\"> 모모는 특이합니다. 왜냐면 보통의 고래들과는 다른 생각을 갖고 살아가기 때문입니다. 집단 사냥으로 먹이를" +
        " 사냥하고, 많은 암컷들과 교미하고 번식하는 일. 혹등고래의 '일반적인 삶'에는 모모는 큰 관심이 없습니다. 오히려, 그 일반적인 삶에 대해 끝도 없이 의문을 던지고 자신의 삶의 의미를 찾습니다. 모모는 노래하는 것, 그리고 " +
        "자신과 대화를 나누는 것을 좋아합니다. 많은 고래들은 모모를 멸시하고 그의 삶을 꾸짖습니다.</span><br></p>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p style=\"text-align:justify;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<img src=\"https://postfiles.pstatic.net/MjAxODA1MDRfNDQg/MDAxNTI1MzY5NjQwMDU0.DFaHxP7ogSpDnVoSyWclsnxK1mcAgxHDIfYRbMu9pYYg" +
        ".gMny8cvJhPrlnPgxYEQ2tCUlyqGlmxBzrSwyjV_BLBIg.JPEG.fslin_/1.jpg?type=w773\" alt=\"\" style=\"float:none;height: auto;width: " +
        "693px\"/>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:center;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<blockquote style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: 12px;font-family: " +
        "나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">싸우는 것도 싫어하고,</span><br><span style=\"color: inherit;background-color: " +
        "initial;font-size: 12px;font-family: 나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">사는 것도 재미없다고 하고, …</span><br><span " +
        "style=\"color: inherit;background-color: initial;font-size: 12px;font-family: 나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, " +
        "simsun;\">대체 네가 원하는 게 뭐야?</span><br><span style=\"color: inherit;background-color: initial;font-size: 12px;font-family: 나눔명조, " +
        "nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">-강을 눈앞에 두고 두려워하는 바야바가 모모에게</span></blockquote>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:left;\"></p>\n" +
        "<p></p>\n" +
        "<p></p>\n" +
        "<p style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: inherit;font-family: 나눔고딕, " +
        "nanumgothic, se_NanumGothic, AppleSDGothicNeo, sans-serif, simhei;\"> 모모는 용감하고 모험감 넘치는 친구 '바야바'를 만납니다. 그리고, 어떤 고래도 하지 않았던 도전을 " +
        "합니다. 바로 강을 역류해서 늪으로 가는 위험천만한 여행을 계획합니다. 하지만, 낯선 강의 풍경에 두려움을 느낀 모모는 강을 눈앞에 두고 깊은 생각에 잠깁니다. 모모는 강을 역류해 올라갈 수 있을까요? 또 이후에 어떻게 " +
        "될까요?</span></p>\n" +
        "<p></p>\n" +
        "<p><span style=\"color: rgb(128,128,128);background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;" +
        "\"><strong>[출처]</strong></span> <a href=\"https://blog.naver.com/fslin_/221267730862\" target=\"_blank\"><span style=\"color: " +
        "rgb(96,140,186);background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;\">[책 리뷰] 삶의 목표를 찾아서, 혹등고래 모모의 " +
        "여행</span></a><span style=\"color: rgb(128,128,128);background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;" +
        "\">|<strong>작성자</strong></span> <a href=\"https://blog.naver.com/fslin_\" target=\"_blank\"><span style=\"color: rgb(96,140,186)" +
        ";background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;\">성또치</span></a></p>\n" +
        "<p><br></p>\n";

    @Test
    public void parsing_img() throws IOException {

        Document doc = Jsoup.connect("http://sports.news.nate.com/view/20180516n24483?mid=s0501").get();

        Elements imgs = doc.select("img");

        List<String> imageUrls = new ArrayList<>();

        for(Element img : imgs) {
            imageUrls.add(img.attr("abs:src"));
        }

        System.out.println(imageUrls); // 이미지 URL들.

    }

    @Test
    public void parsing_text() throws IOException {

        Document doc = Jsoup.connect("http://sports.news.nate.com/view/20180516n24483?mid=s0501").get();
        System.out.println(doc.text());

    }

    @Test
    public void parsing_text_from_Html() throws IOException {

        Document doc = Jsoup.parse(htmlSample);
        Elements imgs = doc.select("img");


        List<String> imageUrls = new ArrayList<>();

        for(Element img : imgs) {
            imageUrls.add(img.attr("abs:src"));
        }



        Gson objGson = new Gson();
        System.out.println(objGson.toJson(imageUrls));



    }

    @Test
    public void parsing_text_from_text() throws IOException {

        Document doc = Jsoup.parse(htmlSample);
        System.out.println(doc.text());
    }






}
