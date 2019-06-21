package com.mbms.dao;

import java.util.List;

import com.mbms.beans.Coupon;
import com.mbms.beans.Customer;


/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public interface Customer_CouponDao {

	void insertCustomer_Coupon(long cutomerid, long couponid) throws Exception ;

	public void removebyCustomerCoupon(Customer customer) throws Exception;

	public void removebyCouponCustomer(Coupon coupon) throws Exception;

	public List<Long> getCouponsByCustomerId(long customerId) throws Exception;
}
