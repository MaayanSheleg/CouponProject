package com.mbms.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.mbms.Exceptions.CouponException;
import com.mbms.beans.Coupon;
import com.mbms.beans.ExpiredCoupon;
import com.mbms.dao.CouponDao;
import com.mbms.enums.CouponType;
import com.mbms.main.ConnectionPool;
import com.mbms.tables.Database;

/**
 * @author Maytal & mayaan *
 */
public class CouponDBDao implements CouponDao {

	private static Connection con;

	//*this method can insert a coupon to the DB
	public void insertCoupon(Coupon coupon) throws CouponException {
		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new CouponException("The Connection is faild");
		}
		// Define the Execute query
		String sql = "INSERT INTO Coupon (title, start_date, end_date, amount, type, message, price, image)  VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;

		try  {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, (Date) coupon.getStart_date());
			pstmt.setDate(3, (Date) coupon.getEnd_date());
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().name());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			pstmt.executeUpdate();

			System.out.println("Coupon created" + coupon.toString());

		} catch (SQLException e) {
			System.out.println(e);

			throw new CouponException("Company creation failed");

		} finally {// finally block used to close resources
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

	//* method that can update a coupon from the DB
	public void updateCoupon(Coupon coupon) throws CouponException {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new CouponException("The Connection is faild");
		}
		// Define the Execute query

		String sql = "UPDATE Coupon SET TITLE=?, START_DATE=?, END_DATE=?, AMOUNT=?,"

					+ " TYPE=?, MESSAGE=?, PRICE=?, IMAGE=? WHERE ID=?";

		PreparedStatement pstmt = null;



		try (Statement stm = con.createStatement()) {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, coupon.getTitle());

			pstmt.setDate(2, (Date) coupon.getStart_date());

			pstmt.setDate(3, (Date) coupon.getEnd_date());

			pstmt.setInt(4, coupon.getAmount());

			pstmt.setString(5, coupon.getType().toString());

			pstmt.setString(6, coupon.getMessage());

			pstmt.setDouble(7, coupon.getPrice());

			pstmt.setString(8, coupon.getImage());

			pstmt.setLong(9, coupon.getID());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);

			throw new CouponException("update coupon failed");

		} finally {// finally block used to close resources
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


	//* method that can give the id of the coupon by name 
	@SuppressWarnings("finally")
	public Coupon whatCouponid(Coupon coupon) throws Exception {
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		PreparedStatement pstmt = null;
		String title = coupon.getTitle();
		Coupon coupi= new Coupon();
		String sql = "SELECT * FROM Coupon";
		pstmt = con.prepareStatement(sql);

		try (Statement stm = con.createStatement()) {

			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {	

				if (title.equals(rs.getString(2))) {

					long id = rs.getLong(1);	
					coupi.setID(id);
				}
			}
		}catch (SQLException e) {
			System.out.println(e);

			throw new Exception("unable to get coupon data");

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

			return coupi;
		}

	}

	public synchronized Set<Coupon> getAllCoupons() throws Exception {

		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM Coupon";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		Set<Coupon> set = new HashSet<>();


		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {

				long id = rs.getLong(1);

				String title = rs.getString(2);

				Date start_date = rs.getDate(3);


				Date end_date = rs.getDate(4);

				int amount=rs.getInt(5);

				String couponType = rs.getString(6);

				CouponType type = null ;
				switch (couponType) {
				case "Food":
					type=CouponType.Food;
					break;
				case "Resturans":
					type=CouponType.Resturans;
					break;
				case "Electricity":
					type=CouponType.Electricity;
					break;
				case "Health":
					type=CouponType.Health;
					break;
				case "Sports":
					type=CouponType.Sports;
					break;
				case "Camping":
					type=CouponType.Camping;
					break;
				case "Traveling":
					type=CouponType.Traveling;
					break;
				default:
					System.out.println("Coupon not existent");
					break;
				}
				String message = rs.getString(7);
				Double price = rs.getDouble(8);
				String image = rs.getString(9);

				set.add(new Coupon(id,title, start_date, end_date, amount, type, message, price, image));
			}

		} catch (SQLException e) {

			System.out.println(e);

			throw new Exception("cannot get Coupon data");

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
		return set;
	}

	//* method that can remove a coupon from the DB
	public void removeCoupon(Coupon coupon) throws Exception {

		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query

		String remove = "DELETE FROM Coupon WHERE id=?";
		PreparedStatement preparedStatement = null;
		preparedStatement = con.prepareStatement(remove);

		try {

			preparedStatement = con.prepareStatement(remove);

		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		try {

			con.setAutoCommit(false);

			preparedStatement.setLong(1, coupon.getID());

			preparedStatement.executeUpdate();

			con.commit();

			System.out.println("the coupon" + coupon.getTitle()+ coupon.getID()+ "was removed");

		} catch (SQLException e) {

			try {
				con.rollback();

			} catch (SQLException e1) {

				throw new Exception("Database error");

			}

			throw new Exception("failed to remove coupon");

		} finally {// finally block used to close resources
			try {
				if (preparedStatement != null) {
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


	public void removeCouponById(long id) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "DELETE FROM COUPON WHERE id=?";
		PreparedStatement preparedStatement = null;
		preparedStatement = con.prepareStatement(sql);

		try {

			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			System.out.println("the coupon " + id + " was removed");

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {

				throw new Exception("Database error");
			}

			throw new Exception("failed to remove coupon");
		} finally {// finally block used to close resources
			try {
				if (preparedStatement != null) {
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
		}}


	//this method can insert a Expired coupon to the DB

	@Override

	public void insertExpiredCoupon(Coupon coupon) throws Exception {
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "INSERT INTO ExpiredCoupon (title, start_date, end_date, amount, type, message, price, image)  VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);

		try  {

			pstmt.setString(1, coupon.getTitle());

			pstmt.setDate(2, (Date) coupon.getStart_date());

			pstmt.setDate(3, (Date) coupon.getEnd_date());

			pstmt.setInt(4, coupon.getAmount());

			pstmt.setString(5, coupon.getType().name());

			pstmt.setString(6, coupon.getMessage());

			pstmt.setDouble(7, coupon.getPrice());

			pstmt.setString(8, coupon.getImage());

			pstmt.executeUpdate();



			System.out.println("Expired Coupon created" + coupon.toString());



		} catch (SQLException e) {

			System.out.println(e);



			throw new Exception("Expired Coupon creation failed");



		} 	finally {// finally block used to close resources
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

	// method that can retrieve a Expired Coupon from the DB by id

	@SuppressWarnings("finally")
	@Override

	public ExpiredCoupon getexpiredCoupon(long id) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM ExpiredCoupon WHERE ID=" + id;
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);

		ExpiredCoupon expiredCoupon= new ExpiredCoupon();

		try (Statement stm = con.createStatement()) {

			ResultSet rs = stm.executeQuery(sql);

			rs.next();

			expiredCoupon.setID(rs.getLong(1));

			expiredCoupon.setTitle(rs.getString(2));

			expiredCoupon.setStart_date(rs.getDate(3));

			expiredCoupon.setEnd_date(rs.getDate(3));

			expiredCoupon.setAmount(rs.getInt(4));

			CouponType type = null ;

			switch (type.getClass().getName()) {

			case "food":

				type=CouponType.Food;

				break;

			case "Resturans":

				type=CouponType.Resturans;

				break;

			case "Electricity":

				type=CouponType.Electricity;

				break;

			case "Health":

				type=CouponType.Health;

				break;

			case "Sports":

				type=CouponType.Sports;

				break;

			case "Camping":

				type=CouponType.Camping;

				break;

			case "Traveling":

				type=CouponType.Traveling;

				break;

			default:

				System.out.println("Coupon not existent");

				break;

			}

			expiredCoupon.setType(type);

			expiredCoupon.setMessage(rs.getString(3));

			expiredCoupon.setPrice(rs.getDouble(3));

			expiredCoupon.setImage(rs.getString(3));


		} catch (SQLException e) {

			System.out.println(e);

			throw new Exception("unable to get expired coupon data");



		} 	finally {// finally block used to close resources
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

			return expiredCoupon;

		}}


	@Override

	public Set<ExpiredCoupon> getAllExpiredCoupon() throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM ExpiredCoupon";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		con = DriverManager.getConnection(Database.getDBUrl());

		Set<ExpiredCoupon> set = new HashSet<>();


		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {

				long id = rs.getLong(1);

				String title = rs.getString(2);

				Date start_date = rs.getDate(3);

				Date end_date = rs.getDate(4);

				int amount=rs.getInt(5);

				String couponType = rs.getString(6);

				CouponType type = null ;

				switch (couponType) {

				case "Food":

					type=CouponType.Food;

					break;

				case "Resturans":

					type=CouponType.Resturans;

					break;

				case "Electricity":

					type=CouponType.Electricity;

					break;

				case "Health":

					type=CouponType.Health;

					break;

				case "Sports":

					type=CouponType.Sports;

					break;

				case "Camping":

					type=CouponType.Camping;

					break;

				case "Traveling":

					type=CouponType.Traveling;

					break;

				default:

					System.out.println("Coupon not existent");

					break;

				}

				String message = rs.getString(7);

				Double price = rs.getDouble(8);

				String image = rs.getString(9);



				set.add(new ExpiredCoupon(id,title, start_date, end_date, amount, type, message, price, image));

			}



		} catch (SQLException e) {



			System.out.println(e);



			throw new Exception("cannot get Coupon data");



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

		return set;

	}

	public Set<Long> getCouponsByCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings("finally")
	public boolean ifCouponNameExists(Coupon coupon) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM COUPON ";

		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		String title = coupon.getTitle();

		try {
			while (rs.next()) {	
				if (title.equals(rs.getString(2))) {

					System.out.println("The COUPON is existed chose difrent TITLE");		
				}
			}} catch (SQLException e) {
				System.out.println(e);
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

				return false;


			}
	}
}
