package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Goods;
import model.Import;
import model.PageBean;
import model.Provider;
import model.Stock;
import util.DateUtil;
import util.StringUtil;

public class StockDao {

	public ResultSet stockList(Connection con,PageBean pageBean) throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_stock t1,t_goodstype t2, t_provider t3 where t1.providerId = t3.id and t1.goodsId = t2.id");
//		if(StringUtil.isNotEmpty(s_bimpoPrice)){
//			sb.append(" and impoPrice >="+s_bimpoPrice+"");
//		}
//		if(StringUtil.isNotEmpty(s_eimpoPrice)){
//			sb.append(" and impoPrice <="+s_eimpoPrice+"");
//		}
//		if(StringUtil.isNotEmpty(s_bexpoPrice)){
//			sb.append(" and expoPrice >="+s_bexpoPrice+"");
//		}
//		if(StringUtil.isNotEmpty(s_eexpoPrice)){
//			sb.append(" and expoPrice <="+s_eexpoPrice+"");
//		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
		}
		//System.out.println(sb.toString());
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public int stockCount(Connection con,Goods goods,String s_bimpoPrice,String s_eimpoPrice,String s_bexpoPrice,String s_eexpoPrice) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_stock");
//		if(StringUtil.isNotEmpty(goods.getGoodsName())){
//			sb.append(" and t1.goodsName like '%"+goods.getGoodsName()+"%'");
//		}
//		if(StringUtil.isNotEmpty(s_bimpoPrice)){
//			sb.append(" and impoPrice >="+s_bimpoPrice+"");
//		}
//		if(StringUtil.isNotEmpty(s_eimpoPrice)){
//			sb.append(" and impoPrice <="+s_eimpoPrice+"");
//		}
//		if(StringUtil.isNotEmpty(s_bexpoPrice)){
//			sb.append(" and expoPrice >="+s_bexpoPrice+"");
//		}
//		if(StringUtil.isNotEmpty(s_eexpoPrice)){
//			sb.append(" and expoPrice <="+s_eexpoPrice+"");
//		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public int stockDelete(Connection con,String delIds) throws Exception{
		String sql = "delete from t_stock where id in ("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	public int stockSave(Connection con,Stock stock) throws Exception{
		String sql = "insert t_stock value(null,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,stock.getGoodsId());
		pstmt.setString(2, stock.getProviderId());
		pstmt.setString(3, stock.getWareHouse());
		pstmt.setInt(4, stock.getStoreNum());
		return pstmt.executeUpdate();
	}
	
	public int stockModify(Connection con ,Stock stock) throws Exception{
		String sql = "update t_stock set goodsId=?,providerId=?,wareHouse=?,storeNum=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1,stock.getGoodsId());
		pstmt.setString(2, stock.getProviderId());
		pstmt.setString(3, stock.getWareHouse());
		pstmt.setInt(4, stock.getStoreNum());
		pstmt.setInt(5, stock.getId());
		return pstmt.executeUpdate();
	}
	
	public boolean getGoodsByStockId(Connection con,String delIds) throws Exception{
		String sql = "select * from t_stock where id=?";
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
