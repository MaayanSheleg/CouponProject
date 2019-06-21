package com.mbms.main;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import com.mbms.beans.Coupon;
import com.mbms.dbdao.Company_CouponDBDao;
import com.mbms.dbdao.CouponDBDao;
import com.mbms.dbdao.Customer_CouponDBDao;

/**
 * @author Maytal & mayaan *
*/
public class ThreadsDailyExpiredCoupons implements Runnable {

	private boolean quit = false;
	private LocalDate now = LocalDate.now();
	private static int SECONDS = 24*60*60*1000;
	private CouponDBDao couponDBDao = new CouponDBDao();
	private Company_CouponDBDao company_CouponDBDao = new Company_CouponDBDao();
	private Customer_CouponDBDao customer_CouponDBDao= new Customer_CouponDBDao();

	
	public void DailyExpiredCoupons() throws Exception {
		
		Set<Coupon> set = couponDBDao.getAllCoupons();

		for (Coupon c : set) {
			
			LocalDate date= Utils.convertToLocalDateViaMilisecond((java.sql.Date) c.getEnd_date());		
			//	System.out.println(c.getEnd_date());
	

			try {
				if (date.isBefore(now)) {
					company_CouponDBDao.removeCompany_Coupon(c);
					customer_CouponDBDao.removebyCouponCustomer(c);
					couponDBDao.insertExpiredCoupon(c);
					couponDBDao.removeCoupon(c);
				}
			}catch (Exception e) {
				System.out.println(e);			}
		}
	}

	public synchronized void run() {
		while (!quit) {
			try {
				this.DailyExpiredCoupons();
				Thread.sleep(SECONDS);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void stopTask() {
		quit = true;
		System.out.println("auto task stop");
	}


}
