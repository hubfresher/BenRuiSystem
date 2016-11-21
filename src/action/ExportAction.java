package action;

import java.sql.Connection;

import model.Export;
import model.PageBean;
import model.Provider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import util.DbUtil;
import util.ExcelUtil;
import util.JsonUtil;
import util.ResponseUtil;
import util.StringUtil;
import dao.ExportDao;

public class ExportAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String page;
	private String rows;
	private Export exportGoods;
	private Provider provider;
//	private String s_goodsId;
	private String s_bexpoPrice;
	private String s_eexpoPrice;
	private String s_bexpoDate;
	private String s_eexpoDate;
	
	private String s_providerName;//¿Í»§Ãû³Æ
	private String delIds;
	private String id;
	
	private String goodsID;
	private String wareHouse;
	private String providerID;
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public Export getExportGoods() {
		return exportGoods;
	}
	public void setExportGoods(Export exportGoods) {
		this.exportGoods = exportGoods;
	}
	public String getS_bexpoPrice() {
		return s_bexpoPrice;
	}
	public void setS_bexpoPrice(String s_bexpoPrice) {
		this.s_bexpoPrice = s_bexpoPrice;
	}
	public String getS_eexpoPrice() {
		return s_eexpoPrice;
	}
	public void setS_eexpoPrice(String s_eexpoPrice) {
		this.s_eexpoPrice = s_eexpoPrice;
	}
	public String getS_bexpoDate() {
		return s_bexpoDate;
	}
	public void setS_bexpoDate(String s_bexpoDate) {
		this.s_bexpoDate = s_bexpoDate;
	}
	public String getS_eexpoDate() {
		return s_eexpoDate;
	}
	public void setS_eexpoDate(String s_eexpoDate) {
		this.s_eexpoDate = s_eexpoDate;
	}
	
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	DbUtil dbUtil = new DbUtil();
	ExportDao exportDao = new ExportDao();
	
	@Override
	public String execute() throws Exception {
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try {
			exportGoods = new Export();
			provider = new Provider();
			if(id!=null){
				exportGoods.setId(Integer.parseInt(id));
			}
			if(s_providerName!=null){
				provider.setProName(s_providerName);;
			}
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(exportDao.exportList(con, pageBean,provider,s_bexpoDate,s_eexpoDate));
			int total = exportDao.exportCount(con,pageBean,provider,s_bexpoDate,s_eexpoDate);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext
					.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public String storeResearch() throws Exception
	{
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			
			int storeNums = exportDao.exportGetStore(con,providerID,goodsID,wareHouse);
			if(storeNums>=0){
				result.put("storageNums",storeNums);
			}else{
				result.put("errorMsg", "²éÑ¯Ê§°Ü");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getExportItem() throws Exception
	{
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int expoid = 0 ;
			if(StringUtil.isNotEmpty(id)){
				expoid = (Integer.parseInt(id));
			}
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(exportDao.exportGetExportItem(con, expoid));
			result.put("rows", jsonArray);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String delete() throws Exception{
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
//			String str[] = delIds.split(",");
			int delNums = exportDao.exportDelete(con,delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums",delNums);
			}else{
				result.put("errorMsg", "É¾³ýÊ§°Ü");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String save() throws Exception{
		if(StringUtil.isNotEmpty(id)){
			exportGoods.setId(Integer.parseInt(id));
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int saveNums = 0;
			JSONObject result = new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums = exportDao.exportModify(con, exportGoods);
			}else{
				saveNums = exportDao.exportSave(con,exportGoods);				
			}
			if(saveNums>=2){
				result.put("success", "true");
			}else{
				result.put("errorMsg", "±£´æÊ§°Ü");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String export() throws Exception{
		Connection con = null;
		try {
			con = dbUtil.getCon();
			Workbook wb=ExcelUtil.fillExcelDataWithTemplate(exportDao.exportData(con), "exportTemp.xls");
			ResponseUtil.export(ServletActionContext.getResponse(), wb, "µ¼³öexcel.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getWareHouse() {
		return wareHouse;
	}
	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}
	
}
