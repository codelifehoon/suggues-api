package somun.service.repositoryClient.visitkoreaTour.detailCommon;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Items{

	@SerializedName("item")
	private Item item;

	public void setItem(Item item){
		this.item = item;
	}

	public Item getItem(){
		return item;
	}
}