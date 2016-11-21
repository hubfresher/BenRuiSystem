package model;

public class Stock {

	private int id;
	private String goodsId; //货物名称
	private String providerId;//货主名称
	private String wareHouse;//仓库
	private int storeNum; //货物存量
//	private String expoPrice;
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	
//	public String getImpoPrice() {
//		return impoPrice;
//	}
//	public void setImpoPrice(String impoPrice) {
//		this.impoPrice = impoPrice;
//	}
//	public String getExpoPrice() {
//		return expoPrice;
//	}
//	public void setExpoPrice(String expoPrice) {
//		this.expoPrice = expoPrice;
//	}
//	public String getStockDesc() {
//		return stockDesc;
//	}
//	public void setStockDesc(String stockDesc) {
//		this.stockDesc = stockDesc;
//	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	public int getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
