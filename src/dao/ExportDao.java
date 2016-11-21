package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import model.Export;
import model.PageBean;
import model.Provider;
import util.DateUtil;
import util.StringUtil;

public class ExportDao {

	public ResultSet exportList(Connection con,PageBean pageBean,Provider provider,String s_bexpoDate,String s_eexpoDate) throws Exception{
		StringBuffer sb = new StringBuffer("SELECT * FROM t_export t1 order by id desc");
		if(StringUtil.isNotEmpty(s_bexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)>=TO_DAYS('"+s_bexpoDate+"')");
		}
		if(StringUtil.isNotEmpty(s_eexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)<=TO_DAYS('"+s_eexpoDate+"')");
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		
		System.out.println(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	//TODO - 导出
	public ResultSet exportData(Connection con) throws Exception {
		String sql = "SELECT t2.id,goodsName,expoPrice,expoDate,expoNum,expoDesc FROM t_goods t2,t_export t1 WHERE t1.goodsId=t2.id";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeQuery();
	}
	
	public int exportCount(Connection con,PageBean pageBean,Provider provider,String s_bexpoDate,String s_eexpoDate) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_export t1");
		if(StringUtil.isNotEmpty(s_bexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)>=TO_DAYS('"+s_bexpoDate+"')");
		}
		if(StringUtil.isNotEmpty(s_eexpoDate)){
			sb.append(" and TO_DAYS(t1.expoDate)<=TO_DAYS('"+s_eexpoDate+"')");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public int exportDelete(Connection con,String delIds) throws Exception{
		String sql = "delete from t_export where id in ("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int exportGetStore(Connection con,String providerid,String goodsid,String warehouse) throws Exception{
		String sql = "select SUM(impoNum) as sum from t_importcargo where goodsId = ? and providerId = ? and impoWarehouseId =?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,goodsid);
		pstmt.setString(2,providerid);
		pstmt.setString(3,warehouse);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("sum");
		}else{
			return 0;
		}
	}
	
	public ResultSet exportGetExportItem(Connection con,int exportid) throws Exception{
		String sql = "select * from t_exportitem t1, t_provider t2,t_goodstype t3 where exportid = ? and t2.id=t1.providerid and t3.id=t1.goodsid";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,exportid);
		ResultSet rs=pstmt.executeQuery();
	    return rs ;
		
	}
	public int exportSave(Connection con,Export exportGoods) throws Exception{
		
		String sql = "insert t_export value(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con
				.prepareStatement(sql);
		pstmt.setString(1,exportGoods.getDriver());
		pstmt.setString(2, exportGoods.getCarNum());
		pstmt.setString(3, exportGoods.getCarDes());
		pstmt.setBoolean(4,Boolean.valueOf(exportGoods.getIsCheckOwner()));
		pstmt.setString(5, exportGoods.getPhoneNum());
		pstmt.setString(6, exportGoods.getShipper());
		pstmt.setBoolean(7, Boolean.valueOf(exportGoods.getIsPay()));
	    pstmt.setString(8, exportGoods.getPayDate());
		pstmt.setBoolean(9,Boolean.valueOf( exportGoods.getIsPayBackFee()));
		pstmt.setString(10, exportGoods.getPayBackFeeDate());
		pstmt.setString(11,exportGoods.getExpoDate());
		pstmt.setString(12, exportGoods.getPayDesc());
		pstmt.setString(13, exportGoods.getContactNum());
		pstmt.setString(14, exportGoods.getContactDesc());
		pstmt.setString(15, exportGoods.getExpoDesc());
		pstmt.setString(16, exportGoods.getFromPlace());
		pstmt.setString(17, exportGoods.getToPlace());
		int insertexportResult = pstmt.executeUpdate();
		
		sql = "SELECT LAST_INSERT_ID()"; 
		pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		int autoInckey = -1;
		if(rs.next()){
			autoInckey = rs.getInt(1);//取得ID
		}else{
			return 0;
		}
		
		int insertexportItemResult = 0;
		if (exportGoods.getGoodsid().length() > 0) {
			String strgoodid[] = exportGoods.getGoodsid().split(",");//货物ids
			String strproviderid[] = exportGoods.getProviderid().split(",");//providerids
			String strnumber[] = exportGoods.getGoodsnum().split(",");//货物数量ids
			String strwarehouse[] = exportGoods.getWarehouseid().split(",");//对应仓库
			for (int i = 0; i < strgoodid.length; i++) {
				String goodsid = strgoodid[i];
				String providerid = strproviderid[i];
				String goodsnum = strnumber[i];
				String warehouse = strwarehouse[i];
				sql  = "insert t_exportitem value(null,?,?,?,?,?)";
				 pstmt=con
						.prepareStatement(sql);
				pstmt.setInt(1,autoInckey);
				pstmt.setInt(2,Integer.parseInt(goodsid.trim()));
				pstmt.setInt(3,Integer.parseInt(goodsnum.trim()));
				pstmt.setString(4,(warehouse.trim()));
				pstmt.setInt(5,Integer.parseInt(providerid.trim()));
				
				insertexportItemResult = insertexportItemResult+pstmt.executeUpdate();
 			}
		}
		return insertexportResult  +  insertexportItemResult ;
	}
	public int exportItemSave(Connection con ,Export exportGoods) throws Exception{
		
		String sql = "insert t_exportitem value(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con
				.prepareStatement(sql);
		pstmt.setString(1,exportGoods.getDriver());
		pstmt.setString(2, exportGoods.getCarNum());
		return 0;
		
	}
	
	public int exportModify(Connection con ,Export exportGoods) throws Exception{
		String sql = "update t_export set driver=?,carNum=?,carDes=?,isCheckOwner=?,phoneNum=?,shipper=?,isPay=?,payDate=?,isPayBackFee=?,"
				+ "payBackFeeDate=?,expoDate=?,payDesc=?,contactNum=?,contactDesc=?,expoDesc=?"
				+ " where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,exportGoods.getDriver());
		pstmt.setString(2, exportGoods.getCarNum());
		pstmt.setString(3, exportGoods.getCarDes());
		pstmt.setBoolean(4,Boolean.valueOf(exportGoods.getIsCheckOwner()));
		pstmt.setString(5, exportGoods.getPhoneNum());
		pstmt.setString(6, exportGoods.getShipper());
		pstmt.setBoolean(7, Boolean.valueOf(exportGoods.getIsPay()));
	    pstmt.setString(8, exportGoods.getPayDate());
	    
		pstmt.setBoolean(9,Boolean.valueOf( exportGoods.getIsPayBackFee()));
		pstmt.setString(10, exportGoods.getPayBackFeeDate());
		
		pstmt.setString(11,exportGoods.getExpoDate());
		pstmt.setString(12, exportGoods.getPayDesc());
		pstmt.setString(13, exportGoods.getContactNum());
		pstmt.setString(14, exportGoods.getContactDesc());
		pstmt.setString(15, exportGoods.getExpoDesc());
		pstmt.setInt(16, exportGoods.getId());
		return pstmt.executeUpdate();
	}
	
	public boolean getGoodsByExportId(Connection con,String delIds) throws Exception{
		String sql = "select * from t_export where goodsId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, delIds);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
}
