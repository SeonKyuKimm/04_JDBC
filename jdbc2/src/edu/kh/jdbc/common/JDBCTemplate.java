package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	/*
	 * DB에 연결 (Connection 생성) + 자동 커밋 off , JDBC 객체 자원 반환 (Close), 트랜젝션 제어,
	 * 
	 * 이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스
	 * 
	 * 모든 필드, 메서드가 static * -> 어디서든지 클래스명.필드명/ 클래스명.메서드명 호출이 가능 -> 별도로 객체 생성 X
	 * 
	 */

	private static Connection conn = null;

	/**
	 * DB 연결 정보를 담고있는 Connection 객체 생성 및 반환 메서드
	 * 
	 * @return conn
	 */
	public static Connection getConnection() {

		try {

			// 현재 커넥션 객체가 없을경우 -> 새 커넥션 객체 생성
			if (conn == null || conn.isClosed()) {
				// conn.isClosed() : 커넥션이 close상태면 true 반환

				Properties prop = new Properties();
				// Map<String, String> 형태의 객체, XML 입출력 특화

				// driver.xml 파일 읽어오기
				prop.loadFromXML(new FileInputStream("driver.xml"));
				// -> xml 파일에 작성된 내용이 Properties 객체에 모두 저장되어있을것임

				// XML에서 읽어온 값을 모두 변수에 저장
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");

				// 커넥션 생성
				Class.forName(driver);// Oracle JDBC Driver 객체 메모리 로드

				// DriverManager 통해 Connection 객체 생성
				conn = DriverManager.getConnection(url, user, password);

				// + 자동 commit 비활성화
				conn.setAutoCommit(false);

			}

		} catch (Exception e) {
			System.out.println(" [ Connection 생성 중 예외 발생 ] ");
			e.printStackTrace();
		}

		return conn; // 생성 및 설정된 Connetcion 객체 반환
	}

	/**
	 * Connection 객체 자원을 반환 메서드
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {

		try {
			// 전달받은 conn이
			// 참조하는 Connection 객체가 있고
			// 그 Connection 객체가 close 상태가 아니라면 (자원반환해라)
			if (conn != null && !conn.isClosed())
				conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Statement(부모) , PreparedStatement(자식) 객체 자원 반환 메서드. 업캐스팅
	 * 
	 * @param stmt
	 */
	public static void close(Statement stmt) {

		try {

			if (stmt != null && !stmt.isClosed())
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
