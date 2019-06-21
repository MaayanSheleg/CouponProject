package com.mbms.tables;

public class Tables {
/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
private static Database database = new Database();

public void CreateTables() {
}

//*this method calling insertcompany from caompnyDBDAO
public static void CreateAllTables() throws Exception {

	database.createCustomerTable();
	database.createCouponTable();
	database.createCompanyTable();
	database.createExpiredCouponTable();
	database.createCustomer_CouponTable();
	database.createCompany_CouponTable();
	

}

public static void DropAllTables() throws Exception {
	
	database.dropCustomer_CouponTable();
	database.dropCompany_CouponTable();
	database.dropCustomerTable();
	database.dropCouponTable();
	database.dropCompanyTable();
	database.dropExpiredCouponTable();
}


}
