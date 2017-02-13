package couponSystem.db.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {

	private static ConnectionPool instance = new ConnectionPool();
	private String url = "jdbc:derby://localhost:1527/coupon_system_db";

	private Set<Connection> connections = new HashSet<Connection>();

	/**
	 * Private Connection Pool CTOR for singleton creation.
	 */
	private ConnectionPool() {
		
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < 10; i++) {

			try {
				connections.add(DriverManager.getConnection(url));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * This method creates a new connection till the number of connections is
	 * less then 10.
	 */
	public synchronized Connection getConnection() {

		while (connections.size() == 0) {

			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		Connection con = connections.iterator().next();
		connections.remove(con);

		return con;

	}

	/**
	 * This method will close the connection and will update the number of
	 * connections in using.
	 */
	public synchronized void returnConnection(Connection con) {

		if (connections.size() < 10) {

			connections.add(con);
			notify();

		} else {
			System.out.println("Error: number of connections to DB is illogical.");
		}

	}

	/**
	 * This method will close all connections.
	 */
	public void closeAllCons() {

		for (Connection curr : connections) {

			try {
				curr.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * "Get Instance" method for singletone "Connection Poll" creation.
	 */
	public static ConnectionPool getInstance() {

		return instance;

	}

}
