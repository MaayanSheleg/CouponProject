package com.mbms.service;

import java.sql.Date;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.mbms.beans.Company;
import com.mbms.beans.Coupon;
import com.mbms.dbdao.CouponDBDao;
import com.mbms.enums.CouponType;
import com.mbms.facade.AdminFacade;
import com.mbms.facade.CompanyFacade;

@Path("@company")
public class CompanyService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	Gson gson = new Gson();

	public CompanyService() {
	}

	private CompanyFacade getFacade() {
		CompanyFacade company = null;
		company = (CompanyFacade)request.getSession(false).getAttribute("facade");
		return company;
	}

	@POST
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createCoupon(Coupon coupon, Company companyA) {


		String failMsg = "FAILED TO ADD A NEW Coupon " ;

		try {

			CompanyFacade company = getFacade();
			
			if (coupon != null) {

				company.insertCoupon(coupon, companyA);
				return "SUCCEED TO ADD A NEW COUPON: " + coupon.getTitle() + ", id = " + coupon.getID();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return failMsg;
	}

	@DELETE
	@Path("removeCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCoupon(Coupon coupon) {

		String failMsg = "FAILED TO REMOVE A COUPON";
		CompanyFacade companyFacade = getFacade();

		try {

			if (coupon != null) {
				companyFacade.removeCoupon(coupon);
				return "SUCCEED TO REMOVE COUPON";
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return failMsg;
	}

	@POST
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCoupon(Coupon coupon , java.sql.Date end_date , double price) {


		try {
			
			CompanyFacade company = getFacade();

			if (coupon != null) {
				
				company.updateCoupon(coupon, end_date, price);
				System.out.println("SUCCEED TO UPDATE A COUPON");
				
			}
		} catch(Exception e){
			System.out.println(e);
		}

		return "FAILED TO UPDATE COUPON";
	}

	@GET
	@Path("getCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCoupon(@PathParam("couponId") long Id) throws Exception {

		try {
			CompanyFacade companyFacade = getFacade();
			Coupon coupon = companyFacade.getCoupon(Id);
			return new Gson().toJson(coupon);

		}catch (Exception e){
			System.out.println(e);
		}

		return "FAILED GET COUPON";
	}

	@GET
	@Path("getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllcoupons(@PathParam("companyId") long id, Company companyA) {

		CompanyFacade company = getFacade();

		try {
			Collection<Coupon> coupons = company.getAllCompanyCoupon(companyA);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "FAILED GET COUPONS";
	}

	@GET
	@Path("getAllCouponsByType")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllcouponsByType(@PathParam("companyId") long id, @QueryParam("couponType") CouponType type, Company companyA) {

		CompanyFacade company = getFacade();

		try {
			Collection<Coupon> coupons = company.getCouponbyType(companyA, type);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "FAILED GET COUPONS BY TYPE";
	}

	@GET
	@Path("getAllCouponsByDate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllcouponsByDate(@PathParam("companyId") long id, @QueryParam("couponEndDate") Date end_date, Company companyA) {

		CompanyFacade company = getFacade();

		try {
			Collection<Coupon> coupons = company.getCouponbyDate(companyA, end_date);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "FAILED GET COUPONS BY DATE";
	}

	@GET
	@Path("getAllCouponsByPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllcouponsByPrice(@PathParam("companyId") long id, @QueryParam("couponPrice") double price, Company companyA) {

		CompanyFacade company = getFacade();

		try {
			Collection<Coupon> coupons = company.getCouponbyprice(companyA, price);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "FAILED GET COUPONS BY PRICE";
	}
	
}
