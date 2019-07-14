package com.mbms.dao;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Set;

import com.mbms.beans.Coupon;
import com.mbms.beans.Customer;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public interface CustomerDao {
	//* this interface are methods that we wan to implement in customerdbda 

	void insertCustomer(Customer customer) throws Exception;
	//* method that can insert a customer to the DB

	void updateCustomer(Customer customer) throws Exception;
	//* method that can update a customer from the DB

	Set<Customer> getAllCustomers() throws Exception;
	//* method that can retrieve a customer from the DB by id

	Customer getCustomer(Customer customer) throws Exception;
	//* method that can retrieve all customers from the DB 

	boolean ifCustomerNameExists(String custName) throws Exception;
	//*method that check if customer exists

	Customer extractCustomerFromResultSet1(ResultSet result) throws Exception;

	Collection<Customer> getAllCustomer() throws Exception;

	void removeCustomerById(long id) throws Exception;

	void removeAllPurchasedCustomerCoupons(long id) throws Exception;

	void customerPurchaseCoupon(Coupon coupon, Customer customer) throws Exception;
}


