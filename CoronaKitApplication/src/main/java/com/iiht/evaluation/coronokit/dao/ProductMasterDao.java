package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.iiht.evaluation.coronokit.model.ProductMaster;
import com.mysql.cj.jdbc.JdbcConnection;



public class ProductMasterDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ProductMasterDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	
	public void setJdbcConnection() {
		try {
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public
	Connection getJdbcConnection() {
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jdbcConnection;
	}

	public static final String INS_PROD_QRY = "INSERT INTO products(pname,pcost,pdesc) VALUES(?,?,?)";
	public static final String UPD_PROD_QRY = "UPDATE products set pname=?,pcost=?,pdesc=? WHERE pid=?";
	public static final String UPD_ORD_QRY = "UPDATE orderdetails SET pname=?,pcost=?,pquantity='0' WHERE pid=?";
	public static final String DEL_PROD_QRY = "DELETE FROM products WHERE pid=?";
	public static final String DEL_ORD_QRY = "DELETE FROM orderdetails WHERE pid=?";
	public static final String GET_ALL_PRODS_QRY = "SELECT pid,pname,pcost,pdesc FROM products order by pid asc";
	public static final String GET_PROD_BY_ID_QRY = "SELECT pid,pname,pcost,pdesc FROM products WHERE pid=?";
	
	public ProductMaster add(ProductMaster product) throws ServletException {
		if (product != null) {
			try (PreparedStatement pst = getJdbcConnection().prepareStatement(INS_PROD_QRY);) {

				pst.setString(1, product.getProductName());
				pst.setDouble(2, product.getCost());
				pst.setString(3, product.getProductDescription());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ServletException("Adding product failed!");
			}
		}
		return product;
	}

	public ProductMaster save(ProductMaster product) throws ServletException {
		if (product != null) {
			try (PreparedStatement pst = getJdbcConnection().prepareStatement(UPD_PROD_QRY);
					PreparedStatement psd = getJdbcConnection().prepareStatement(UPD_ORD_QRY);) {
				

				pst.setString(1, product.getProductName());
				pst.setDouble(2, product.getCost());
				pst.setString(3, product.getProductDescription());
				pst.setInt(4, product.getId());
				psd.setString(1, product.getProductName());
				psd.setDouble(2, product.getCost());
				psd.setInt(3, product.getId());

				pst.executeUpdate();
				psd.executeUpdate();
			} catch (SQLException exp) {
				throw new ServletException("Saving product failed!");
			}
		}

		return product;
	}

	public boolean deleteById(int productID) throws ServletException {
		boolean isDeleted = false;
		try (PreparedStatement pst = getJdbcConnection().prepareStatement(DEL_PROD_QRY);
				PreparedStatement psd = getJdbcConnection().prepareStatement(DEL_ORD_QRY);) {

			pst.setInt(1, productID);
			psd.setInt(1, productID);

			int rowsCount = pst.executeUpdate();
			int rowCount = psd.executeUpdate();

			isDeleted = rowsCount > 0;

		} catch (SQLException exp) {
			throw new ServletException("Deleting product failed!");
		}
		return isDeleted;
	}
	
	public List<ProductMaster> getAll() throws ServletException {
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

	public ProductMaster getById(int productID) throws ServletException {
		ProductMaster product = null;

		try (PreparedStatement pst = getJdbcConnection().prepareStatement(GET_PROD_BY_ID_QRY);) {

			pst.setInt(1, productID);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				product = new ProductMaster();
				product.setId(rs.getInt(1));;
				product.setProductName(rs.getString(2));;
				product.setCost(rs.getDouble(3));;
				product.setProductDescription(rs.getString(4));;
			}

		} catch (SQLException exp) {
			throw new ServletException("Feteching product failed!");
		}

		return product;
	}
	
}