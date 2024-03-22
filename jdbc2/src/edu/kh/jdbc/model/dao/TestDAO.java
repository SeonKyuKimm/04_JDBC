package edu.kh.jdbc.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// import static 구문
// -> static이 붙은 필드, 메서드를 호출할 때
//		클래스명을 생략할 수 있도록 해주는 구문
import edu.kh.jdbc.common.JDBCTemplate;

import edu.kh.jdbc.model.vo.TestVo;

public class TestDAO {

	// DAO (DATA ACCESS OBJECT) : 데이터 접근객체
	// 데이터가 저장된 DB에 접근하는 개체
	// -> sql 수행, 결과 반환 받는 기능 수령
	
	// JDBC 객체를 참조하기 위한 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// xml로 SQL을 다룰것이다 -> Properties 객체 지휘
	private Properties prop;
	
	
	// 기본생성자
	public TestDAO() {
		// TestDAO 객체 생성시
		// test-quer.xml 파일의 내용을 읽어와
		// Properties 객체에 저장
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("Test_query.xml"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//_____________________________ DML 연습_____________________________
	// insert update delete 
	public int insert( Connection conn, TestVo vo1) throws SQLException{
		
		// 1 . 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2 . SQL 작성(Test_query.xml 에 작성된 SQL 얻어오기)
			String sql = prop.getProperty("insert");
			// INSERT INO TB_TEST
			// VALUES(? , ? , ?)
			
			// 3 . PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//conn.createStatement() + executeQuery(sql) 차이 
			
			// 4 . 위치 홀더( ? ) 에 알맞은 값 세팅
			pstmt.setInt(1, vo1.getTestNo() );
			pstmt.setString(2, vo1.getTestTitle()); // "제목 1"
			pstmt.setString(3, vo1.getTestContent()); // "내용1"
			
			// 5 . SQL(INSERT) 수행 후 결과 반환받기
			result = pstmt.executeUpdate(); // DML 모두 수행, 반영된 '행의 개수'(INT)로 반환
			
			
			/*┌─────────────────────────────────────────────────┐
			  │	 			차이점								│
			  │	stmt = conn.createStatement();					│
			  │	stmt.executeQuery(sql);							│
			  │													│
			  ├─────────────────────────────────────────────────┤
			  │													│
			  │	pstmt = conn.prepareStatement(sql);				│
			  │	pstmt = executeUpdate();  // sql 다시 넣지 마라!│
			  └─────────────────────────────────────────────────┘*/
			
		}finally {
			// 6 . 사용한 JDBC객체 자원 반환하기
			JDBCTemplate.close(pstmt);
		}
			// 7 . SQL 수행한 결과값 반환
		return result;
			
	}

	/** 번호가 일치하는 행의 제목, 내용을 수정하는 DAO
	 * @param conn
	 * @param vo
	 * @return result
	 */
	public int update(Connection conn, TestVo vo) throws Exception {
		
		// 결과 저장용 변수
		int result = 0;
		
		try {
			
						
			String sql = prop.getProperty("update");
			/*UPDATE TB_TEST SET
			 * TEST_TITLE ?,
			 * TEST_CONTENT ?,
			 * WHERE TEST_NO = ?
			 * 
			 * */
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTestTitle() );
			pstmt.setString(2, vo.getTestContent() );
			pstmt.setInt(3, vo.getTestNo());
			
			result = pstmt.executeUpdate();
			
			
		}finally {
		
			JDBCTemplate.close(pstmt);
			
		}
		
		
		return result;
	}

	public int delete(Connection conn, int testNo) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("delete");
			/*DELETE FROM TB_TEST
			WHERE TESTNO = ?*/
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, testNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
	
}
