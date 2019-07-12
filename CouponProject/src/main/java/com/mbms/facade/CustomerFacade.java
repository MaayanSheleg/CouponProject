package com.mbms.facade;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mbms.beans.Coupon;
import com.mbms.beans.Customer;
import com.mbms.dao.Client;
import com.mbms.dbdao.CouponDBDao;
import com.mbms.dbdao.CustomerDBDAO;
import com.mbms.dbdao.Customer_CouponDBDao;
import com.mbms.enums.ClientType;
import com.mbms.enums.CouponType;
import com.mbms.main.ConnectionPool;
import com.mbms.main.Utils;

/**
 * @author Maytal & mayaan *
*/
public class CustomerFacade implements Client {

	private CustomerDBDAO customerDBDAO = new CustomerDBDAO(); 
	private Customer customer;
	private Customer_CouponDBDao customer_CouponDBDao =  new Customer_CouponDBDao();
	private CouponDBDao couponDBDao = new CouponDBDao();
	private Customer_CouponDBDao customer_CouponDAO = new Customer_CouponDBDao();
	private LocalDate now = LocalDate.now();
	
		//full cTor for the CustomerFacade
		public CustomerFacade(Customer C) {
			this.customer = C;
		}

		//empty cTor for CustomerFacade
		public  CustomerFacade() {
		}

		// this method the customer prchec a coupon and the method updated the coupon_customer table
		public void buyCoupon(Customer temp, Coupon titi) throws Exception {
			
			Customer customer= new Customer();
			customer= customerDBDAO.getCustomer(temp);
			
			Coupon coupon = new Coupon();
			Coupon coco = new Coupon();
			coupon = titi;
			coco =couponDBDao.whatCouponid(titi);
			long id = coco.getID();
			coupon.setID(id);
			
			int amount = coupon.getAmount();
			long customerId = customer.getId();
			long couponId= coupon.getID();
			
			LocalDate date= Utils.convertToLocalDateViaMilisecond((Date) coupon.getEnd_date());			
			System.out.println(customer);
			try {
				if (amount > 1) {
					if (coupon.getEnd_date().getTime() >= Utils.getCurrentDate().getTime()) {		
							
						customer_CouponDBDao.insertCustomer_Coupon(customerId, couponId);
						
						coupon.setAmount(coupon.getAmount() -1);
						couponDBDao.updateCoupon(coupon);
							System.out.println( "customer: "+customer.getCust_name()  + ", buy new coupon: " + coupon.getTitle());
						}}}
					catch (Exception e) {
				System.out.println(e);
					}
				
				} 
		
		public List<Coupon> getAllPurchasedCouponsByCustomer(Customer temp) throws Exception {
			
			Customer customer= new Customer();
			customer= customerDBDAO.getCustomer(temp);
			
			long custId= customer.getId();
					
			List<Long> customersCouponsbyid = customer_CouponDAO.getCouponsByCustomerId(custId);

			List<Coupon> nameCoupons = new ArrayList<Coupon>();

			for (Long id : customersCouponsbyid) {

				nameCoupons.add(couponDBDao.getCoupon(id));

			}
			return nameCoupons;
			
		}
	
		public List<Coupon> getAllPurchasedCouponsByType(Customer customer3, CouponType type) throws Exception {
			// Open a connection from the connection pool class
			List<Coupon> couponByType = new ArrayList<Coupon>();
			try {

				Connection conn = ConnectionPool.getInstance().getConnection();

			} catch (Exception e) {

				throw new Exception("The Connection is faild");
			}
			
			try {
			List<Coupon> coupons = getAllPurchasedCouponsByCustomer(customer3);

			for (Coupon coupon : coupons) {

				if (coupon.getType().equals(type)) {

					couponByType.add(coupon);
				}
			//	System.out.println("@" +couponByType);
			}
		} catch (Exception e) {

			throw new Exception("update customer failed");}
			
		finally { // finally block used to close resources

			try {
				Connection conn = ConnectionPool.getInstance().getConnection();
				java.sql.Statement stmt = conn.createStatement();
				if (stmt != null) {
					ConnectionPool.getInstance().returnConnection(conn);
				}

			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}
			try {
				Connection conn = ConnectionPool.getInstance().getConnection();
				if (conn != null) {
					ConnectionPool.getInstance().returnConnection(conn);
				}

			} catch (Exception e) {

				throw new Exception("The close connection action faild");
			}
		}
			return couponByType;
		
		}

		public List<Coupon> getAllPurchasedCouponByPrice(Customer customer3, double price) throws Exception{
	
			List<Coupon> coupons = getAllPurchasedCouponsByCustomer(customer3);
			
			List<Coupon> couponByPrice = new ArrayList<Coupon>();

			for (Coupon coupon : coupons) {

				if (coupon.getPrice() <= price) {

					couponByPrice.add(coupon);
				}
			}
			
			return couponByPrice;

		}

		@Override
		public Client login(String user, String password, ClientType clientType) {
			return null;
		}


		public List<Coupon> getCouponByPrice (double price) throws Exception{
			List<Coupon> coupons = getAllCustomerCoupon(this.customer);
			List<Coupon> couponByPrice = new ArrayList<Coupon>();
			try {
				for (Coupon coupon : coupons) {
					if (coupon.getPrice() <= price) {
						couponByPrice.add(coupon);
					}}}
			catch (Exception e) {
				System.out.println(e);
			}
			return couponByPrice;
		}

		public List<Coupon> getAllCustomerCoupon(Customer customer) throws Exception {
			long customerId= customer.getId();
			List<Long> coupons = customerDBDAO.getCustomerCoupons(customerId);
			List<Coupon> allcoupons = new ArrayList<Coupon>();
			for (Long id : coupons) {
				allcoupons.add(couponDBDao.getCoupon(id));
			}
			return allcoupons;
		}

		public List<Coupon> getCouponbyType(CouponType type) throws Exception{
			List<Coupon> coupons = getAllCustomerCoupon(this.customer);
			List<Coupon> couponByType = new ArrayList<Coupon>();
			try {
				for (Coupon coupon : coupons) {
					if (coupon.getType().equals(type)) {
						couponByType.add(coupon);
					}}}
			catch (Exception e) {
				System.out.println(e);
			}
			return couponByType;
		}

		public List <Long> getAllpurchasedCoupons(long customerId) throws Exception{
			return customerDBDAO.getCustomerCoupons(customerId);
		}
}	
