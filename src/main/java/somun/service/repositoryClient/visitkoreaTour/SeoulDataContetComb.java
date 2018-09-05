package somun.service.repositoryClient.visitkoreaTour;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.service.repositoryClient.seoulData.RowItem;

@Slf4j
@Data
@Builder
public class SeoulDataContetComb extends ProviderContetComb {

    @Expose
    private RowItem rowItem;


    public String getProviderKey(){

        return contProv.name() + "_" + rowItem.getSVCID();
    }

}
