package com.mbms.dbdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mbms.Exceptions.CompanyException;
import com.mbms.Exceptions.LogingException;
import com.mbms.beans.Company;
import com.mbms.beans.Customer;
import com.mbms.dao.CustomerDao;
import com.mbms.main.ConnectionPool;
import com.mbms.tables.Database;

/**
 * @author Maytal & mayaan *
*/
public class CustomerDBDAO implements CustomerDao {
	private static Connection con;

	//* method that can insert a customer to the DB
	public void insertCustomer(Customer customer) throws Exception {
		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "INSERT INTO Customer (CUSTNAME, PASSWORD)  VALUES(?,?)";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);

		try  {
			pstmt.setString(1, customer.getCust_name());
			pstmt.setString(2, customer.getPassword());
			pstmt.executeUpdate();

			System.out.println("Customer created" + customer.toString());

		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("Customer creation failed");

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

	public void updateCustomer(Customer customer) throws Exception {
		//* method that can update a customer from the DB
		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "UPDATE Customer " + " SET custName='" + customer.getCust_name()+ "', password='" + customer.getPassword()+
				"' WHERE ID=" + customer.getId();
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);

		con = DriverManager.getConnection(Database.getDBUrl());

		try (Statement stm = con.createStatement()) {
			stm.executeUpdate(sql);
			System.out.println(" The customer " +customer.getCust_name() + " was updated ");
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("update Company failed");

		} 	
		finally {
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

		}}

	@SuppressWarnings("finally")
	@Override
	public synchronized Set<Customer> getAllCustomers() throws Exception {
		//* method that can retrieve all customers from the DB 

		Connection con;
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}

		// Define the Execute query
		String sql = "SELECT * FROM Customer";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		con = DriverManager.getConnection(Database.getDBUrl());
		Set<Customer> set = new HashSet<>();


		try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

			while (rs.next()) {	
				long id = rs.getLong(1);
				String custName = rs.getString(2);
				String password = rs.getString(3);

				set.add(new Customer(id, custName, password));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get customer data");
		}	
		finally {// finally block used to close resources
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
			return set;}
	}

	//* method that grt a customer and find him in the db and get his id customer  
	@SuppressWarnings("finally")
	public Customer getCustomer(Customer customer) throws Exception {
		Connection conn;
		Customer cust= new Customer();
		// Open a connection from the connection pool class
		try {
			conn = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		java.sql.Statement stmt = null;
		PreparedStatement pstms = null;
		String sql = "SELECT * FROM Customer";
		pstms = conn.prepareStatement(sql);
		ResultSet rs = pstms.executeQuery();
		String name = customer.getCust_name();
		try {
			while (rs.next()) {	
				if (name.equals(rs.getString(2))) {
					long id = rs.getLong(1);
					String custName = rs.getString(2);
					String password = rs.getString(3);
					cust.setId(id);
					cust.setCust_name(custName);
					cust.setPassword(password);
				}
			}

		}catch (SQLException e) {
			System.out.println(e);
			throw new Exception("customer buy this coupon");
		}

		finally {// finally block used to close resources
			try {
				if (pstms != null) {
					ConnectionPool.getInstance().returnConnection(conn);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}
			try {
				if (conn != null) {
					ConnectionPool.getInstance().returnConnection(conn);
				}
			} catch (Exception e) {
				throw new Exception("The close connection action faild");
			}

			return cust;
		}
	}

	public Customer getCustomerById(long id) throws Exception {

		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new CompanyException("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM Customer WHERE ID=" + id;
		PreparedStatement pstmt = null;
		
		Customer customer= new Customer();

		try (Statement stm = con.createStatement()) {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			rs.next();

			customer.setId(rs.getLong(1));
			customer.setCust_name(rs.getString(2));
			customer.setPassword(rs.getString(3));
			System.out.println(customer);
		}
		
		catch (SQLException e) {

			throw new CompanyException("unable to get customer data");

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
		}return customer;
	
	}
	
	@SuppressWarnings("finally")
	public boolean ifCustomerNameExists(String cust) throws Exception {
		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT ID FROM Customer WHERE custName = ? ";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);

		try {
			pstmt.setString(1, cust);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				System.out.println("The customer is existed chose difrent customer");;

			}
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		finally {// finally block used to close resources
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

			return false;}
	}

	public Customer extractCustomerFromResultSet1(ResultSet result) throws Exception {
		Customer customer=new Customer();
		customer.setId(result.getLong("ID"));
		customer.setCust_name(result.getString("CUSTNAME"));
		customer.setPassword(result.getString("PASSWORD"));
		return customer;
	}	

	//method that can retrieve all customers from the DB 
	public Collection<Customer> getAllCustomer() throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM Customer";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		con = DriverManager.getConnection(Database.getDBUrl());
		List<Customer> customerList = new ArrayList<>();

		try {
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Customer customer = extractCustomerFromResultSet1(resultSet);
				customerList.add(customer);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Product data");
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
		return customerList;
	}

	//method that  remove a customer from the DB
	public void removeCustomerById(long id) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "DELETE FROM Customer WHERE id=?";
		PreparedStatement preparedStatement = null;
		preparedStatement = con.prepareStatement(sql);

		try {

			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			System.out.println("the customer " + id + " was removed");

		} catch (SQLException e) {
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println(e1);
				throw new Exception("Database error");
			}

			throw new Exception("failed to remove customer");
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

	//method that remove all purchased customer coupons
	public void removeAllPurchasedCustomerCoupons(long id) throws Exception {

		Connection con;

		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String remove = "DELETE FROM Customer_Coupon WHERE customer_ID=?";
		PreparedStatement preparedStatement = null;
		preparedStatement = con.prepareStatement(remove);			

		try {

			preparedStatement = con.prepareStatement(remove);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();

		}catch (Exception e) {
			System.out.println(e);
			System.out.println(" Delete customer_coupon failed");	
		}
		finally {// finally block used to close resources
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

		}
	}

@SuppressWarnings("finally")
public boolean login(String name, String password)throws LogingException, SQLException
{
	// Open a connection from the connection pool class
		try {
					con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new LogingException("The Connection is faild");
		}
	// Define the Execute query
		
	boolean loginSuccess  = false;
	String query = "SELECT * FROM Customer WHERE CUSTNAME=? AND PASSWORD=?";
	PreparedStatement pstmt = con.prepareStatement(query);
	
	try {
	
		pstmt.setString(1, name);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
		loginSuccess = true;
	}
	
} catch (Exception e) {
	throw new LogingException("logging is failed!");
} finally {// finally block used to close resources
	try {
		if (pstmt != null) {
			ConnectionPool.getInstance().returnConnection(con);
		}
	} catch (Exception e) {
		throw new LogingException("The close connection action faild");
	}
	try {
		if (con != null) {
			ConnectionPool.getInstance().returnConnection(con);
		}
	} catch (Exception e) {
		throw new LogingException("The close connection action faild");
	}
	return loginSuccess;
}


}
}

