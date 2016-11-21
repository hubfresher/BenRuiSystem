package model;

import java.util.Date;

public class Export {

	private int id;
	
	private String driver;//司机 1
	private String carNum;//车号 2
	private String carDes;//车备注 3
//	private boolean isCheckOwner;//是否核实货主 4
	private String isCheckOwner;//是否核实货主 4

	private String phoneNum;//手机号 5
	private String shipper;//装货人 6
//	private boolean isPay;//是否付运费 7
	private String isPay;//是否付运费 7
	
//	private Date payDate; //8
	private String payDate; //8
//	private boolean isPayBackFee;//是否付回单钱 9
	private String isPayBackFee;//是否付回单钱 9

//	private Date payBackFeeDate;// 10
	private String payBackFeeDate;// 10
//	private Date expoDate;//发货日期 11 
	private String expoDate;//发货日期 11 
	private String payDesc;//运费备注 12
	private String contactNum;//合同号 13
	private String contactDesc;//合同备注14
	private String expoDesc;//出库备注 15
	
	private String fromPlace;//出发地  16
	private String toPlace;//目的地  17
	/*
	 * 
	 * */
	private String goodsid;//货物ID,可以是多个 16
	private String warehouseid;//仓库,可以是多个 19
	private String goodsnum;//货物数量,可以是多个 17
	private String providerid;//出库目标公司,可以是多个 18
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
	
	public String getExpoDesc() {
		return expoDesc;
	}
	public void setExpoDesc(String expoDesc) {
		this.expoDesc = expoDesc;
	}
	
	
	public String getCarDes() {
		return carDes;
	}
	public void setCarDes(String carDes) {
		this.carDes = carDes;
	}
	
	
	public String getPayDesc() {
		return payDesc;
	}
	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public String getContactDesc() {
		return contactDesc;
	}
	public void setContactDesc(String contactDesc) {
		this.contactDesc = contactDesc;
	}
	public String getIsCheckOwner() {
		return isCheckOwner;
	}
	public void setIsCheckOwner(String isCheckOwner) {
		this.isCheckOwner = isCheckOwner;
	}
	public String getIsPay() {
		return isPay;
	}
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getIsPayBackFee() {
		return isPayBackFee;
	}
	public void setIsPayBackFee(String isPayBackFee) {
		this.isPayBackFee = isPayBackFee;
	}
	public String getPayBackFeeDate() {
		return payBackFeeDate;
	}
	public void setPayBackFeeDate(String payBackFeeDate) {
		this.payBackFeeDate = payBackFeeDate;
	}
	public String getExpoDate() {
		return expoDate;
	}
	public void setExpoDate(String expoDate) {
		this.expoDate = expoDate;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getProviderid() {
		return providerid;
	}
	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}
	public String getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(String goodsnum) {
		this.goodsnum = goodsnum;
	}
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public String getWarehouseid() {
		return warehouseid;
	}
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}	
	
	
}
