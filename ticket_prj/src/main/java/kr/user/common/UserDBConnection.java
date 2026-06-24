package kr.user.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDBConnection {

	private static final String JNDI_NAME = "java:comp/env/jdbc/ticketDB";
	private static final UserDBConnection instance = new UserDBConnection();

	private UserDBConnection() {
	}

	public static UserDBConnection getInstance() {
		return instance;
	}

	/**
	 * Tomcat JNDI DataSource에서 사용자 파트 DB 커넥션을 가져온다.
	 */
	public Connection getConnection() throws SQLException {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(JNDI_NAME);
			return dataSource.getConnection();
		} catch (NamingException ne) {
			throw new SQLException("사용자 DB JNDI 리소스를 찾을 수 없습니다: " + JNDI_NAME, ne);
		}
	}

	/**
	 * ResultSet, Statement, Connection 순서로 DB 자원을 닫는다.
	 */
	public void close(ResultSet rs, Statement stmt, Connection con) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * try-with-resources를 쓰기 어려운 상황에서 여러 자원을 순서대로 닫는다.
	 */
	public void close(AutoCloseable... resources) {
		for (AutoCloseable resource : resources) {
			if (resource == null) {
				continue;
			}
			try {
				resource.close();
			} catch (Exception ignored) {
			}
		}
	}
}
