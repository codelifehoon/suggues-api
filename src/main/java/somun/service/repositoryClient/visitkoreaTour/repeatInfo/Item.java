
package somun.service.repositoryClient.visitkoreaTour.repeatInfo;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Item {

    @Expose
    private Long contentid;
    @Expose
    private Long contenttypeid;
    @Expose
    private Long fldgubun;
    @Expose
    private String infoname;
    @Expose
    private String infotext;
    @Expose
    private Long serialnum;

    public Long getContentid() {
        return contentid;
    }

    public void setContentid(Long contentid) {
        this.contentid = contentid;
    }

    public Long getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(Long contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public Long getFldgubun() {
        return fldgubun;
    }

    public void setFldgubun(Long fldgubun) {
        this.fldgubun = fldgubun;
    }

    public String getInfoname() {
        return infoname;
    }

    public void setInfoname(String infoname) {
        this.infoname = infoname;
    }

    public String getInfotext() {
        return infotext;
    }

    public void setInfotext(String infotext) {
        this.infotext = infotext;
    }

    public Long getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(Long serialnum) {
        this.serialnum = serialnum;
    }

}
