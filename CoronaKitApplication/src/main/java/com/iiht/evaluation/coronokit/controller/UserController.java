package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.OrderSummary;
import com.iiht.evaluation.coronokit.model.ProductMaster;

@WebServlet({"/user","/newuser","/insertuser","/showproducts","/addnewitem","/deleteitem","/showkit","/placeorder","/saveorder","/ordersummary"})
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	public CoronaKit coronaKit;
	public List<KitDetail> kitdetails;
	public OrderSummary orderSummary = new OrderSummary();

	public void setProductMasterDao(ProductMasterDao productMasterDao) {}
	public void setKitDAO(KitDao kitDao) {this.kitDAO = kitDao;}
	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config. getServletContext().getInitParameter("jdbcPassword");
		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		this.coronaKit = new CoronaKit();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		String viewName = "";
		try {
			switch (action) {
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				viewName = insertNewUser(request, response);
				break;
			case "showproducts":
				viewName = showAllProducts(request, response);
				break;	
			case "addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;	
			case "ordersummary":
				viewName = showOrderSummary(request, response);
				break;	
			default : viewName = "notfound.jsp"; break;	
			}
		} catch (Exception ex) {
			
			throw new ServletException(ex.getMessage());
		}
			RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
			dispatch.forward(request, response);
	
	}

	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		String view = "";
		try {
			coronaKit.setTotalAmount((int) kitDAO.getTotalPrice());
		} catch (ServletException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		request.setAttribute("coronaKit", coronaKit);
		view = "ordersummary.jsp";		
		return view;
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response) {
		return "placeorder.jsp";
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		return "placeorder.jsp";
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {
		String view="";	
		int qtyVal = 0;
		ArrayList<String> quantities = new ArrayList<String>(Arrays.asList(request.getParameterValues("pquantity")));
		for(String i: quantities) {
			qtyVal += Integer.parseInt(i);
		}
		
		if(qtyVal > 0) {
		List<ProductMaster> products = null;
		try {
			List<String> pids = kitDAO.getpids();
			kitDAO.updateQuantity(pids, quantities);	
			products = kitDAO.getOrderDetails();
			request.setAttribute("products", products);
			kitdetails = kitDAO.getkitdetails(coronaKit);
			orderSummary.setKitDetails(kitdetails);
			view="showkit.jsp";
		} catch (ServletException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}}else {
			request.setAttribute("errMsg", "you have not selected any products, hence cannot proceed to Order Summary");
			view = "errorPage.jsp";
		}
		
		return view;
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {
		// Covered with ModifyKit button
		return "";
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response) {
		// Covered with ModifyKit button
		return "";
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response) {
		String view = "";

		try {
			List<ProductMaster> products = kitDAO.getProductsList();
			request.setAttribute("products", products);
			view = "showproductstoadd.jsp";
		} catch (ServletException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		return view;
	}

	private String insertNewUser(HttpServletRequest request, HttpServletResponse response) {
		String view = "";
		int id = Integer.parseInt("" + LocalTime.now().getSecond() +""+ LocalTime.now().getMinute() +""+ LocalTime.now().getHour());
		List<ProductMaster> products = null;
		if(validations(request.getParameter("Name"),request.getParameter("Email"),request.getParameter("Contact"),request.getParameter("Address"))) {
		coronaKit.setId(id);
		coronaKit.setPersonName(request.getParameter("Name"));
		coronaKit.setEmail(request.getParameter("Email"));
		coronaKit.setContactNumber(request.getParameter("Contact"));
		coronaKit.setTotalAmount(0);
		coronaKit.setDeliveryAddress(request.getParameter("Address"));
		coronaKit.setOrderDate(LocalDate.now().plusDays(3).toString());
		coronaKit.setOrderFinalized(true);
		
		orderSummary.setCoronaKit(coronaKit);
		try {
			products = kitDAO.getProductsList();
			view = "showproductstoadd.jsp";
		} catch (ServletException e) {
			request.setAttribute("errMsg", e.getMessage());
			view = "errorPage.jsp";
		}
		request.setAttribute("products", products);
		request.setAttribute("coronaKit", coronaKit);

		
		}else {
			request.setAttribute("errMsg", "</br> User details are invalid. </br> "
					+ "User Name should not be null and should be of text format and length should be less than or equal to 20. </br>"
					+ "User Email should not be null and should be of username@email.com format. </br>"
					+ "User Contact should not be null and should be of 10 digit number format and leading number cannot be 0. </br>"
					+ "User Address should be text format and should not be null.");
			view = "errorPage.jsp";
		}
		return view;
	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		return "newuser.jsp";
	}
	
	public boolean validations(String name, String email, String contact, String address) {
		boolean isValid = false;
		
		return (name!=null && name.length() <= 20 && validateEmail(email) && contact!=null && contact.matches("[1-9][0-9]{9}") && address!=null && address.length() <= 40);
	}
	
	public boolean validateEmail(String email){
		  return Pattern.matches("[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*", email);
		}

}