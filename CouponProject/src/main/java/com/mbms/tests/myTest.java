package com.mbms.tests;

import com.mbms.beans.Company;
import com.mbms.beans.Coupon;
import com.mbms.enums.ClientType;
import com.mbms.enums.CouponType;
import com.mbms.facade.AdminFacade;
import com.mbms.facade.CompanyFacade;
import com.mbms.main.CouponSystem;
import com.mbms.main.Utils;

public class myTest {

	private static CouponSystem couponSystem;
	
	public static void main(String[] args) throws Exception {
	

		couponSystem = CouponSystem.getInstance();

		AdminFacade adminFacade = new AdminFacade();
		Company companyTestService = new Company(101010, "service", "winwin", "service@service.com");
		CompanyFacade companyFacade = new CompanyFacade();
		Coupon couponService = new Coupon("Service", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 12, CouponType.Electricity, "please work", 10, "image");
		
		try {
			adminFacade.insertCompany(companyTestService);
			companyFacade = (CompanyFacade)couponSystem.login("service", "winwin", ClientType.COMPANY);
			companyFacade.insertCoupon(couponService);
		} catch (Exception e) {
			System.out.println("failed connect as company");
			e.printStackTrace();
		}

	}

}
