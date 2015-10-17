package microblog.kitsch.business.dataaccess;

import microblog.kitsch.KitschSystem;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseUtil_old {
	private static Properties connectionProps;
	private static String dbms;
	private static String serverName;
	private static String portNumber;
	private static String dbName;
	private static String user;
	private static String password;
	
	static {
		try {
			connectionProps = new Properties();
			connectionProps.load(new FileInputStream(KitschSystem.PROPERTIES_FILE_LOCATION));
			Class.forName(connectionProps.getProperty("driverClass"));
			dbms = connectionProps.getProperty("dbms");
			serverName = connectionProps.getProperty("serverName");
			portNumber = connectionProps.getProperty("portNumber");
			dbName = connectionProps.getProperty("dbName");
			user = connectionProps.getProperty("user");
			password = connectionProps.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		String url = null;
		Connection conn = null;
		
		if (dbms.equals("mysql") || dbms.equals("derby")) {
			url = "jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/" + dbName;
			
		} else if (dbms.equals("oracle")) {
			url = "jdbc:" + dbms + ":thin:@" + serverName + ":" + portNumber + ":" + dbName;
		}
		
		conn = DriverManager.getConnection(url, user, password);
		//conn = DriverManager.getConnection(url, connectionProps);
		System.out.println("Connected to " + dbms + " database");
		
		return conn;
	}
	
	public static void close(Connection connection, Statement stmt) {
		DatabaseUtil_old.close(connection, stmt, null);
	}
	
	public static void close(Connection connection, Statement stmt, ResultSet rs) {
		try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        try { if (connection != null) connection.close(); } catch(SQLException e) { e.printStackTrace(System.err); }
	}
}
