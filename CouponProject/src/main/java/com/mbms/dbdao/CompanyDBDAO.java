package com.mbms.dbdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mbms.Exceptions.CompanyException;
import com.mbms.Exceptions.LogingException;
import com.mbms.beans.Company;
import com.mbms.dao.CompanyDao;
import com.mbms.main.ConnectionPool;
import com.mbms.tables.Database;

/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public class CompanyDBDAO implements CompanyDao {

	private static Connection con;
	
	//this method can insert a company to the DB
	public void insertCompany(Company company) throws Exception {
		
		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query

		String sql = "INSERT INTO Company (ID, COMP_NAME, PASSWORD,EMAIL)  VALUES(?,?,?,?)";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		con = DriverManager.getConnection(Database.getDBUrl());
		
		try  {

			pstmt.setLong(1, company.getId());
			pstmt.setString(2, company.getComp_name());
			pstmt.setString(3, company.getPassword());
			pstmt.setString(4, company.getEmail());
			pstmt.executeUpdate();

			System.out.println("Company created" + company.toString());

		} catch (SQLException e) {
			System.out.println(e);
			throw new CompanyException("Company creation failed");
							
		}	finally {
			// finally block used to close resources
		
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
				}}
	}

	//method that can remove a company from the DB
	@Override
	public void removeCompany(Company company) throws Exception {
		
		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String remove = "DELETE FROM Company WHERE id=?";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(remove);			

		try  {

			con.setAutoCommit(false);
			pstmt.setLong(1, company.getId());
			pstmt.executeUpdate();
			con.commit();
			
			System.out.println("the company " + company.getComp_name()+ " " +company.getId()+ " was removed");

		} catch (SQLException e) {

			try {
				con.rollback();
			} catch (SQLException e1) {

				throw new Exception("Database error");
			}

			throw new Exception("failed to remove company");
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
	}}

	//method that can update a company from the DB
	public void updateCompany(Company company) throws Exception {

		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "UPDATE Company " + " SET comp_name='" + company.getComp_name() + "', password='" + company.getPassword()+
				"', email='" + company.getEmail() + "' WHERE ID=" + company.getId();
		
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		
		con = DriverManager.getConnection(Database.getDBUrl());

		try (Statement stm = con.createStatement()) {
			
			stm.executeUpdate(sql);
			
			System.out.println("the company " + company.getComp_name()+" "+ company.getId()+ " was updated");

		} catch (SQLException e) {

			throw new Exception("update Company failed");

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

	//method that can retrieve a company from the DB by id
	public Company getCompany(long id) throws CompanyException {
		
		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new CompanyException("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM Company WHERE ID=" + id;
		PreparedStatement pstmt = null;
		
		Company company= new Company();

		try (Statement stm = con.createStatement()) {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery(sql);
			rs.next();

			company.setId(rs.getLong(1));
			company.setComp_name(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));
			System.out.println(company);
		}
		
		catch (SQLException e) {

			throw new CompanyException("unable to get company data");

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
		}return company;
	}

	//method that print all company's list
	@SuppressWarnings("finally")
	public Collection<Company> getAllCompanys() throws Exception {
		
		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT * FROM Company";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		List <Company> companyList = new ArrayList<>();
		
		try {
		
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Company company = extractCompanyFromResultSet1(resultSet);
				companyList.add(company);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Product data");
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
		return companyList;
	}
	}
	
	@SuppressWarnings("finally")
	@Override
	public boolean ifCompanyNameExists(String name) throws Exception {
		
		Connection con;
		
		// Open a connection from the connection pool class
		try {
			con = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("The Connection is faild");
		}
		// Define the Execute query
		String sql = "SELECT ID FROM Company WHERE COMP_NAME = ? ";
		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		
		try {	

			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				System.out.println("The company is existed chose difrent company");;

			}
		} catch (SQLException e) {
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

	public Company extractCompanyFromResultSet1(ResultSet result) throws Exception {
		Company company=new Company();
		company.setId(result.getLong("ID"));
		company.setComp_name(result.getString("COMP_NAME"));
		company.setPassword(result.getString("PASSWORD"));
		company.setEmail(result.getString("EMAIL"));
		return company;
	}	
	
	@SuppressWarnings("finally")
	public boolean login(String compName, String password) throws LogingException, SQLException {
		
		// Open a connection from the connection pool class
			try {
						con = ConnectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new LogingException("The Connection is faild");
			}
		// Define the Execute query
		boolean loginSuccess  = false;
		
		
			String query = "SELECT * FROM Company WHERE COMP_NAME=? AND PASSWORD=?";
			PreparedStatement pstmt = con.prepareStatement(query);
		try {
			pstmt.setString(1, compName);
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
