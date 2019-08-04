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
	@POST
	@Path("createCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public String createCompany(Company company) throws Exception {
		
		AdminFacade admin = getFacade();
		String failMsg = "FAILED TO ADD A NEW COMPANY: " + "There is already a company with the same name: " 
							+ company.getComp_name() + " - please change the company name"; // debug

		try {
			admin.insertCompany(company);
			return new Gson().toJson(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return failMsg;
	}

	// REMOVE a Company
	@DELETE
	@Path("removeCompany/{companyId}")	
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCompany(@PathParam("companyId") long id) {

		String failMsg = "FAILED TO REMOVE A COMPANY: there is no such id! " + id
		+ " - please enter another company id";

		AdminFacade admin = getFacade();
		Company company = null;
		try {
			company = admin.getCompany(id);
			if (company != null) {
				admin.removeCompany(company);
				return "SUCCEED TO REMOVE A COMPANY: name = " + company.getComp_name() ;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return failMsg;
	}

	@POST
	@Path("updateCompany")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCompany(@QueryParam("compId") long id, @QueryParam("password") String password,
			@QueryParam("email") String email) throws Exception {

		AdminFacade admin = getFacade();
		try {
			Company company = admin.getCompany(id);
			if (company != null) {
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
	@Path("getcompany/{compId}")
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

//	// CREATE a new Customer - add a customer to the Customer Table in DB
	@POST
	@Path("createCustomer")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createCustomer(Customer customer ) {

		AdminFacade admin = getFacade();
		String failMsg = "FAILED TO ADD A NEW CUSTOMER";

			try {
				admin.insertCustomer(customer);
				return new Gson().toJson(customer);
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		
		return failMsg;
	}

	// REMOVE Customer
	@DELETE
	@Path("removeCustomer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void removeCustomer(@PathParam("customerId") long customerId) {

		AdminFacade admin = getFacade();
		Customer customer = null;
		try {
			customer = admin.getCustomer(customerId);
			if (customer != null) {
				admin.removeCustomer(customer);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@POST
	@Path("updateCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateCustomer(@QueryParam("customerId") long id, @QueryParam("password") String password) throws Exception {

		AdminFacade admin = getFacade();

		try {
			Customer customer = admin.getCustomer(id);
			if (customer != null) {
				admin.updateCustomer(customer, password);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@GET
	@Path("getcustomer/{customerId}")
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

