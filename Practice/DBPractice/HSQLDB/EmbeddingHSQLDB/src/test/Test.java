package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.hsqldb.Server;

public class Test {

	private static Connection _connection = null;
	/**
	 * create a writer, log hsqldb server info to a file
	 * @param logFileName String, the log file name
	 * @param append boolean, true: append new content; false: clear old content 
	 * @param autoFlush boolean, true: auto flush; false: not auto flush
	 * @return PrintWriter
	 * @throws IOException
	 */
	private static PrintWriter createLogWriter (String logFileName,
		boolean append, boolean autoFlush)
		throws IOException {
		File f = new File(logFileName);

		// create file if not exists
		if (!f.exists()) {
			String logFilePath = f.getAbsolutePath();

			// create parent folders
			File folder = new File(logFilePath.substring(0, logFilePath.indexOf(logFileName)));
			folder.mkdirs();

			// create file
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f, append);
		return new PrintWriter(fw, autoFlush);
	}
	/**
	 * get a db connection
	 * @param driverName
	 * @param dbUrl
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static Connection getConnection (String dbUrl,
		String userName, String password)
		throws SQLException, ClassNotFoundException {
		if (_connection == null
			|| _connection.isClosed()) {
			// Getting a connection to the newly started database
			Class.forName("org.hsqldb.jdbcDriver");
			// Default user of the HSQLDB is 'sa'
			// with an empty password
			return DriverManager.getConnection(dbUrl
				,userName , password);
		} else {
			return _connection;
		}
	}
	public static void main(String[] args)
		throws ClassNotFoundException, SQLException, IOException {
		String dbName = "testName";
		String path = "testPath";

		
		PrintWriter logWriter =
			createLogWriter(dbName+"_"+path+".log",
							true, true);
		// start the hsqldb server
		Server hsqlServer = HSQLDBClass.startServer(dbName, path, logWriter);
		Connection connection =
			getConnection("jdbc:hsqldb:hsql://localhost/"+dbName, "sa", "");
		try {

			Random rand = new Random();
			Statement stmt = connection.createStatement();
			// test several SQL operation
			// create table if not exists
			stmt.execute(
				"CREATE TABLE IF NOT EXISTS testTable ( id BIGINT NOT NULL IDENTITY,"
				+ "firstName VARCHAR(32),"
				+ "lastName VARCHAR(32));");

			// insert data
			stmt.executeUpdate(
				"INSERT INTO testTable (firstName, lastName) VALUES("
				+ "'firstName_"+rand.nextInt()+"', 'lastName_"+rand.nextInt()+"')", Statement.RETURN_GENERATED_KEYS);

			// query data
			ResultSet rs = stmt.executeQuery(
				"select * from testTable;");

			while (rs.next()) {
				// show all data
				System.out.println(rs.getBigDecimal(1) + "\t"
					+ rs.getString(2) + "\t"
					+ rs.getString(3) + "\t");
			}
		} finally {
			System.out.println("close connection");
			// Closing the connection
			if (connection != null) {
				connection.close();
			}
			// Stop the server
			if (hsqlServer != null) {
				HSQLDBClass.stopServer(dbName);
			}
		}
	}
}