package kr.admin.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class AdminDBConnection {

    private static final String JNDI_NAME = "java:comp/env/jdbc/dbcp";

    private final DataSource dataSource;

    private AdminDBConnection() {

        try {
            Context ctx = new InitialContext();

            dataSource = (DataSource) ctx.lookup(JNDI_NAME);

            System.out.println("Admin DataSource = " + dataSource);

        } catch (NamingException e) {
            throw new IllegalStateException(
                    "관리자 DB JNDI 조회 실패 : " + JNDI_NAME, e);
        }
    }

    private static class Holder {
        private static final AdminDBConnection INSTANCE =
                new AdminDBConnection();
    }

    public static AdminDBConnection getInstance() {
        return Holder.INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}