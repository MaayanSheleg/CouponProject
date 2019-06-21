package com.mbms.dao;

import java.sql.ResultSet;
import java.util.Collection;

import com.mbms.beans.Company;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public interface CompanyDao {
	
	//* this interface are a methods that we want to implement in companydbdao

		void insertCompany(Company company) throws Exception;
		//* method that can insert a company to the DB

		void removeCompany(Company company) throws Exception;
		//* method that can remove a company from the DB
		
		void updateCompany(Company company) throws Exception;
		//* method that can update a company from the DB
		
		Company getCompany(long id) throws Exception;
		//* method that can retrieve a company from the DB by id
		
		Collection<Company> getAllCompanys() throws Exception; 
		//* method that can retrieve all companies from the DB 
		
		public boolean ifCompanyNameExists(String comp_Name) throws Exception;
		//*method that check if customer exists

		 Company extractCompanyFromResultSet1(ResultSet result) throws Exception;

}
