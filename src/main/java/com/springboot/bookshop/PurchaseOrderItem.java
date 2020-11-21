package main.java.com.springboot.bookshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PurchaseOrderItem {
	
	DataSource ds;

	public PurchaseOrderItem() {
		try{
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public HashMap<Integer, PurchaseOrderItemBean>RetrievePurchaseOrderItems (int count) throws SQLException
	{
		String query = String.format("select * from POItem where count=%d", count);
		
		HashMap<Integer, PurchaseOrderItemBean> rv = new HashMap<Integer, PurchaseOrderItemBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while(r.next())
		{
			String bid = r.getString("BID");
			int id = r.getInt("COUNT");
			int price = r.getInt("PRICE");
			int quantity = r.getInt("QUANTITY");
			rv.put(id,new PurchaseOrderItemBean(id, bid, price, quantity));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
	
	public HashMap<Integer, List<PurchaseOrderItemBean>>RetrievePurchaseOrderItems () throws SQLException
	{
		String query = String.format("select * from POItem");
		
		HashMap<Integer, List<PurchaseOrderItemBean>> rv = new HashMap<Integer, List<PurchaseOrderItemBean>>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while(r.next())
		{
			String bid = r.getString("BID");
			int id = r.getInt("COUNT");
			int price = r.getInt("PRICE");
			int quan = r.getInt("QUANTITY");
			if (rv.containsKey(id))
			{
				List<PurchaseOrderItemBean> tmp = rv.get(id);
				tmp.add(new PurchaseOrderItemBean(id, bid, price, quan));
			}
			else
			{
				LinkedList<PurchaseOrderItemBean> tmp = new LinkedList<PurchaseOrderItemBean>();
				tmp.add(new PurchaseOrderItemBean(id, bid, price, quan));
				rv.put(id, tmp);
			}
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
	
	public int updatePO(String username, String firstname, String lastname, String status) throws SQLException{
		int count = 1;
		String insertPO = String.format("insert into PO (username, lname, fname, status) "
				+ "values ('" + username + "', '" + lastname + "' , '" + firstname + "' , '" + status + "')");
		String query = String.format("select count from PO ORDER BY count desc");
		System.out.println("this is what was inserted" + insertPO);
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(insertPO);
		p.executeUpdate();
		PreparedStatement q = con.prepareStatement(query);
		ResultSet r = q.executeQuery();
		if(r.next()) {
			count = r.getInt("COUNT");
		}
		else
			count = -1;
		r.close();
		p.close();
		con.close();
		return count;
	}
}
