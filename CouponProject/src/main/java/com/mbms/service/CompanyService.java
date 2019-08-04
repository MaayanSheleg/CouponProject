package com.mbms.service;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import javax.security.auth.login.LoginException;
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
import com.mbms.enums.ClientType;
import com.mbms.enums.CouponType;
import com.mbms.facade.AdminFacade;
import com.mbms.facade.CompanyFacade;
import com.mbms.main.CouponSystem;

@Path("company")
public class CompanyService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	private CouponSystem couponSystem;
	Gson gson = new Gson();

	public CompanyService() {
	}

		private CompanyFacade getFacade() {
			CompanyFacade company = null;
			company = (CompanyFacade)request.getSession(false).getAttribute("facade");
			return company;
		}

//	public CompanyFacade getFacade() throws Exception {
//		CompanyFacade companyFacade = (CompanyFacade)couponSystem.login("Fedrik", "fff1", ClientType.COMPANY);
//		return companyFacade;
//	}

	@POST
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createCoupon(Coupon coupon)throws Exception {
		CompanyFacade companyFacade = getFacade();
		try {
			coupon= companyFacade.insertCoupon(coupon);
			return new Gson().toJson(coupon);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	//Remove coupon
	@DELETE
	@Path("removeCoupon/{couponId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCoupon(@PathParam("couponId") long couponId) throws LoginException {

		String failMsg = "FAILED TO REMOVE A COUPON";
		try {
			CompanyFacade companyFacade = getFacade();
			Coupon coupon = null;

			coupon = companyFacade.getCoupon(couponId);
			companyFacade.removeCoupon(coupon);
			return "SUCCEED TO REMOVE COUPON";

		} catch (Exception e) {
			System.out.println(e);
		}
		return failMsg;
	}

	// UPDATE Coupon
	@POST
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateCoupon(@QueryParam("couponId") long ID, @QueryParam("endDate") Date end_Date,
			@QueryParam("price") double price) throws LoginException, Exception {
		CompanyFacade companyFacade = getFacade();
		try {
			Coupon coupon = companyFacade.getCoupon(ID);
			if(coupon!=null) {
				companyFacade.updateCoupon(coupon, end_Date, price);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//Get coupon
	@GET
	@Path("getCoupon/{couponId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCoupon(@PathParam("couponId") long couponId) throws Exception {
		try {
			CompanyFacade companyFacade = getFacade();
			Coupon coupon = companyFacade.getCoupon(couponId);
			return new Gson().toJson(coupon);
		}catch (Exception e){
			System.out.println(e);
		}
		return "FAILED GET COUPON";
	}

	// GET all coupons company
	@GET
	@Path("getAllCoupons/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllcoupons(@PathParam("companyId") long companyId) throws Exception {

		CompanyFacade companyFacade = getFacade();
		Company company = companyFacade.getCompany(companyId);
		try {
			Collection <Coupon> coupons = companyFacade.getAllCompanyCoupon(company);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "FAILED GET COUPONS";
	}

	// GET Coupon By type
	@GET
	@Path("getAllCouponsByType/{couponType}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllcouponsByType(@PathParam("couponType") CouponType couponType) throws Exception {

		CompanyFacade companyFacade = getFacade();

		try {
			List<Coupon> coupons = companyFacade.getCouponbyType(couponType);
			return new Gson().toJson(coupons);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "FAILED GET COUPONS BY TYPE";
	}

	// GET Coupon By Date
	@GET
	@Path("getCouponByDate/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCouponByDate(@PathParam("endDate") Date endDate) throws LoginException, Exception {
		CompanyFacade companyFacade = getFacade();
		try {
			List<Coupon> coupons = companyFacade.getCouponByDate(endDate);
			return new Gson().toJson(coupons);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.err.println("this company dont have coupons by thid end date : " + endDate);
		return null;
	}

	// GET Coupon By Price
	@GET
	@Path("getCouponByPrice/{Price}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCouponByPrice(@PathParam("Price") double price) throws LoginException, Exception {
		CompanyFacade companyFacade = getFacade();
		try {
			List<Coupon> coupons = companyFacade.getCouponByPrice(price);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.err.println("this company dont have coupons with thid Price : " + price);
		return null;
	}

}
