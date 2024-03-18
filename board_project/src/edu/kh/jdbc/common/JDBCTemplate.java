package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	/* DB 연결 (Connection 생성) , 자동 커밋 off
	 * 트랜잭션 제어, JDBC 객제 자원 반환(close)
	 * 
	 * 이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스
	 * 
	 * * 모든 필드, 메서드가 static * 
	 * -> 별도 객체 생성 X
	 * -> 어디서든지 클래스명.필드명 / 클래스명.메서드명 호출 가능
	 * 
	 * */
	private static Connection conn = null;

	/**
	 * DB 연결정보를 담고있는 Connection 객체 생성 및 반환 메서드
	 * 
	 * @return conn
	 */
	public static Connection getConnection() {

		try {
			if (conn == null || conn.isClosed()) {

				Properties prop = new Properties();

				prop.loadFromXML(new FileInputStream("driver.xml"));

				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");

				Class.forName(driver);

				conn = DriverManager.getConnection(url, user, password);

				conn.setAutoCommit(false);
			}

		} catch (Exception e) {
			System.out.println("[ Connection 생성 중 예외 발생 ]");
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * Connection 객체 자원 반환 메서드
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {

			if (conn != null && !conn.isClosed())
				conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Statement (부모), PreparedStatement(자식_) 객체 자원 반환 메서드. 업캐스팅
	 * 
	 * @param stmt
	 */
	public static void close(Statement stmt) {

		try {

			if (stmt != null && stmt.isClosed())
				stmt.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * ResultSet 객체 자원 반환 메서드
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {

		try {

			if (rs != null && !rs.isClosed())
				rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 트랜젝션 commit 메서드
	 * 
	 * @param conn
	 */
	public static void commit(Connection conn) {

		try {

			if (conn != null && !conn.isClosed())
				conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 트랜젝션 rollback 메서드
	 * 
	 * @param conn
	 */
	public static void rollback(Connection conn) {

		try {

			if (conn != null && !conn.isClosed())
				conn.rollback();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
