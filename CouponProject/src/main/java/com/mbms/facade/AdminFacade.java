package com.mbms.facade;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.mbms.beans.Company;
import com.mbms.beans.Coupon;
import com.mbms.beans.Customer;
import com.mbms.dao.Client;
import com.mbms.dbdao.CompanyDBDAO;
import com.mbms.dbdao.Company_CouponDBDao;
import com.mbms.dbdao.CouponDBDao;
import com.mbms.dbdao.CustomerDBDAO;
import com.mbms.dbdao.Customer_CouponDBDao;
import com.mbms.enums.ClientType;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public class AdminFacade implements Client {

	private ClientType clientType = ClientType.ADMIN;
	private static final String adminUserName = "admin";
	private static final String adminPassword = "1234";
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private Company_CouponDBDao company_CouponDBDao = new Company_CouponDBDao();
	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private LocalDate now = LocalDate.now();
	private Customer_CouponDBDao customer_CouponDBDao = new Customer_CouponDBDao();
	private CouponDBDao coupCompanyDao = new CouponDBDao();


	// empty cTor for companyFacade
	public AdminFacade() {

	}


	//******************************COMPANY METHODS*********************************

	public void insertCompany(Company company) throws Exception {

		if (company != null) {

			String companyName = company.getComp_name();

			if (companyName != null) {

				if ((company.getPassword() != null) && (company.getEmail() != null) && (!companyDBDAO.ifCompanyNameExists(companyName))) {

					try {
						companyDBDAO.insertCompany(company);
						System.out.println("the company created");
					}
					catch (Exception e)  {
						System.out.println(e);
					}

				}
			}
		}

	}


public void removeCompany(Company company) throws Exception {
	CompanyFacade companyFacade = new CompanyFacade();
	long compid=company.getId();
	List<Long> companycoupon= company_CouponDBDao.getCouponsByCompanyId(compid);
	System.out.println(companycoupon);

	for (Long id : companycoupon) {
		customerDBDAO.removeAllPurchasedCustomerCoupons(id);
	
	}
	company_CouponDBDao.removeCompany_CouponByCompany(company);
	companyDBDAO.removeCompany(company);
	
}

public void removeCoupons(Coupon coupon) throws Exception {
	company_CouponDBDao.removeCoupons(coupon);	
}

public void updateCompany(Company company, String password, String email) throws Exception {
	//*this method update company details without company name
	company.setPassword(password);
	company.setEmail(email);
	companyDBDAO.updateCompany(company);
}


public Company getCompany (long companyId) throws Exception {
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	return companyDBDAO.getCompany(companyId);
}


public Collection <Company> getAllCompanys() throws Exception {
	//*this method calling get all company from caompnyDBDAO

	return companyDBDAO.getAllCompanys();

}

//***************************CUSTOMER METHODS*******************************************

public void insertCustomer(Customer customer) throws Exception {
	//*this method calling insert customer from CustomerDBDAO
	
	if (customer != null) {

		String companyName = customer.getCust_name();

		if (companyName != null) {

			if ((customer.getPassword() != null) && (!customerDBDAO.ifCustomerNameExists(customer.getCust_name()))) {

				try {
					customerDBDAO.insertCustomer(customer);
					System.out.println("the customer created");
				}
				catch (Exception e)  {
					System.out.println(e);
				}

			}
		}
	}
}

public void removeCustomer(Customer temp) throws Exception{
	Customer cus= new Customer();
	cus= customerDBDAO.getCustomer(temp);
	long id= cus.getId();
	customerDBDAO.removeAllPurchasedCustomerCoupons(id);
	customerDBDAO.removeCustomerById(id);	
}

public void updateCustomer(Customer customer, String password) throws Exception {
	//*this method calling update customer from customerDBDAO without customer name

	Customer customer2=new Customer();
	customer2=customerDBDAO.getCustomer(customer);

	customer2.setPassword(password);

	customerDBDAO.updateCustomer(customer2);
}

public Customer getCustomerDetails (Customer temp) throws Exception {
	Customer customer= new Customer();
	customer= customerDBDAO.getCustomer(temp);
	return customer;
}

public Customer getCustomer (long customerId) throws Exception {
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	return customerDBDAO.getCustomerById(customerId);
}

public Set<Customer> getAllCustomers() throws Exception {
	//*this method calling getAllCustomer from customerDBDAO
	return customerDBDAO.getAllCustomers();
}

@Override
public Client login(String user, String password, ClientType clienttype) {
	return null;
}

}
