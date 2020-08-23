package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.ProductMaster;



public class KitDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public KitDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				//Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public Connection getJdbcConnection() {
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jdbcConnection;
	}
	
	public static final String GET_ALL_PRODS_QRY = "SELECT pid,pname,pcost,pquantity FROM orderdetails order by pid asc";
	public static final String UPD_QTY_QRY = "UPDATE orderdetails SET pquantity = ? where pid =?";
	public static final String PID_LIST_QRY = "SELECT pid FROM orderdetails order by pid asc";
	public static final String GET_ALL_ORDERS_QRY = "SELECT pid, pname, pcost, pquantity FROM orderdetails where pquantity > 0";
	public static final String GET_TOT_PRICE_QRY = "Select sum(pcost * pquantity) FROM orderdetails";
	public static final String GET_KIT_DETAIL_QRY = "Select products.pid, orderdetails.pquantity, (products.pcost * orderdetails.pquantity) FROM products, orderdetails where products.pid = orderdetails.pid and orderdetails.pquantity > 0";
	
	public List<String> getpids() throws ServletException{
		List<String> pids = new ArrayList<>();
		try {
			PreparedStatement pst = getJdbcConnection().prepareStatement(PID_LIST_QRY);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				pids.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new ServletException("Fetching pids failed!");
		}
		return pids;
	}
	public List<KitDetail> getkitdetails(CoronaKit coronaKit) throws ServletException {
		List<KitDetail> kitDetail =new ArrayList<>();
		try {
			PreparedStatement pst = getJdbcConnection().prepareStatement(GET_KIT_DETAIL_QRY);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				KitDetail kitdetail = new KitDetail();
				kitdetail.setId(coronaKit.getId());
				kitdetail.setCoronaKitId(0);
				kitdetail.setProductId(rs.getInt(1));
				kitdetail.setQuantity(rs.getInt(2));
				kitdetail.setAmount(rs.getInt(3));
				
				kitDetail.add(kitdetail);				
			}
		} catch (SQLException e) {
			throw new ServletException("Fetching kit details failed!");
		}
		return kitDetail;
	}
	public List<ProductMaster> getOrderDetails() throws ServletException{
		List<ProductMaster> products = new ArrayList<>();
		try {
			PreparedStatement pst = getJdbcConnection().prepareStatement(GET_ALL_ORDERS_QRY);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				ProductMaster product = new ProductMaster();
				product.setId(rs.getInt(1));
				product.setProductName(rs.getString(2));
				product.setCost(rs.getDouble(3));
				product.setProductDescription(rs.getString(4));
				
				products.add(product);
			}
		} catch (SQLException e) {
			throw new ServletException("Fetching order details failed!");
		}
		return products;
	}
	public double getTotalPrice() throws ServletException{
		double totPrice = 0;
		try {
			PreparedStatement pst = getJdbcConnection().prepareStatement(GET_TOT_PRICE_QRY);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				totPrice = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new ServletException("Calculating total purchase cost failed!");
		}
		return totPrice;
	}
	
	public void updateQuantity(List<String> pids, List<String> quantities) throws ServletException{
			for (int i=0; i<quantities.size(); i++) {
				try {
					PreparedStatement pst = getJdbcConnection().prepareStatement(UPD_QTY_QRY);
					pst.setString(1, quantities.get(i));
					pst.setString(2, pids.get(i));
					pst.executeUpdate();
				} catch (SQLException e) {
					throw new ServletException("Update quantities failed!");
				}
			}
	}
	
	public List<ProductMaster> getProductsList() throws ServletException {
		List<ProductMaster> products = new ArrayList<>();
		
		try (PreparedStatement pst = getJdbcConnection().prepareStatement(GET_ALL_PRODS_QRY);) {
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				ProductMaster product = new ProductMaster();
				product.setId(rs.getInt(1));
				product.setProductName(rs.getString(2));
				product.setCost(rs.getDouble(3));
				product.setProductDescription(rs.getString(4));
				
				products.add(product);
			}
			
			if(products.isEmpty()) {
				products=null;
			}

		} catch (SQLException exp) {
			throw new ServletException("Feteching products failed!");
		}
		
		return products;
	}
}
