
package somun.service.repositoryClient.visitkoreaTour.extraImage;

import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Items {

    @Expose
    private List<Item> item ;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> items) {
        this.item = items;
    }

}
