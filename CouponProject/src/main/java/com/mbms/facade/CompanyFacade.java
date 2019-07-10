package com.mbms.facade;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mbms.Exceptions.CouponException;
import com.mbms.beans.Company;
import com.mbms.beans.Coupon;
import com.mbms.dao.Client;
import com.mbms.dbdao.CompanyDBDAO;
import com.mbms.dbdao.Company_CouponDBDao;
import com.mbms.dbdao.CouponDBDao;
import com.mbms.dbdao.Customer_CouponDBDao;
import com.mbms.enums.ClientType;
import com.mbms.enums.CouponType;
import com.mbms.main.ConnectionPool;
import com.mbms.main.Utils;


/**
 * @author Maytal & mayaan *
*/
public class CompanyFacade implements Client {

	private ClientType clientType = ClientType.COMPANY;
	private CouponDBDao coupCompanyDao = new CouponDBDao();
	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	private Company_CouponDBDao company_CouponDBDao = new Company_CouponDBDao();
	private Customer_CouponDBDao cus_couCompany = new Customer_CouponDBDao();
	private CouponDBDao couponDBDao = new CouponDBDao();
	private LocalDate now = LocalDate.now();
	private Customer_CouponDBDao customer_CouponDBDao= new Customer_CouponDBDao();
	private Company company= new Company();

	// full cTor for the companyFacade
	public CompanyFacade(Company C) {
		this.company = C;
		
	}

	// empty cTor for companyFacade
	public CompanyFacade() {
	}

	//this method insert values to coupon table and company_coupon table
	public Coupon insertCoupon(Coupon coupon) throws Exception {
		try {
			Set<Coupon> coupons = coupCompanyDao.getAllCoupons();
			Iterator<Coupon> i = coupons.iterator();
			while (i.hasNext()) {
				Coupon current = i.next();
				if (coupon.getTitle().equals(current.getTitle())) {
					throw new Exception("This coupon with this title already exists");	
				}
			}
			if (!i.hasNext()) {
				coupCompanyDao.insertCoupon(coupon);
				company_CouponDBDao.insertCompany_Coupon(this.company, coupon);

			} 
		} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		return coupon;
	}

public void removeCoupon(Coupon fofo) throws Exception {	
	Coupon coupon = new Coupon();
	Coupon coco = new Coupon();
	coupon = fofo;
	coco =couponDBDao.whatCouponid(fofo);
	long id = coco.getID();
	coupon.setID(id);
	System.out.println(coupon);
	
	company_CouponDBDao.removeCompany_Coupon(coupon);
	customer_CouponDBDao.removebyCouponCustomer(coupon);
	couponDBDao.removeCoupon(fofo);

	
}

public void updateCoupon(Coupon titi , java.sql.Date end_date , double price) throws Exception {
	Coupon coupon = new Coupon();
	Coupon coco = new Coupon();
	coupon = titi;

	coco =couponDBDao.whatCouponid(titi);
	long id = coco.getID();
	coupon.setID(id);
	coupon.setEnd_date(end_date);
	coupon.setPrice(price);
	System.out.println(coupon);

	coupCompanyDao.updateCoupon(coupon);
	System.out.println("the coupon " + coupon.getTitle() + " was updated");
}

// this method return company information
public void  getDetails (Company company)
{
	System.out.println(company);
}

public List<Coupon> getAllCompanyCoupon(Company company) throws Exception {

	long compId= company.getId();

	List<Long> companyCouponId = company_CouponDBDao.getCouponsByCompanyId(compId);

	List<Coupon> nameCoupons = new ArrayList<Coupon>();

	for (Long id : companyCouponId) {

		nameCoupons.add(couponDBDao.getCoupon(id));

	}
	return nameCoupons;

}

public List<Coupon> getCouponbyType(Company company, CouponType type) throws Exception{

	List<Coupon> coupons = getAllCompanyCoupon(company);
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

public List<Coupon> getCouponbyprice(Company company, double price) throws Exception{

	List<Coupon> coupons = getAllCompanyCoupon(company);
	List<Coupon> couponByPrice = new ArrayList<Coupon>();
	for (Coupon coupon : coupons) {

		if (coupon.getPrice() <=(price)) {

			couponByPrice.add(coupon);
		}
	}
	return couponByPrice;
}

public List<Coupon> getCouponbyDate(Company company, Date date) throws Exception{

	List<Coupon> coupons = getAllCompanyCoupon(company);
	List<Coupon> couponByDate = new ArrayList<Coupon>();
	for (Coupon coupon : coupons) {

		if (coupon.getEnd_date().equals(date) || coupon.getEnd_date().before(date)) {
			couponByDate.add(coupon);
		}
	}
	return couponByDate;
}

// method that can retrieve a coupon from the DB by id
public Coupon getCoupon(long id) throws CouponException {
	Connection con;

	// Open a connection from the connection pool class
	try {
		con = ConnectionPool.getInstance().getConnection();
	} catch (Exception e) {
		throw new CouponException("The Connection is faild");
	}
	// Define the Execute query
	String sql = "SELECT * FROM Coupon WHERE ID=" + id;
	PreparedStatement pstmt = null;


	Coupon coupon= new Coupon();

	try (Statement stm = con.createStatement()) {

		pstmt = con.prepareStatement(sql);
		ResultSet rs = stm.executeQuery(sql);

		if(rs.next()) {

			coupon.setID(rs.getLong(1));
			coupon.setTitle(rs.getString(2));
			coupon.setStart_date(rs.getDate(3));
			coupon.setEnd_date(rs.getDate(4));
			coupon.setAmount(rs.getInt(5));
			coupon.setMessage(rs.getString(7));
			coupon.setPrice(rs.getDouble(8));
			coupon.setImage(rs.getString(9));

			switch (rs.getString(6)) {

			case "Food":

				coupon.setType(CouponType.Food);

				break;

			case "Resturans":

				coupon.setType(CouponType.Resturans);

				break;

			case "Electricity":

				coupon.setType(CouponType.Electricity);

				break;

			case "Health":

				coupon.setType(CouponType.Health);

				break;

			case "Sports":

				coupon.setType(CouponType.Sports);

				break;

			case "Camping":

				coupon.setType(CouponType.Camping);

				break;

			case "Traveling":

				coupon.setType(CouponType.Traveling);

				break;

			default:

				System.out.println("Coupon not existent");

				break;


			}}
		//System.out.println(coupon);	
		return coupon;
	} catch (SQLException e) {
		System.out.println(e);

		throw new CouponException("unable to get coupon data");

	} finally {
		// finally block used to close resources

		try {
			if (pstmt != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		} catch (Exception e) {
			throw new CouponException("The close connection action faild");
		}
		try {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		} catch (Exception e) {
			throw new CouponException("The close connection action faild");
		}


	}
}

@Override
public Client login(String user, String password, ClientType clientType) {
	return null;
}
}
