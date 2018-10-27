package com.gem.hsx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConn {
	public Connection getConnection() 
	{ 
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hsx", "hsx");
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {	
			e.printStackTrace();
		}   	
		return connection;
	}
	public void closeconn(Connection connection)
	{  	 
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
