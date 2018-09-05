package somun.common.util;

import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsoupExtends {


    public  static String text(String html) {
        if (StringUtils.isEmpty(html)) return "";
        return Jsoup.parse(html).text();

    }

    public  static String imagesTagJsonList(String html, int limit) {

        if (StringUtils.isEmpty(html)) return "";

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        Elements imgs = Jsoup.parse(html).select("img");
        String jsonThumbnails = (gson).toJson(imgs.stream()
                                                        .map(d->d.attr("abs:src"))
                                                        .filter(d->!StringUtils.isEmpty(d))
                                                        .limit(limit)
                                                        .collect(Collectors.toList()
                                                        ));

        return jsonThumbnails;
    }



}
