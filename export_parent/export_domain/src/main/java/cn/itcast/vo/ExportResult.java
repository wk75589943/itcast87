package cn.itcast.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

@XmlRootElement(name="export")
public class ExportResult implements Serializable{
	private String exportId; //报运单id
	private Integer state;  //报运状态
	private String remark; //报运说明


	private Set<ExportProductResult> products;


	public String getExportId() {
		return exportId;
	}
	public void setExportId(String exportId) {
		this.exportId = exportId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<ExportProductResult> getProducts() {
		return products;
	}
	public void setProducts(Set<ExportProductResult> products) {
		this.products = products;
	}
	
}
