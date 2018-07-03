package somun.service.repositoryClient.visitkoreaTour.detailCommon;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Generated("com.robohorse.robopojogenerator")
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailCommon{

	@SerializedName("header")
	private Header header;

	@SerializedName("body")
	private Body body;

	public void setHeader(Header header){
		this.header = header;
	}

	public Header getHeader(){
		return header;
	}

	public void setBody(Body body){
		this.body = body;
	}

	public Body getBody(){
		return body;
	}
}