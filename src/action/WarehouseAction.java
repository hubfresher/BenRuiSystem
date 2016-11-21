/**
 * 
 */
package action;

import java.sql.Connection;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import dao.WarehouseDao;
import model.PageBean;
import model.Warehouse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DbUtil;
import util.JsonUtil;
import util.ResponseUtil;

/**
 * @author Pablo
 *
 */
public class WarehouseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DbUtil dbUtil = new DbUtil();
	WarehouseDao warehouseDao = new WarehouseDao();
	
	private String page;
	private String rows;
	private Warehouse warehouse;
	
	private String s_name;
	private String id;
	private String delId;
	/**
	 * 
	 */
	public WarehouseAction() {
		// TODO Auto-generated constructor stub
	}
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
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDelId() {
		return delId;
	}
	public void setDelId(String delId) {
		this.delId = delId;
	}
	
	
	@Override
	public String execute() throws Exception {
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try {
			if(warehouse==null){
				warehouse = new Warehouse();
			}
			if(s_name!=null){
				warehouse.sethName(s_name);
//				dirver.setPhone(s_phone);
//				goods.setProId("0");
//				goods.setTypeId("0");
			}
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(warehouseDao.warehouseList(con, pageBean,warehouse));
			int total = warehouseDao.warehouseCount(con, warehouse);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
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
	
	public String delete()throws Exception{
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int delNums = warehouseDao.warehouseDelete(con, delId);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums",delNums);
			}else{
				result.put("errorMsg", "…æ≥˝ ß∞‹");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String save()throws Exception{
//		if(StringUtil.isNotEmpty(id)){
//			dirver.setId(Integer.parseInt(id));
//		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
//			if(StringUtil.isNotEmpty(id)){
//				saveNums=dirversDao.goodsModify(con, goods);
//			}else{
				saveNums=warehouseDao.warehouseAdd(con, warehouse);
			//}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "±£¥Ê ß∞‹");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
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
	
	public String warehouseComboList()throws Exception{
		Connection con=null;
		try{
			warehouse = new Warehouse();
			con=dbUtil.getCon();
			JSONArray jsonArray=new JSONArray();

			jsonArray.addAll(JsonUtil.formatRsToJsonArray(warehouseDao.warehouseList(con, null, warehouse)));
			ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
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
}
