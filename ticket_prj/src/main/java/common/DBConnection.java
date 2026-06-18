package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public void dbClose(ResultSet rs, Statement stmt, Connection con)throws SQLException {
		try {
			if(rs != null) {rs.close();}//end if
			if(stmt != null) {stmt.close();}//end if
		}finally {
			if(con != null) {con.close();}//end if
		}//end finally
	}//dbClose
	
}//class
