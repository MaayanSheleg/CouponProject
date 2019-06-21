package com.mbms.main;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mbms.tables.Database;
import com.mbms.tables.Tables;



/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public class Test {
	
	public static void main(String[] args) throws Exception {

		// ����� �� �'���- ��������
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		Connection con = DriverManager.getConnection(Database.getDBUrl());
		
		CouponSystem.getInstance().getInstance();

		Tables.CreateAllTables();

		//usersTest.adminTestStart();
//		usersTest.companyTestStart();
//		usersTest.customerTest();
//		
//		usersTest.adminTestUpdates();
//		usersTest.companyTestUpdates();
//
//		usersTest.companyTestEnd();
//		usersTest.adminTestRemove();

//
		CouponSystem.getInstance().shutdown();
//		
//		Tables.DropAllTables();

	}
}
