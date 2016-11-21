package model;

import java.util.Date;

public class Import {

	private int id;
	private String goodsId;//-->goodsType
	private String providerId;
	private Date impoDate;
	private String impoNum;
	private String impoWarehouseId;
	private String impoDes;
	public int getId() {
		return id;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getImpoWarehouseId() {
		return impoWarehouseId;
	}
	public void setImpoWarehouseId(String impoWarehouseId) {
		this.impoWarehouseId = impoWarehouseId;
	}
	public String getImpoDes() {
		return impoDes;
	}
	public void setImpoDes(String impoDes) {
		this.impoDes = impoDes;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	public Date getImpoDate() {
		return impoDate;
	}
	public void setImpoDate(Date impoDate) {
		this.impoDate = impoDate;
	}
	public String getImpoNum() {
		return impoNum;
	}
	public void setImpoNum(String impoNum) {
		this.impoNum = impoNum;
	}
	
}
