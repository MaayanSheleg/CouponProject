package com.mbms.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.mbms.beans.Coupon;
import com.mbms.beans.Customer;
import com.mbms.enums.CouponType;
import com.mbms.facade.AdminFacade;
import com.mbms.facade.CustomerFacade;

@Path("customer")
public class CustomerService{

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	Gson gson = new Gson();
	
	public CustomerService() {
	}

	private CustomerFacade getFacade() {		
		CustomerFacade customer = null;
		customer = (CustomerFacade)request.getSession(false).getAttribute("facade");
		return customer;
	}
	
//	@POST
//	@Path("purchaseCoupon")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String purchaseCoupon(Customer customer ,Coupon coupon)	{
//
//		CustomerFacade customerFacade = getFacade();
//		String failMsg = "FAILED TO purchase coupon";
//
//		try {
//			if(customer != null) {
//				if (coupon != null) {
//					customerFacade.buyCoupon(customer, coupon);
//					return "THE CUSTOMER: " + customer.getCust_name() + "SUCCEED TO PURCHASE COUPON: " + coupon.getTitle();
//				}
//			}
//		} catch (Exception e) {
//			System.out.println();
//		}
//		return failMsg;
//	}
//
//	@GET
//	@Path("getPurchaseCoupon/{customerId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getPurchaseCoupon(@PathParam("customerId") long id)  {
//
//		CustomerFacade customerFacade = getFacade();
//		String failMsg = "FAILED TO get purchase coupon";
//		try {
//			 customerFacade.buyCoupon(temp, titi);
//			if (customer != null) {
//				return new Gson().toJson(customer);				
//			}
//			List<Coupon> buycoupons = customerFacade.getAllPurchasedCouponsByCustomer(customer);
//			return new Gson().toJson(buycoupons);				
//		
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//		return "FAILED TO GET CUSTOMER";
//	}

//	@GET
//	@Path("getCoupontype")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllPurchasedCouponsByType(@PathParam("customerId") long id, CouponType type)  {
//
//		CustomerFacade customerFacade = getFacade();
//		String failMsg = "FAILED TO get coupon by type";
//		AdminFacade admin = getAdmin();
//		try {
//			Customer customer = admin.getCustomer(id);
//			if (customer != null) {
//				return new Gson().toJson(customer);				
//			}
//			
//			List<Coupon> couponstype = customerFacade.getAllPurchasedCouponsByType( customer,type);
//			
//			return new Gson().toJson(couponstype);				
//		
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//		return "FAILED TO GET CUSTOMER";
//	}
//
//	@GET
//	@Path("getCouponbyprice")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String  getAllPurchasedCouponByPrice(@PathParam("customerId") long id, double price)  {
//
//		CustomerFacade customerFacade = getFacade();
//		String failMsg = "FAILED TO get coupon by type";
//		AdminFacade admin = getAdmin();
//		try {
//			Customer customer = admin.getCustomer(id);
//			if (customer != null) {
//				return new Gson().toJson(customer);				
//			}
//			
//			List<Coupon> couponsprice = customerFacade.getAllPurchasedCouponByPrice( customer,price);
//			
//			return new Gson().toJson(couponsprice);				
//		
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//		return "FAILED TO GET CUSTOMER";
//	}

}