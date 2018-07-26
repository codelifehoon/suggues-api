package somun.common.util;

import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsoupExtends {


    public  static String text(String html) {
        if (StringUtils.isEmpty(html)) return "";
        return Jsoup.parse(html).text();

    }

    public  static String imagesTagJsonList(String html) {

        if (StringUtils.isEmpty(html)) return "";

        Elements imgs = Jsoup.parse(html).select("img");
        String jsonThumbnails = (new Gson()).toJson(imgs.stream().map(d->d.attr("abs:src")).collect(Collectors.toList()));

        return jsonThumbnails;
    }



}
