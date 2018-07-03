package somun.service.repositoryClient.visitkoreaTour.detailCommon;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Header{

	@SerializedName("resultCode")
	private String resultCode;

	@SerializedName("resultMsg")
	private String resultMsg;

	public void setResultCode(String resultCode){
		this.resultCode = resultCode;
	}

	public String getResultCode(){
		return resultCode;
	}

	public void setResultMsg(String resultMsg){
		this.resultMsg = resultMsg;
	}

	public String getResultMsg(){
		return resultMsg;
	}
}