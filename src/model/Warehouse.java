package model;

public class Warehouse {
	private String id;
	private String hName;
	private String hAddress;
	private String hDes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String gethName() {
		return hName;
	}
	public void sethName(String hName) {
		this.hName = hName;
	}
	public String gethAddress() {
		return hAddress;
	}
	public void sethAddress(String hAddress) {
		this.hAddress = hAddress;
	}
	public String gethDes() {
		return hDes;
	}
	public void sethDes(String hDes) {
		this.hDes = hDes;
	}
	
}
