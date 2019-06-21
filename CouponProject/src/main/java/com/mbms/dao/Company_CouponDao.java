package com.mbms.dao;

import java.util.List;

import com.mbms.beans.Company;
import com.mbms.beans.Coupon;

public interface Company_CouponDao {
	
	void insertCompany_Coupon(Company company, Coupon coupon) throws Exception;

	void removeCompany_Coupon(Coupon coupon) throws Exception;
	
	void removeCoupons(Coupon coupon) throws Exception;
	
	public long getMaxCouponId() throws Exception;
	
	public List <Long>  getCouponsByCompanyId(long compid) throws Exception;

}
