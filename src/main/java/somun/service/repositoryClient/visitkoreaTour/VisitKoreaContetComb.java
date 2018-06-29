package somun.service.repositoryClient.visitkoreaTour;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.service.repositoryClient.visitkoreaTour.commonInfo.Item;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.DetailCommon;
import somun.service.repositoryClient.visitkoreaTour.extraImage.ExtraImage;
import somun.service.repositoryClient.visitkoreaTour.introduceInfo.Introduce;
import somun.service.repositoryClient.visitkoreaTour.repeatInfo.Repeat;


@Slf4j
@Data
@Builder
public class VisitKoreaContetComb {

    @Expose
    Item item;
    @Expose
    ExtraImage extraImage;
    @Expose
    Introduce introduce;
    @Expose
    Repeat repeat;

    @Expose
    DetailCommon detailCommon;


    public String getProviderKey(Codes.CONTPROV provider){

        return provider.name() + "_" + item.getContenttypeid() + "_" +  item.getContentid();
    }

}
