//package com.mbms.tests;
//
//import java.util.Set;
//
//import com.mbms.beans.Company;
//import com.mbms.beans.Coupon;
//import com.mbms.beans.Customer;
//import com.mbms.dao.Client;
//import com.mbms.enums.ClientType;
//import com.mbms.enums.CouponType;
//import com.mbms.facade.AdminFacade;
//import com.mbms.facade.CompanyFacade;
//import com.mbms.facade.CustomerFacade;
//import com.mbms.main.CouponSystem;
//import com.mbms.main.Utils;

//
//
///**
// * 
// */
///**
// * @author Maytal & mayaan *
//*/
//public class usersTest {
//
//	//Create company object
//	static Company company1 = new Company(963852, "Fedrik", "z1z1", "Fedrik@Fedrik.com");
//	static Company company2 = new Company(258963, "Mars", "m1m1", "mars@mars.com");		
//	static Company company3 = new Company(1234567, "Coca Cola", "1234", "cocacola@gmail.com");
//	static Company company4 = new Company(2345678, "Google", "4444", "google@gmail.com");
//	static Company company5 = new Company(3456789, "Apple", "m123", "apple@gmail.com");
//
//	static Coupon coupon11 = new Coupon("Fedrik 50% less", Utils.getCurrentDate(), Utils.getExpiredDate(), 5, CouponType.Resturans, "Fedrik coupon", 20.5, "image.png");
//	static Coupon coupon12 = new Coupon("Fedrik for free", Utils.getCurrentDate(), Utils.getExpiredDate(), 6, CouponType.Resturans, "Fedrik coupon", 10.3, "image.png");
//	static Coupon coupon13 = new Coupon("Fedrik discount", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 7, CouponType.Resturans, "Fedrik coupon", 50.2, "image.png");
//	static Coupon coupon14 = new Coupon("Fedrik deal", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 8, CouponType.Resturans, "Fedrik coupon", 10.4, "image.png");
//	static Coupon coupon15 = new Coupon("Fedrik meal", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 9, CouponType.Resturans, "Fedrik coupon", 5.5, "image.png");
//
//	static Coupon coupon21 = new Coupon("Mars 50% less", Utils.getCurrentDate(), Utils.getExpiredDate(), 10, CouponType.Food, "Mars coupon", 20.5, "image.png");
//	static Coupon coupon22 = new Coupon("Mars for free", Utils.getCurrentDate(), Utils.getExpiredDate(), 12, CouponType.Food, "Mars coupon", 3.2, "image.png");
//	static Coupon coupon23 = new Coupon("Mars discount", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 5, CouponType.Food, "Mars coupon", 4.4, "image.png");
//	static Coupon coupon24 = new Coupon("Mars deal", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 4, CouponType.Food, "Mars coupon", 5.5, "image.png");
//	static Coupon coupon25 = new Coupon("Mars- new choclate deal", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 3, CouponType.Food, "Mars coupon", 20, "image.png");
//
//	static Coupon coupon31 = new Coupon("Coca Cola- cola 50% less", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 10, CouponType.Health, "Coca Cola coupon", 20.5, "image.png");
//	static Coupon coupon32 = new Coupon("Coca Cola for free", Utils.getCurrentDate(), Utils.getExpiredDate(), 12, CouponType.Health, "Coca Cola coupon", 3.2, "image.png");
//	static Coupon coupon33 = new Coupon("Coca Cola discount", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 5, CouponType.Health, "Coca Cola coupon", 4.4, "image.png");
//	static Coupon coupon34 = new Coupon("Coca Cola deal", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 4, CouponType.Health, "Coca Cola coupon", 5.5, "image.png");
//	static Coupon coupon35 = new Coupon("new cola deal", Utils.getCurrentDate(), Utils.getExpiredDateNextDay(), 3, CouponType.Health, "Coca Cola coupon", 20, "image.png");
//
//	static Coupon coupon41 = new Coupon("Google around the world", Utils.getCurrentDate(), Utils.getExpiredDate(), 11, CouponType.Traveling, "Google coupon", 1.90, "image.png");
//	static Coupon coupon42 = new Coupon("Google race", Utils.getCurrentDate(), Utils.getExpiredDate(), 20, CouponType.Traveling, "Google coupon", 10.90, "image.png");
//	static Coupon coupon43 = new Coupon("Google Bicycle", Utils.getCurrentDate(), Utils.getExpiredDate(), 81, CouponType.Traveling, "Google coupon", 11.90, "image.png");
//	static Coupon coupon44 = new Coupon("Google city run", Utils.getCurrentDate(), Utils.getExpiredDate(), 71, CouponType.Traveling, "Google coupon", 4.90, "image.png");
//	static Coupon coupon45 = new Coupon("Google traveling", Utils.getCurrentDate(), Utils.getExpiredDate(), 41, CouponType.Traveling, "Google coupon", 8, "image.png");
//
//	static Coupon coupon51 = new Coupon("Apple store coupon", Utils.getCurrentDate(), Utils.getExpiredDate(), 11, CouponType.Electricity, "Apple coupon", 1.90, "image.png");
//	static Coupon coupon52 = new Coupon("Iphone 50% less", Utils.getCurrentDate(), Utils.getExpiredDate(), 20, CouponType.Electricity, "Apple coupon", 10.90, "image.png");
//	static Coupon coupon53 = new Coupon("Apple deal", Utils.getCurrentDate(), Utils.getExpiredDate(), 81, CouponType.Electricity, "Apple coupon", 11.90, "image.png");
//	static Coupon coupon54 = new Coupon("Apple store discount", Utils.getCurrentDate(), Utils.getExpiredDate(), 71, CouponType.Electricity, "Apple coupon", 4.90, "image.png");
//	static Coupon coupon55 = new Coupon("headphones 2+1", Utils.getCurrentDate(), Utils.getExpiredDate(), 41, CouponType.Electricity, "Apple coupon", 8, "image.png");
//
//	//Create Customer object
//	static Customer customer1= new Customer( "Johann Sebastian Bach", "kfkf99");
//	static Customer customer2= new Customer( "Wolfgang Amadeus Mozart", "kfkf99");
//	static Customer customer3 = new Customer("Ludwig van Beethoven","m777kkj");
//	static Customer customer4 = new Customer("Johannes Brahms", "b888k");
//	static Customer customer5 = new Customer("Franz Schubert", "l44kk4");
//
//	public static void adminTestStart() throws Exception {
//
//		String name;
//		String password;
//		Client client;
//		ClientType type = Client.clientType.admin;
//		Set<Company> companies;
//		AdminFacade admin = new AdminFacade();
//
//		System.out.println("**************Admin Test***************");
//
////		//(1) bad login//
////		name= "admin";
////		password="12348";
////		System.out.println("(1) checking bad login as admin:");
//
////		client=CouponSystem.getInstance().login(name,password,type);
//
//		name= "admin";
//		password="1234";
//		
	//	client=CouponSystem.getInstance().login(name,password,type);
//
//			// (2)get Admin facade
//			admin=(AdminFacade)client;
//
//			System.out.println("(3) Add companies");
//			admin.insertCompany(company1);
//			admin.insertCompany(company2);
//			admin.insertCompany(company3);
//			admin.insertCompany(company4);
//			admin.insertCompany(company5);
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(4) Get Company");
//			admin.getCompany(company1);
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(5) Get All companies");
//			System.out.println(admin.getAllCompanys());
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(6) Add customers");
//			admin.insertCustomer(customer1);
//			admin.insertCustomer(customer2);
//			admin.insertCustomer(customer3);
//			admin.insertCustomer(customer4);
//			admin.insertCustomer(customer5);
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(7) Get  all Customers");
//			System.out.println(admin.getAllCustomers());
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(7) Get Customer");
//			admin.getCustomerDetails(customer2);
//
//
//	}
//
//	public static void companyTestStart () throws Exception{
//
//		String name;
//		String password;
//		ClientType type = ClientType.company;
//		Client client;
//		CompanyFacade companyFacade = new CompanyFacade();
//
//		System.out.println("**Company Test***");
//
////	//	(2) BAD LOGIN
////		name= "Apple";
////		password="1234";
////		client=CouponSystem.getInstance().login(name,password,type);
//
//		//(3) good login
//		name= "Apple";
//		password="m123";
//		client=CouponSystem.getInstance().login(name,password,type);
//
//		try{
//			// (**) login as company Apple 
//			System.out.println("(**)login as company Apple:");
//			//	client=CouponSystem.getInstance().login(company5.getComp_name(),company5.getPassword(),type);
//
//			System.out.println("(11) Insert copons");
//
//
//			companyFacade.insertCoupon(coupon11, company1);
//			companyFacade.insertCoupon(coupon12, company1);
//			companyFacade.insertCoupon(coupon13, company1);
//			companyFacade.insertCoupon(coupon14, company1);
//			companyFacade.insertCoupon(coupon15, company1);
//
//			companyFacade.insertCoupon(coupon21, company2);
//			companyFacade.insertCoupon(coupon22, company2);
//			companyFacade.insertCoupon(coupon23, company2);
//			companyFacade.insertCoupon(coupon24, company2);
//			companyFacade.insertCoupon(coupon25, company2);
//
//			companyFacade.insertCoupon(coupon31, company3);
//			companyFacade.insertCoupon(coupon32, company3);
//			companyFacade.insertCoupon(coupon33, company3);
//			companyFacade.insertCoupon(coupon34, company3);
//			companyFacade.insertCoupon(coupon35, company3);
//
//			companyFacade.insertCoupon(coupon41, company4);
//			companyFacade.insertCoupon(coupon42, company4);
//			companyFacade.insertCoupon(coupon43, company4);
//			companyFacade.insertCoupon(coupon44, company4);
//			companyFacade.insertCoupon(coupon45, company4);
//
//			companyFacade.insertCoupon(coupon51, company5);
//			companyFacade.insertCoupon(coupon52, company5);
//			companyFacade.insertCoupon(coupon53, company5);
//			companyFacade.insertCoupon(coupon54, company5);
//			companyFacade.insertCoupon(coupon55, company5);
//
//			System.out.println("(14) Get copons");
//			System.out.println(companyFacade.getAllCompanyCoupon(company5));
//
//
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//
//	}
//
//	public static void customerTest() throws Exception{
//
//		String name;
//		String password;
//		ClientType type=  ClientType.customer;
//		Client client;
//		CustomerFacade customerFacade = new CustomerFacade();
//
//		System.out.println("**Customer Test***");
//		System.out.println("(**) login as costumer:");
//	
//		//(2) BAD LOGIN
////		System.out.println("(**) bad login:");
////		name= "Franz Schubert";
////		password="l443333kk4";
////		client=CouponSystem.getInstance().login(name,password,type);
//
//
//		//(3) good login
//		System.out.println("(**) good login:");
//		name= "Johann Sebastian Bach";
//		password="y7y7y7y";
//		client=CouponSystem.getInstance().login(name,password,type);
//
//		try{
//			//(19) login as costumer:
//			System.out.println("(**) login as costumer:");
//			client=CouponSystem.getInstance().login(name,password,type);
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(11) Buy copons");
//			//customerFacade.buyCoupon(customer1, coupon11);
//			customerFacade.buyCoupon(customer1, coupon21);
//			customerFacade.buyCoupon(customer1, coupon31);
//			customerFacade.buyCoupon(customer1, coupon41);
//			customerFacade.buyCoupon(customer1, coupon51);
//
//
//		//	customerFacade.buyCoupon(customer2, coupon12);
//			customerFacade.buyCoupon(customer2, coupon22);
//			customerFacade.buyCoupon(customer2, coupon32);
//			customerFacade.buyCoupon(customer2, coupon42);
//			customerFacade.buyCoupon(customer2, coupon42);
//
//
//		//	customerFacade.buyCoupon(customer3, coupon13);
//			customerFacade.buyCoupon(customer3, coupon23);
//			customerFacade.buyCoupon(customer3, coupon33);
//			customerFacade.buyCoupon(customer3, coupon43);
//
//			customerFacade.buyCoupon(customer4, coupon14);
//			customerFacade.buyCoupon(customer4, coupon24);
//			customerFacade.buyCoupon(customer4, coupon34);
//			customerFacade.buyCoupon(customer4, coupon44);
//
//			customerFacade.buyCoupon(customer5, coupon15);
//			customerFacade.buyCoupon(customer5, coupon25);
//			customerFacade.buyCoupon(customer5, coupon35);
//			customerFacade.buyCoupon(customer5, coupon45);
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(11) get All Purchased Coupons By Customer");
//			System.out.println(customerFacade.getAllPurchasedCouponsByCustomer(customer5));
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(11) get All Purchased Coupons By type");
//			System.out.println(customerFacade.getAllPurchasedCouponsByType(customer2, CouponType.Traveling));
//			System.out.println(customerFacade.getAllPurchasedCouponByPrice(customer1, 10));
//
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//
//	}
//
//	public static void adminTestUpdates() throws Exception {
//
//		AdminFacade admin = new AdminFacade();
//
//		try {
//
//			System.out.println("(5) Update Company");
//			admin.updateCompany(company1, "54321o", "test@test.com");
//			admin.updateCompany(company2, "4444", "T@T.com");
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(10) Update Customer");
//			admin.updateCustomer(customer1, "y7y7y7y");
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//
//
//	}
//
//	public static void companyTestUpdates() throws Exception {
//
//		CompanyFacade companyFacade = new CompanyFacade();
//
//		try {
//
//			System.out.println("(13) Update copons");
//			companyFacade.updateCoupon(coupon11, Utils.getExpiredDatelastmonth(), 55.55);
//			companyFacade.updateCoupon(coupon12, Utils.getExpiredDatelastmonth(), 55.55);
//			companyFacade.updateCoupon(coupon13, Utils.getExpiredDatelastmonth(), 55.55);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	public static void adminTestRemove() throws Exception {
//
//		AdminFacade admin = new AdminFacade();
//
//		try {
//
//			System.out.println("(9) Remove customer");
//			admin.removeCustomer(customer5);
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//			System.out.println("(4) Remove company");
//			admin.removeCompany(company4);
//
//			System.out.println("--------------------------------------------------------------------- ");
//
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//
//	}
//
//	public static void companyTestEnd() throws Exception{
//
//		CompanyFacade companyFacade = new CompanyFacade();		
//
//		try {
//			System.out.println("(12) Remove copons");
//			companyFacade.removeCoupon(coupon55);
//
//
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}
//
//	}
//
//
//
//}
//
