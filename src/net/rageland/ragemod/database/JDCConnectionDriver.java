package net.rageland.ragemod.database;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class JdcConnectionDriver implements Driver {

	public static final String URL_PREFIX = "jdbc:jdc:";
	private static final int MAJOR_VERSION = 1;
	private static final int MINOR_VERSION = 0;
	private JdcConnectionPool pool;

	public JdcConnectionDriver(String driver, String url, String user, String password, long timeout) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		DriverManager.registerDriver(this);
		Class.forName(driver).newInstance();
		pool = new JdcConnectionPool(url, user, password, timeout);
	}

	public JdcConnectionPool getConnectionPool() {
		return pool;
	}

	public Connection connect(String url, Properties props) throws SQLException {
		if (!url.startsWith(URL_PREFIX)) {
			return null;
		}
		return pool.getConnection();
	}

	public boolean acceptsURL(String url) {
		return url.startsWith(URL_PREFIX);
	}

	public int getMajorVersion() {
		return MAJOR_VERSION;
	}

	public int getMinorVersion() {
		return MINOR_VERSION;
	}

	public DriverPropertyInfo[] getPropertyInfo(String str, Properties props) {
		return new DriverPropertyInfo[0];
	}

	public boolean jdbcCompliant() {
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
