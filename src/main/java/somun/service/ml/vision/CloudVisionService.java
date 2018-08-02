package somun.service.ml.vision;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.google.cloud.vision.v1.WebDetection;
import com.google.protobuf.ByteString;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CloudVisionService {


    public List<String>  detectText(String filePath) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();
        List<String> detectTextList = new ArrayList<>();

        Image image = getImage(filePath);

        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    log.debug(res.getError().getMessage());
                    continue;
                }

                detectTextList.addAll(res.getTextAnnotationsList()
                                     .stream()
                                     .limit(1)
                                     .map(EntityAnnotation::getDescription)
                                     .collect(Collectors.toList()));
            }
        }

        return detectTextList;
    }

    public  List<String>  detectWebDetections(String filePath) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();
        List<String> detectWebList = new ArrayList<>();

        Image image = getImage(filePath);

        Feature feat = Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(image).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    log.debug(res.getError().getMessage());
                    return detectWebList;
                }

                // Search the web for usages of the image. You could use these signals later
                // for user input moderation or linking external references.
                // For a full list of available annotations, see http://g.co/cloud/vision/docs
                WebDetection annotation = res.getWebDetection();

                /*for (WebDetection.WebPage page : annotation.getPagesWithMatchingImagesList()) {
                    System.out.println(page.getUrl() + " : " +  page.getScore());

                }*/
                detectWebList = annotation.getPagesWithMatchingImagesList()
                                          .stream()
                                          .limit(2)
                                          .map(WebDetection.WebPage::getUrl)
                                          .collect(Collectors.toList());
            }
        }
        return detectWebList;
    }


    private  Image getImage(String filePath) throws IOException {
        Image image;

        if (filePath.startsWith("gs://")) { // GCS에서 이미지를 가져올때 image 생성
            ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(filePath).build();
            image = Image.newBuilder().setSource(imgSource).build();
        }
        else if(filePath.startsWith("http")) { // GCS에서 이미지를 가져올때 image 생성
            ByteString imgBytes = ByteString.readFrom(new URL(filePath).openStream());
            image = Image.newBuilder().setContent(imgBytes).build();
        }
        else { // 로컬에서 이미지를 가져올때 image 생성

            ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));
            image = Image.newBuilder().setContent(imgBytes).build();
        }

        return image;
    }


}
