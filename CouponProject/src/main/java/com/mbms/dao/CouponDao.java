package com.mbms.dao;

import java.util.Set;

import com.mbms.beans.Coupon;
import com.mbms.beans.ExpiredCoupon;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
 */
public interface CouponDao {

	void insertCoupon(Coupon coupon) throws Exception;
	void removeCoupon(Coupon coupon) throws Exception;
	void updateCoupon(Coupon coupon) throws Exception;
	Coupon whatCouponid(Coupon coupon) throws Exception ;
	Coupon getCoupon(long id) throws Exception;
	Set<Coupon> getAllCoupons() throws Exception;
	void insertExpiredCoupon(Coupon coupon) throws Exception;
	ExpiredCoupon getexpiredCoupon(long id) throws Exception;
	public Set<ExpiredCoupon> getAllExpiredCoupon() throws Exception;
	boolean ifCouponNameExists(Coupon coupon) throws Exception;

}