package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {

	public static void main(String[] args) {
		
		/* JDBC (Java DataBase Connectivity) : Java에서 DB에 연결할 수 있게 해주는 Java Programming API (Java에 기본 내장된 인터페이스)
		 java.sql 페이지에서 제공한다.
			
		 JDBC를 이용한 어플리케이션 만들 때 필요한것
		   1 . Java의 JDBC 관련 인터페이스
		   2 . DBMS (Oracle)
		   3 . Oracle에서 Java와 연결할 때 사용할 
		 	   JDBC를 상속받아 구현할 클래스 모음 (ojdbc10.jar 라이브러리)
		 */
		
		
		// 1단계 : JDBC 객체 참조변수 선언 ( java.sql 패키지에 있는 인터페이스 )
		
		Connection conn = null;
		// DB 연결정보를 담은 객체
		// -> DBMS 타입, 이름, IP, Port, 계정명, 비밀번호 저장
		// -> DBeaver의 계정 접속 방법과 유사하다
		// * Java와 DB사이를 연결해주는 통로역할을 한다.
		
		Statement stmt = null;
		// Connection 을 통해서 
		// SQL문을 DB에 전달하여 실행하고(수행하고)
		// 생성된 결과( ResultSet, INSERT , DELETE 등의 DML 성공한 행의 개수 )를 반환하는데 사용되는 객체
		
		
		ResultSet rs = null;
		// SELECT 질의 성공 시 반환되는데
		// 조회 결과 집합을 나타내는 객체다
		 
		
		try {
			// 2단계 : 참조변수에 알맞은 객체 대입
			
			
			// 1 . DB 연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
			// 런타임 시점에 해당 경로의 클래스를 동적으로 로드함
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// -> () 안에 작성된 클래스의 객체를 반환
			// -> 메모리에 객체가 생성되고 JDBC가 필요할 때 알아서 참조하여 사용하는 형태가 된다
			// --> 생략해도 자동으로 메모리 로드가 진행이 됨( 웬만하면 명시적으로 작성하는걸 권장합니다 )
			
			
			// 2 . 연결정보를 담은 Connection 생성 ( java <-> DB 통로 생성 )
			// -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어옴
			
			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류 알려줌 
			String ip = "localhost"; // DB 서버 컴퓨터의 IP
			// == 127.0.0.1 ( loop back ip ) == 본인 주소로 돌아오는 ip, 나중에 프로젝트 시에는 빈자리 컴퓨터를 서버로 쓸거임.
			
			String port = ":1521"; // 포트번호 . oracle 사용하는 포트의 번호 1521 기본값
			
			String sid =":XE"; // 디비 이름
			
			//jdbc:oracle:thin:@localhost:XE == url 이라고 부름
			
			String user = "kh_ksk"; // DB 안에서 사용자 계정
			String pw = "kh1234"; // DB안에서 비밀번호
					
			// DriverManager : 메모리에 로드된 JDBC드라이버를 이용해서 
							// Connection 객체를 만드는 역할
			conn = DriverManager.getConnection(type + ip + port + sid, user, pw );
			// url = jdbc::oracle:thin:@
			
			// getConnection이 전달하는것
			
			// 중간확인
			//System.out.println(conn); //oracle.jdbc.driver.T4CConnection@72d1ad2e
			
			// 3 . SQL 작성 
			// *** JAVA에서 작성되는 SQL은 마지막에 ; 을 찍으면 안된다 ! ***
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			
			// 4 . Statement 객체 생성
			// -> Connection 객체를 통해서 생성
			stmt = conn.createStatement();
			
			
			// 5. 생성된 Statement 객체에 
			// sql을 적재하여 실행한 후
			// 결과를 반환받아와서 
			// rs (result set)변수에 저장
			rs = stmt.executeQuery(sql);
			// executeQuery() : SELECT문 수행 메서드, ResultSet 반환
			
			
			
			// 3단계 : SQL을 수행해서 반환받은 결과 (Result Set)를
			//		   한 행씩 접근해서 컬럼 값 얻어오기
			
			while( rs.next() ) {
				 //rs.next() : rs가 참조하고 있는 ResultSet객체의
				 //				첫 번째 컬럼부터 순서대로 한 행씩 이동항며
				 //				다음 행이 있을 경우 true, 없으면 false반환
				
				// rs.get자료형("컬럼명")
				String empId = rs.getString("EMP_ID"); // "200" 
				// 현재 형의  "EMP_ID" 문자열 컬럼의 값을 얻어옴
				
				String empName = rs.getString("EMP_NAME"); // "선동일"
				
				int salary = rs.getInt("SALARY"); // 8,000,000
				
				//java.sql.Date
				Date hireDate = rs.getDate("HIRE_DATE"); // YYYY-MM-DD 만 넘어옴 1990-02-06
				
				
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s \n", 
								   empId, empName, salary, hireDate);
				
				// java.sql.Date의 toString()은 yyyy-mm-dd 형식으로 오버라이딩 되어있음.
				
			}
	
			
			
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 경로가 잘못 작성됨.");
			
			
		}catch(SQLException e) {
			// SQPException : DB 관련된 최상위 예외
			e.printStackTrace();
		}finally {
			
			// 4단계 : 사용한 JDBC 객체 자원 반환 ( close() 수행 )
			// Connection, Statement, ResultSet 순서
			try {
				if(rs != null) rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
				
			}
		}
		
	}
}
