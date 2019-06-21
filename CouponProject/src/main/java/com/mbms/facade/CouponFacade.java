package com.mbms.facade;

import java.sql.Date;
import java.util.Set;

import com.mbms.beans.Coupon;
import com.mbms.dbdao.CouponDBDao;
import com.mbms.enums.CouponType;

/**
 * @author Maytal & mayaan *
*/
public class CouponFacade {
	
	private CouponDBDao CouponDBDAO = new CouponDBDao();
	private Coupon coupon;

	public CouponFacade(Coupon C) {
		this.coupon = C;
		
	}

	public  CouponFacade() {
	}

	public void insertCoupon(Coupon coupon) throws Exception {
		CouponDBDAO.insertCoupon(coupon);
	}

	public void removeCoupon(Coupon coupon) throws Exception {

		CouponDBDAO.removeCoupon(coupon);

	}

	public void updateCoupon(Coupon coupon, long id, String title, Date start_date, Date end_date, int amount, CouponType type, String message, double price, String image) throws Exception {

		coupon.setID(id);
		coupon.setTitle(title);
		coupon.setStart_date(start_date);
		coupon.setEnd_date(end_date);
		coupon.setAmount(amount);
		coupon.setType(type);
		coupon.setMessage(message);
		coupon.setPrice(price);
		coupon.setImage(image);
	}

	public Coupon getTitle() {
		return getTitle();
	}

	public Set<Coupon> getAllCoupons() throws Exception {
		return CouponDBDAO.getAllCoupons();
	}

	
}
