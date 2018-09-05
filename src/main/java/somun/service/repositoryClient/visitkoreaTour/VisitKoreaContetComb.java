package somun.service.repositoryClient.visitkoreaTour;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.service.repositoryClient.visitkoreaTour.commonInfo.Item;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.DetailCommon;
import somun.service.repositoryClient.visitkoreaTour.extraImage.ExtraImage;
import somun.service.repositoryClient.visitkoreaTour.introduceInfo.Introduce;
import somun.service.repositoryClient.visitkoreaTour.repeatInfo.Repeat;


@Slf4j
@Data
@Builder
public class VisitKoreaContetComb extends ProviderContetComb {

    @Expose
    private Item item;
    @Expose
    private ExtraImage extraImage;
    @Expose
    private Introduce introduce;
    @Expose
    private Repeat repeat;

    @Expose
    private DetailCommon detailCommon;

    public String getProviderKey(){

        return contProv.name() + "_" + item.getContenttypeid() + "_" +  item.getContentid();
    }

}
