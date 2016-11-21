package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.PageBean;
import model.Warehouse;
import util.StringUtil;

public class WarehouseDao {
	public ResultSet warehouseList(Connection con,PageBean pageBean,Warehouse house) throws Exception{
		StringBuffer sb = new StringBuffer("SELECT * FROM t_warehouse t");
		if(StringUtil.isNotEmpty(house.gethName())){
			sb.append(" and t.hName like '%"+house.gethName()+"%'");
		}
//		if(StringUtil.isNotEmpty(goods.getGoodsName())){
//			sb.append(" and t3.goodsName like '%"+goods.getGoodsName()+"%'");
//		}
//		if(StringUtil.isNotEmpty(goods.getProId())){
//			sb.append(" and t3.proId='"+goods.getProId()+"'");
//		}
//		if(StringUtil.isNotEmpty(goods.getTypeId())){
//			sb.append(" and t3.typeId='"+goods.getTypeId()+"'");
//		}
//		sb.append(" order by t3.id asc");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		System.out.println(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
//	public ResultSet exportData(Connection con) throws Exception {
//		String sql = "SELECT goodsId,goodsName,proName,typeName,goodsDesc FROM t_goodsType t1,t_provider t2,t_goods t3 WHERE t3.proId=t2.id AND t1.id=t3.typeId";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		return pstmt.executeQuery();
//	}
	
	public int warehouseCount(Connection con,Warehouse house) throws Exception{
	StringBuffer sb = new StringBuffer("select count(*) as total from t_warehouse t");
		if(StringUtil.isNotEmpty(house.gethName())){
			sb.append(" and t.hName like '%"+house.gethName()+"%'");
		}
		//sb.append(" order by t3.rowid asc");
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
//	public int driverDelete(Connection con,String delname,String phone)throws Exception{
//		String sql="delete from t_dirver where name=?,phone=?";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(0, delname);
//		pstmt.setString(1, phone);
//		System.out.println(sql);
//		return pstmt.executeUpdate();
//	}
	public int warehouseDelete(Connection con,String delId)throws Exception{
		String sql="delete from t_warehouse where id = ?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, delId);
		System.out.println(sql);
		return pstmt.executeUpdate();
	}
	
	public int warehouseAdd(Connection con, Warehouse house)throws Exception{
		String sql="insert into t_warehouse values(?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, house.getId());
		pstmt.setString(2, house.gethName());
		pstmt.setString(3, house.gethAddress());
		pstmt.setString(4, house.gethDes());		
		return pstmt.executeUpdate();
	}
}
