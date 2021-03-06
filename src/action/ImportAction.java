package action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;

import model.Goods;
import model.GoodsType;
import model.Import;
import model.PageBean;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;

import util.DateUtil;
import util.DbUtil;
import util.ExcelUtil;
import util.JsonUtil;
import util.ResponseUtil;
import util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

import dao.ImportDao;

public class ImportAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String page;
	private String rows;
	
	private String s_goodsId;
	private String s_goodsName;
	
	private String s_providerId;
	private String s_bimpoDate;//开始时间
	private String s_impoDate;
	private String s_eimpoDate;//结束时间
	private Import importGoods;
	
	public String getS_bimpoDate() {
		return s_bimpoDate;
	}
	public void setS_bimpoDate(String s_bimpoDate) {
		this.s_bimpoDate = s_bimpoDate;
	}
	public String getS_eimpoDate() {
		return s_eimpoDate;
	}
	public void setS_eimpoDate(String s_eimpoDate) {
		this.s_eimpoDate = s_eimpoDate;
	}


	private String s_impoWarehouseId;
	private String s_impoDes;
	private GoodsType goodsType;
	private String delIds;
	

	private String id;
	
	private File userUploadFile;
	
	
	public File getUserUploadFile() {
		return userUploadFile;
	}
	public void setUserUploadFile(File userUploadFile) {
		this.userUploadFile = userUploadFile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	public GoodsType getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	public Import getImportGoods() {
		return importGoods;
	}
	public void setImportGoods(Import importGoods) {
		this.importGoods = importGoods;
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
	
	public String getS_goodsId() {
		return s_goodsId;
	}
	public void setS_goodsId(String s_goodsId) {
		this.s_goodsId = s_goodsId;
	}


	DbUtil dbUtil = new DbUtil();
	ImportDao importDao = new ImportDao();
	
	@Override
	public String execute() throws Exception {
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try {
			
			importGoods = new Import();
			goodsType = new GoodsType();
			if(s_goodsId!=null){
				importGoods.setGoodsId(s_goodsId);
			}
			if(s_goodsName!=null){
				goodsType.setTypeName(s_goodsName);
			}
			
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(importDao.importList(con, pageBean,goodsType,importGoods,s_providerId,s_impoDate,s_impoWarehouseId,s_impoDes));
			int total = importDao.importCount(con,goodsType,importGoods,s_providerId,s_impoDate,s_impoWarehouseId,s_impoDes);
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
	
	public String delete() throws Exception{
		Connection con = null;
		try {
			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			String str[] = delIds.split(",");
			int delNums = importDao.importDelete(con,delIds);
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
	
	public String save() throws Exception{
		if(StringUtil.isNotEmpty(id)){
			importGoods.setId(Integer.parseInt(id));
		}
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int saveNums = 0;
			JSONObject result = new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums = importDao.importModify(con, importGoods);
			}else{
				saveNums = importDao.importSave(con,importGoods);				
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
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
			Workbook wb=ExcelUtil.fillExcelDataWithTemplate(importDao.exportData(con), "importTemp.xls");
			ResponseUtil.export(ServletActionContext.getResponse(), wb, "导出excel.xls");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String upload()throws Exception{
		POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(userUploadFile));
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet hssfSheet=wb.getSheetAt(0);  // 获取第一个Sheet页
		if(hssfSheet!=null){
			for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
				HSSFRow hssfRow=hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				Import importGoods = new Import();
				
				importGoods.setGoodsId(ExcelUtil.formatCell(hssfRow.getCell(0)));
//				importGoods.setImpoPrice(ExcelUtil.formatCell(hssfRow.getCell(1)));
				
				/*HSSFCell hssfCell=hssfRow.getCell(2);
				String dateFormat = ExcelUtil.getCell(hssfCell);
				System.out.println(dateFormat.toString());*/
				
				importGoods.setImpoDate(DateUtil.formatString(ExcelUtil.getCell(hssfRow.getCell(2)), "yyyy-MM-dd"));
				importGoods.setImpoNum(ExcelUtil.formatCell(hssfRow.getCell(3)));
//				importGoods.setImpoDesc(ExcelUtil.formatCell(hssfRow.getCell(4)));
				
				
				Connection con=null;
				try{
					con=dbUtil.getCon();
					importDao.importSave(con, importGoods);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					dbUtil.closeCon(con);
				}
			}
		}
		JSONObject result=new JSONObject();
		result.put("success", "true");
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	public String getS_goodsName() {
		return s_goodsName;
	}
	public void setS_goodsName(String s_goodsName) {
		this.s_goodsName = s_goodsName;
	}
	public String getS_providerId() {
		return s_providerId;
	}
	public void setS_providerId(String s_providerId) {
		this.s_providerId = s_providerId;
	}
	public String getS_impoDate() {
		return s_impoDate;
	}
	public void setS_impoDate(String s_impoDate) {
		this.s_impoDate = s_impoDate;
	}
	public String getS_impoWarehouseId() {
		return s_impoWarehouseId;
	}
	public void setS_impoWarehouseId(String s_impoWarehouseId) {
		this.s_impoWarehouseId = s_impoWarehouseId;
	}
	public String getS_impoDes() {
		return s_impoDes;
	}
	public void setS_impoDes(String s_impoDes) {
		this.s_impoDes = s_impoDes;
	}
}
