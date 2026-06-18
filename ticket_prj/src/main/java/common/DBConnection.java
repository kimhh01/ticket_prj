package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	
	private static DBConnection dbCon;
	
	private DBConnection() {
		
	}//DBConnection
	
	public static DBConnection getInstance() {
		
		if(dbCon == null) {
			dbCon = new DBConnection();
		}//end if
		
		return dbCon;
		
	}//getInstance
	
	public Connection getConnection() {
		
		return null;
		
	}//getConnection
	
	public void dbClose(ResultSet rs, Statement stmt, Connection con) {
		
	}//dbClose
	
}//class
