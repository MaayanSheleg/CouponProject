package com.mbms.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mbms.beans.Coupon;
import com.mbms.beans.Customer;
import com.mbms.dao.Customer_CouponDao;
import com.mbms.main.ConnectionPool;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public class Customer_CouponDBDao implements Customer_CouponDao {

	private CustomerDBDAO customerDBDAO=new CustomerDBDAO();
	private CouponDBDao couponDBDAO=new CouponDBDao();
	private Company_CouponDBDao company_CouponDBDao=new Company_CouponDBDao();

	private static Connection con;

	@Override
	public void insertCustomer_Coupon(long cutomerid, long couponid) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "insert into Customer_Coupon (Customer_ID, Coupon_ID) values (?,?)";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);

		try  {

			pstmt.setLong(1, cutomerid);
			pstmt.setLong(2, couponid);

			pstmt.executeUpdate();

			System.out.println("Customer_Coupon added.  CustomerId: " + cutomerid + " couponId: " + couponid);

		}catch (SQLException e) {
			throw new Exception("DB error -  Customer_Coupon addition failed. companyId: " + cutomerid + " couponId: " + couponid);
		}catch (Exception e) {
			throw new Exception(" Customer_Coupon addition failed.  CustomerId: " +cutomerid + " couponId: " + couponid);

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
		}
	}

	public void removebyCustomerCoupon(Customer customer) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String remove = "DELETE FROM Customer_Coupon WHERE customer_id=?";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(remove);


		try (PreparedStatement pstm1 = con.prepareStatement(remove);) {

			con.setAutoCommit(false);
			pstm1.setLong(1,customer.getId());
			pstm1.executeUpdate();
			con.commit();

			System.out.println("the coupon " +"(" +customer.getId()+ ")" +  " was removed");

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
			}}


	}

	public void removebyCouponCustomer(Coupon coupon) throws Exception {
		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String remove = "DELETE FROM Customer_Coupon WHERE coupon_id=?";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(remove);

		try (PreparedStatement pstm1 = con.prepareStatement(remove);) {

			con.setAutoCommit(false);
			pstm1.setLong(1,coupon.getID());
			pstm1.executeUpdate();
			con.commit();

			System.out.println("the coupon" + coupon.getID() +  " was removed");

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {

				throw new Exception("Database error");
			}

			throw new Exception("failed to remove coupon");
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


	public List<Long> getCouponsByCustomerId(long customerId) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		Statement statement = con.createStatement();
		String sql = "select * from Customer_Coupon where customer_Id = " + customerId;
		ResultSet rs = statement.executeQuery(sql);

		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);

		List<Long> listCouponId = new ArrayList<Long>();

		try  {
			while (rs.next()) {
				long coupon_Id = rs.getLong(2);
				listCouponId.add(coupon_Id);
			}

			return listCouponId;

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


}
