package microblog.kitsch.business.dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseUtil {
	public static DataSource dataSource;
	
	static {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/kitsch");
		} catch (NamingException e) {
			System.out.println("JNDI error occurred.");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static void close(Connection connection, Statement stmt) {
		DatabaseUtil.close(connection, stmt, null);
	}
	
	public static void close(Connection connection, Statement stmt, ResultSet rs) {
		try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        try { if (connection != null) connection.close(); } catch(SQLException e) { e.printStackTrace(System.err); }
	}
}
