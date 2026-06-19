package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
	
	public Connection getConnection(File file) throws SQLException {
		Connection con = null;
		
		if(!file.exists()) {
			return null;
		}//end if
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
		
		String url = prop.getProperty("url");
		String id = prop.getProperty("id");
		String pass = prop.getProperty("pass");
		
		con = DriverManager.getConnection(url,id,pass);
		
		return con;
		
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
