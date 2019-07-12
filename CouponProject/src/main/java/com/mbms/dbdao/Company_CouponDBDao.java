package com.mbms.dbdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mbms.Exceptions.CompanyException;
import com.mbms.beans.Company;
import com.mbms.beans.Coupon;
import com.mbms.dao.Company_CouponDao;
import com.mbms.main.ConnectionPool;
import com.mbms.tables.Database;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public class Company_CouponDBDao implements Company_CouponDao {

	private static Connection con;
	private LocalDate now = LocalDate.now();
	private Coupon coupon = new Coupon();
	private CompanyDBDAO compCompanyDAO = new CompanyDBDAO();
	private CouponDBDao coupCompanyDAO = new CouponDBDao();
	
	@Override
	//this method insert values to company_coupon table
	public void insertCompany_Coupon(Company company, Coupon coupon) throws Exception {

		Connection con;
		
		// Open a connection from the connection pool class
		try {
			
			con = ConnectionPool.getInstance().getConnection();
			
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		
		// Define the Execute query
		String sql = "insert into Company_Coupon (Company_ID, Coupon_ID) values (?,?)";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		
	//	con = DriverManager.getConnection(Database.getDBUrl());
		
		try  {
			pstmt.setLong(1, company.getId());
			pstmt.setLong(2, coupon.getID());
			pstmt.executeUpdate();


			System.out.println("Company_Coupon added. companyId: " + company.getId() + " couponId: " + coupon.getID());
		
		} catch (SQLException e) {
//			throw new Exception("Company_Coupon addition failed. companyId: " + company.getId() + " couponId: " + coupon.getID());
		System.out.println(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (pstmt != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}
			try {
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}
		}
	
	}


	
	
	@Override
	public void removeCompany_Coupon(Coupon coupon) throws Exception {

		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query

		String remove = "DELETE FROM Company_Coupon WHERE coupon_ID=?";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(remove);
	

		try (PreparedStatement pstm1 = con.prepareStatement(remove);) {

			con.setAutoCommit(false);
			pstm1.setLong(1, coupon.getID());
			pstm1.executeUpdate();
			con.commit();

			System.out.println("the coupons of company: "+coupon.getTitle() +" with id: " + coupon.getID()+" , "+ "was removed");

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {

				throw new Exception("Database error");
			}

			throw new Exception("failed to remove coupon");
		} finally {// finally block used to close resources
			try {
				if (pstmt != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}
			try {
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}

		}
	}

public void removeCoupons(Coupon coupon) throws CompanyException{
		
		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new CompanyException("The Connection is faild");
		}
		// Define the Execute query

		String remove = "DELETE FROM Coupon WHERE id=?";
		PreparedStatement pstmt = null;
	
		try  {
			pstmt = con.prepareStatement(remove);
			con.setAutoCommit(false);
			pstmt.setLong(1, coupon.getID());
			pstmt.executeUpdate();
			con.commit();
			
			System.out.println("the coupon" + coupon.getTitle()+ coupon.getID()+ " was removed");

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {

				throw new CompanyException("Database error");
			}

			throw new CompanyException("failed to remove coupon");
		} finally {// finally block used to close resources
			try {
				if (pstmt != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new CompanyException("The close connection action faild");
			}
			try {
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new CompanyException("The close connection action faild");
			}
		}
		
	}
	
	// this method return the last coupon ID
		@SuppressWarnings("finally")
		public long getMaxCouponId() throws Exception {
			Connection con;
			
			// Open a connection from the connection pool class
			try {
				con = ConnectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("The Connection is faild");
			}
			// Define the Execute query
			String sql = "SELECT MAX(ID) FROM COUPON";
			PreparedStatement pstmt = null;
			pstmt = con.prepareStatement(sql);
			long result = 0;
			try (Statement pstt = con.createStatement()){

				ResultSet rs = pstt.executeQuery(sql);
				if (rs.next())
				{
					String max = rs.getString(1);
					result = Long.parseLong(max);
					System.out.println(max);
					
				}
			}catch (SQLException e) {
				throw new Exception("Get max ID from coupon table failed");
			}finally {// finally block used to close resources
				try {
					if (pstmt != null) {
						ConnectionPool.getInstance().returnConnection(con);
					}
				} catch (Exception e) {
					throw new Exception("The close connection action faild");
				}
				try {
					if (con != null) {
						ConnectionPool.getInstance().returnConnection(con);
					}
				} catch (Exception e) {
					throw new Exception("The close connection action faild");
				}

				return result;
		}}

	
	public List<Long>  getCouponsByCompanyId(long compid) throws Exception {

		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM COMPANY_COUPON where COMPANY_ID=" + compid;
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		
			List<Long> listComanyCoupon = new ArrayList<Long>();
			
			try  {
				
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sql);

				while (rs.next()) {
					
					long coupon_Id = rs.getLong(2);

					listComanyCoupon.add(coupon_Id);
			}
		return listComanyCoupon;

	} catch (SQLException e) {

		throw new Exception();

	} finally {// finally block used to close resources
		try {
			if (pstmt != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		} catch (Exception e) {
			throw new Exception("The close connection action faild");
		}
		try {
			if (con != null) {
				ConnectionPool.getInstance().returnConnection(con);
			}
		} catch (Exception e) {
			throw new Exception("The close connection action faild");
		}
	}


	}
	
	public void removeCompany_CouponByCompany(Company company) throws Exception {

		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query

		String remove = "DELETE FROM Company_Coupon WHERE company_ID=?";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(remove);
	

		try (PreparedStatement pstm1 = con.prepareStatement(remove);) {

			con.setAutoCommit(false);
			pstm1.setLong(1, coupon.getID());
			pstm1.executeUpdate();
			con.commit();

			System.out.println("the coupons of company: "+company.getComp_name() +" with id: " + company.getId()+" , "+ "was removed");

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {

				throw new Exception("Database error");
			}

			throw new Exception("failed to remove coupon");
		} finally {// finally block used to close resources
			try {
				if (pstmt != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}
			try {
				if (con != null) {
					ConnectionPool.getInstance().returnConnection(con);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}

		}
	}	


}
