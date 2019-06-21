package com.mbms.main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.mbms.Exceptions.CouponException;
import com.mbms.tables.Database;
import com.mbms.tables.Tables;


/**
 * 
 */
/**
 * @author Maytal & mayaan *
*/
public class ConnectionPool {

		private static ConnectionPool instance;
		private final int maxConnection=10;
		private BlockingQueue<Connection> conQ= new LinkedBlockingDeque<>(maxConnection);
		// this BlockingQueue saves the number of the connections.

		Tables database= new Tables();
		private ConnectionPool() throws SQLException, CouponException {

			try {

				Class.forName(Database.getDriverData());

			}catch (ClassNotFoundException e){

				e.getMessage();

			}

			Connection con1 = DriverManager.getConnection(Database.getDBUrl());
			DatabaseMetaData metas;

			ResultSet tables;

			Statement stat;

			metas = con1.getMetaData();

			stat = con1.createStatement();

			tables = metas.getTables(con1.getCatalog(), null, "COMPANY", null);

			if (!tables.next()) {

				try {
				
					database.CreateAllTables();
				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				

			}

			con1.close();

			while (conQ.size() <  maxConnection) {

				con1 = DriverManager.getConnection(Database.getDBUrl());

				conQ.offer(con1);

			}

		}


		//getInstance for singlton
		public static ConnectionPool getInstance() throws CouponException{

			if (instance==null){

				try {

					instance=new ConnectionPool();

				} catch (SQLException e) {

					// TODO Auto-generated catch block

					e.printStackTrace();
				}
			}
			return instance;
		}

		/**

		 * send a connection to the requester from conQ. this method is synchronised so only one can get to the connection pool at a time.

		 * before sending a connection it set the autocommit to true.

		 */

		public synchronized Connection getConnection() throws CouponException {

			try{

				Connection c=conQ.poll();

				c.setAutoCommit(true);

				return c;

			} catch (SQLException e) {

				throw new CouponException("failed to connect server");

			}

		}

		/**

		 * return a connection to conQ. this method is synchronised so only one can get to the connection pool at a time

		 * @param c connection

		 */

		public synchronized void returnConnection (Connection c){

			conQ.offer(c);

		}

		/**

		 * close all the connection available in conQ

		 * @throws CouponException

		 */

		public void closeAllConnections()throws CouponException {

			Connection c;

			while (conQ.peek()!=null){

				c=conQ.poll();

				try {

					c.close();

				} catch (SQLException e) {

					throw new CouponException("Unable to close all connections");
				}
			}
		}

		/**

		 * @return the number of the available connections

		 */

		public int avilabelConnections (){

			return this.conQ.size();

		}

	}

