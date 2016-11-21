package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Goods;
import model.GoodsType;
import model.Import;
import model.PageBean;
import util.DateUtil;
import util.StringUtil;

public class ImportDao {

	public ResultSet importList(Connection con,PageBean pageBean,GoodsType goodst,Import importGoods,String s_providerId,String s_impoDate,String s_impoWarehouseId,String s_impoDes) throws Exception{
		StringBuffer sb = new StringBuffer("SELECT * FROM t_provider t3, t_goodstype t2,t_importcargo t1 WHERE t1.goodsId=t2.id AND t1.providerId=t3.id");
		if(StringUtil.isNotEmpty(goodst.getTypeName())){
			sb.append(" and t2.goodsName like '%"+goodst.getTypeName()+"%'");
		}
		
		if(StringUtil.isNotEmpty(s_providerId)){
			sb.append(" and providerId ="+s_providerId+"'");
		}
		
		if(StringUtil.isNotEmpty(s_impoDate)){
			sb.append(" and TO_DAYS(t1.impoDate)>=TO_DAYS('"+s_impoDate+"')");
		}
		
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		
		System.out.println(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public ResultSet exportData(Connection con) throws Exception {
		String sql = "SELECT typeName,impoDate,impoNum,impoDesc FROM t_goodstype t2,t_import t1 WHERE t1.goodsId=t2.id";
		PreparedStatement pstmt = con.prepareStatement(sql);
		return pstmt.executeQuery();
	}
	
	public int importCount(Connection con,GoodsType goodst,Import importGoods,String s_providerId,String s_impoDate,String s_impoWarehouseId,String s_impoDes) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_goodstype t2,t_importcargo t1 where t1.goodsId=t2.id");
		if(StringUtil.isNotEmpty(goodst.getTypeName())){
			sb.append(" and t2.typeName like '%"+goodst.getTypeName()+"%'");
		}
		
		if(StringUtil.isNotEmpty(s_providerId)){
			sb.append(" and providerId ="+s_providerId+"'");
		}
		
		if(StringUtil.isNotEmpty(s_impoDate)){
			sb.append(" and TO_DAYS(t1.impoDate)>=TO_DAYS('"+s_impoDate+"')");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public int importDelete(Connection con,String delIds) throws Exception{
		String sql = "delete from t_importcargo where id in ("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int importSave(Connection con,Import importGoods) throws Exception{
		
//		String sql = "select * from t_importcargo where goodsId=? and providerId=? and impoWarehouseId=?";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, importGoods.getGoodsId());
//		pstmt.setString(2, importGoods.getProviderId());
//		pstmt.setString(3, importGoods.getImpoWarehouseId());
//		ResultSet rs=pstmt.executeQuery();
//		if(rs.next()){
//			//找到记录则更新,这样写在有并发时候有问题
//			sql = "update t_importcargo set impoNum=?,impoDes=? where id=?";
//		}else{
			//未找到记录则插入
		String	sql = "insert t_importcargo value(null,?,?,?,?,?,?)";
		//}		 
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,importGoods.getGoodsId());
		pstmt.setString(2, importGoods.getProviderId());
		pstmt.setString(3, DateUtil.formatDate(importGoods.getImpoDate(), "yyyy-MM-dd"));
		pstmt.setInt(4,Integer.parseInt(importGoods.getImpoNum()));
		pstmt.setString(5, importGoods.getImpoWarehouseId());
		pstmt.setString(6, importGoods.getImpoDes());
		return pstmt.executeUpdate();
	}
	
	public int importModify(Connection con ,Import importGoods) throws Exception{
		String sql = "update t_importcargo set goodsId=?,providerId=?,impoDate=?,impoNum=?,impoWarehouseId=?,impoDes=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,importGoods.getGoodsId());
		pstmt.setString(2, importGoods.getProviderId());
		pstmt.setString(3, DateUtil.formatDate(importGoods.getImpoDate(), "yyyy-MM-dd"));
		pstmt.setString(4, importGoods.getImpoNum());
		pstmt.setString(5, importGoods.getImpoWarehouseId());
		pstmt.setString(6, importGoods.getImpoDes());
		pstmt.setInt(7, importGoods.getId());
		return pstmt.executeUpdate();
	}
	
	public boolean getGoodsByImportId(Connection con,String delIds) throws Exception{
		String sql = "select * from t_importcargo where goodsId=?";
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
