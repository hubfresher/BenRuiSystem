package action;

import java.io.File;
import java.sql.Connection;


import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import dao.DirverDao;
import model.Dirver;
import model.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DbUtil;
import util.JsonUtil;
import util.ResponseUtil;

public class DriversAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String page;
	private String rows;
	private Dirver dirver;
	
	private String s_name;
	private String s_phone;
	private String id;


	private String delId;
//	private String delPhone;

	
	private File userUploadFile;
	
	public File getUserUploadFile() {
		return userUploadFile;
	}
	public void setUserUploadFile(File userUploadFile) {
		this.userUploadFile = userUploadFile;
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
	
	public Dirver getDirver() {
		return dirver;
	}
	public void setDirver(Dirver dirv) {
		this.dirver =dirv;
	}
	
	public String getDelId() {
		return delId;
	}
	public void setDelId(String delId) {
		this.delId = delId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	DbUtil dbUtil = new DbUtil();
	DirverDao dirversDao = new DirverDao();
	
	@Override
	public String execute() throws Exception {
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try {
			if(dirver==null){
				dirver = new Dirver();
			}
			if(s_name!=null){
				dirver.setName(s_name);
//				dirver.setPhone(s_phone);
//				goods.setProId("0");
//				goods.setTypeId("0");
			}
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(dirversDao.dirverList(con, pageBean,dirver));
			int total = dirversDao.dirversCount(con,dirver);
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
			int delNums = dirversDao.driverDelete(con,delId);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums",delNums);
			}else{
				result.put("errorMsg", "删除失败");
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
				saveNums=dirversDao.driverAdd(con, dirver);
			//}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
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
	
	public String dirversComboList()throws Exception{
		Connection con=null;
		try{
			dirver = new Dirver();
			con=dbUtil.getCon();
			JSONArray jsonArray=new JSONArray();
//			JSONObject jsonObject=new JSONObject();
//			jsonObject.put("id", "");
//			jsonObject.put("name", "请选择...");
//			jsonArray.add(jsonObject);
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(dirversDao.dirverList(con, null,dirver)));
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
	
//	public String export() throws Exception{
//		Connection con = null;
//		try {
//			con = dbUtil.getCon();
//			Workbook wb=ExcelUtil.fillExcelDataWithTemplate(dirversDao.exportData(con), "goodsTemp.xls");
//			ResponseUtil.export(ServletActionContext.getResponse(), wb, "导出excel.xls");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//	public String upload()throws Exception{
//		POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(userUploadFile));
//		HSSFWorkbook wb=new HSSFWorkbook(fs);
//		HSSFSheet hssfSheet=wb.getSheetAt(0);  // 获取第一个Sheet页
//		if(hssfSheet!=null){
//			for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
//				HSSFRow hssfRow=hssfSheet.getRow(rowNum);
//				if(hssfRow==null){
//					continue;
//				}
//				Goods goods = new Goods();
//				
//				goods.setGoodsId(ExcelUtil.formatCell(hssfRow.getCell(0)));
//				goods.setGoodsName(ExcelUtil.formatCell(hssfRow.getCell(1)));
////				goods.setProId(ExcelUtil.formatCell(hssfRow.getCell(2)));
////				goods.setTypeId(ExcelUtil.formatCell(hssfRow.getCell(3)));
//				goods.setGoodsDesc(ExcelUtil.formatCell(hssfRow.getCell(4)));
//								
//				Connection con=null;
//				try{
//					con=dbUtil.getCon();
//					goodsDao.goodsAdd(con, goods);
//				}catch(Exception e){
//					e.printStackTrace();
//				}finally{
//					dbUtil.closeCon(con);
//				}
//			}
//		}
//		JSONObject result=new JSONObject();
//		result.put("success", "true");
//		ResponseUtil.write(ServletActionContext.getResponse(), result);
//		return null;
//	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_phone() {
		return s_phone;
	}
	public void setS_phone(String s_phone) {
		this.s_phone = s_phone;
	}

}
