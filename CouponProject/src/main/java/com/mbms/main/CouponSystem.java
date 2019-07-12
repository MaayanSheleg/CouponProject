package com.mbms.main;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.security.auth.login.LoginException;

import com.mbms.Exceptions.CouponException;
import com.mbms.beans.Company;
import com.mbms.beans.Customer;
import com.mbms.dao.Client;
import com.mbms.dbdao.CompanyDBDAO;
import com.mbms.dbdao.CustomerDBDAO;
import com.mbms.enums.ClientType;
import com.mbms.facade.AdminFacade;
import com.mbms.facade.CompanyFacade;
import com.mbms.facade.CustomerFacade;

/**
 * @author Maytal & mayaan *
*/
public class CouponSystem {
	
	private static CouponSystem instance;
	private static ThreadsDailyExpiredCoupons DailyTask;
	private	static Thread thread;

	
	private CouponSystem(ThreadsDailyExpiredCoupons dailyTask, Thread thread, Connection connection) {
		DailyTask = new ThreadsDailyExpiredCoupons();
		thread = new Thread(DailyTask);
		thread.start();
	}

	public static CouponSystem getInstance() throws Exception {
		if (instance == null) {
			try {
				instance = new CouponSystem(DailyTask, thread, null);
				System.out.println("CouponSystem start");

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}

		}

		return instance;
	}

	public static Client login(String name, String password, ClientType clientType) throws Exception {

		Client client =  null;

		switch (clientType) {

		case ADMIN:
			if(name.equals("admin") && password.equals("1234")) {
			client = new AdminFacade();
			}
			break;

		case COMPANY:
			
			CompanyDBDAO companyDBDAOAO = new CompanyDBDAO();

			Collection<Company>companies = companyDBDAOAO.getAllCompanys();
			Iterator<Company> i = companies.iterator();
			
			while (i.hasNext()) {
				Company current = i.next();
				if (current.getComp_name().equals(name) && current.getPassword().equals(password)) {
					CompanyFacade companyFacade = new CompanyFacade(current);
					return companyFacade;
				}else if (!i.hasNext()) {
					throw new Exception("Login Falied! Invalid User or Password!");
				}
			}
			break;

		case CUSTOMER:
		
			CustomerDBDAO customerDAO = new CustomerDBDAO();
			
			Collection<Customer>customers = customerDAO.getAllCustomer();
			Iterator<Customer> c = customers.iterator();
			
			while (c.hasNext()) {
				Customer current2 = c.next();
				if (current2.getCust_name().equals(name) && current2.getPassword().equals(password)) {
					CustomerFacade customerFacade = new CustomerFacade(current2);
					return customerFacade;
				}else if (!c.hasNext()) {
					throw new LoginException("Login Falied! Invalid User or Password!");
				}
			}
				break;

		default:
			throw new CouponException("login failed");
			
		}
	
//		client = client.login(name, password, clientType);
//		System.out.println(name);
//		System.out.println(password);
//		System.out.println(clientType);
//	System.out.println(111111);
//		System.out.println(client);
		
		if(client==null) {
			
			throw new CouponException("Login failed! incorect password");
		}else {
			return client;
		}
	}
	
	
	public void shutdown() throws Exception
	{
	/*Shutdown system is a method that close all the connectionPool,
	*and stop the daily task (the expired coupon thread) 
	*/
		DailyTask.stopTask();

		try {
			ConnectionPool connectionPool = ConnectionPool.getInstance();

			try {
				connectionPool.closeAllConnections();

			} catch (Exception e) {
				throw new Exception("connection failed");
			}

		} catch (Exception e) {

			throw new Exception("shut down failed");
		}
	}	
}

