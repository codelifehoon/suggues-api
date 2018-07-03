package somun.service.repositoryClient.visitkoreaTour.detailCommon;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Body{

	@SerializedName("pageNo")
	private int pageNo;

	@SerializedName("totalCount")
	private int totalCount;

	@SerializedName("items")
	private Items items;

	@SerializedName("numOfRows")
	private int numOfRows;

	public void setPageNo(int pageNo){
		this.pageNo = pageNo;
	}

	public int getPageNo(){
		return pageNo;
	}

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setItems(Items items){
		this.items = items;
	}

	public Items getItems(){
		return items;
	}

	public void setNumOfRows(int numOfRows){
		this.numOfRows = numOfRows;
	}

	public int getNumOfRows(){
		return numOfRows;
	}
}