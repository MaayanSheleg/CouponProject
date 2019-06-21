package com.mbms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mbms.Exceptions.CompanyException;
import com.mbms.Exceptions.CustomrException;
import com.mbms.beans.Company;
import com.mbms.beans.Customer;
import com.mbms.enums.ClientType;
import com.mbms.facade.AdminFacade;
import com.mbms.main.CouponSystem;

@Path("admin")
public class AdminService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	public AdminService() {

	}

	private AdminFacade getFacade() {

		AdminFacade admin = null;
		admin = (AdminFacade) request.getSession(false).getAttribute("facade");
		return admin;
	}

	// Create a new company in the db
	@GET
	@Path("createCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public String createCompany(@QueryParam("id")int id, @QueryParam("name") String comp_Name, @QueryParam("pass") String password, @QueryParam("email") String email) {

		System.out.println(comp_Name +" "+password +" "+email);
		AdminFacade admin = getFacade();
		String failMsg = "FAILED TO ADD A NEW COMPANY: " + "There is already a company with the same name: " + comp_Name
				+ " - please change the company name"; // debug

		Company company = new Company(id, comp_Name, password, email);

		try {
			if (company != null) {
				admin.insertCompany(company);
				return "SUCCEED TO ADD A NEW COMPANY: name = " + comp_Name + ", id = " + company.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return failMsg;

	}

	// REMOVE a Company
	@GET
	@Path("removeCompany")	
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCompany(@QueryParam("company") Company company) {

		String failMsg = "FAILED TO REMOVE A COMPANY: there is no such id! " + company.getId()
		+ " - please enter another company id";

		AdminFacade admin = getFacade();
		try {
			company = admin.getCompany(company.getId());
			if (company != null) {
				admin.removeCompany(company);
				return "SUCCEED TO REMOVE A COMPANY: name = " + company.getComp_name() ;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return failMsg;
	}

	@PUT
	@Path("updateCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCompany(@QueryParam("compId") long id, @QueryParam("pass") String password,
			@QueryParam("email") String email) {

		AdminFacade admin = getFacade();

		try {
			Company company = admin.getCompany(id);
			if (company != null) {
				company.setPassword(password);
				company.setEmail(email);
				admin.updateCompany(company, password, email);
				return "SUCCEED TO UPDATE A COMPANY: pass = " + company.getPassword() + ",e-mail = "
				+ company.getEmail() + ", id = " + id;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return "FAILED TO UPDATE A COMPANY: there is no such id! " + id + " - please enter another company id";
	}

	@GET
	@Path("getcompany")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCompany(@PathParam("compId") long id)  {
		AdminFacade admin = getFacade();

		try {
			Company company = admin.getCompany(id);
			if (company != null) {
				return new Gson().toJson(company);				
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		System.err
		.println("FAILED GET COMPANY BY ID: there is no such id!" + id + " - please enter another company id"); // for

		return null;
	}

	@GET
	@Path("getallcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCompanies() {

		AdminFacade admin = getFacade();

		try {
			Collection<Company> companies = admin.getAllCompanys();
			if (!companies.isEmpty()){
				return new Gson().toJson(companies);
			} else {
				return new Gson().toJson(companies);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// CREATE a new Customer - add a customer to the Customer Table in DB
	@GET
	@Path("createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createCustomer(@QueryParam("id") int id, @QueryParam("name") String custName,@QueryParam("pass") String password ) {

		AdminFacade admin = getFacade();
		String failMsg = "FAILED TO ADD A NEW CUSTOMER";
		Customer customer = new Customer(custName, password);

		if(customer != null) {
			try {
				admin.insertCustomer(customer);
				return "SUCCEED TO ADD A NEW CUSTOMER: name = " + custName + ", id = " + customer.getId();
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		return failMsg;
	}

	// REMOVE Customer
	@GET
	@Path("removeCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCustomer(@QueryParam("customer") Customer customer) {

		String failMsg = "FAILED TO REMOVE A Customer";
		AdminFacade admin = getFacade();
		try {
			customer = admin.getCustomerDetails(customer);
			if (customer != null) {
				admin.removeCustomer(customer);
				return "SUCCEED TO REMOVE A CUSTOMER: name = " + customer.getCust_name() ;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return failMsg;
	}

	@PUT
	@Path("updateCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCustomer(@QueryParam("customerId") long id, @QueryParam("pass") String password) {

		AdminFacade admin = getFacade();

		try {
			Customer customer = admin.getCustomer(id);
			if (customer != null) {
				customer.setPassword(password);
				admin.updateCustomer(customer, password);
				return "SUCCEED TO UPDATE A Customer: pass = " + customer.getPassword() + ", id = " + id;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return "FAILED TO UPDATE A customer";
	}

	@GET
	@Path("getcustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomer(@PathParam("customerId") long id)  {
		AdminFacade admin = getFacade();

		try {
			Customer customer = admin.getCustomer(id);
			if (customer != null) {
				return new Gson().toJson(customer);				
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return "FAILED TO GET CUSTOMER";
	}

	@GET
	@Path("getallcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCustomers() {

		AdminFacade admin = getFacade();

		try {
			Collection<Customer> customers = admin.getAllCustomers();
			if (!customers.isEmpty()){
				return new Gson().toJson(customers);
			} else {
				return new Gson().toJson(customers);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return "FAILED GET ALL CUSTOMERS";
	}
}

