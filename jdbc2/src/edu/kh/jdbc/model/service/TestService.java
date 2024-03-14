package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.sql.SQLException;

//import static 구문
//-> static이 붙은 필드, 메서드를 호출할 때
//		클래스명을 생략할 수 있도록 해주는 구문
import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.model.dao.TestDAO;
import edu.kh.jdbc.model.vo.TestVo;

public class TestService {
	// Service : 비즈니스 로직 (데이터 가공,
	// 트랜젝션 제어)처리
	// -> 실체 프로그램이 지공하는 기능을 모아놓는 클래스

	// 하나의 Service메서드에서 n개의 DAO메서드를 호출하여
	// 이를 하나의 트랜젝션 단위로 취급하여
	// 한번에 commit, rollback을 수행할 수 있다.
	private TestDAO dao = new TestDAO();

	// ------------- dml
	public int insert(TestVo vo1) throws SQLException {

		// 커넥션 생성
		Connection conn = getConnection();

		// DAO 메서드 호출하여 수행 후 결과 반환받기
		// -> Service에서 생성한 Connection 객체를 반드시 같이 전달!!!
		int result = dao.insert(conn, vo1);

		// 트랜젝션 제어

		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		// connection 반환
		close(conn);

		// 결과 반환
		return result;

	}

	/**
	 * 3행 삽입 서비스
	 * 
	 * @param vo2
	 * @param vo3
	 * @param vo4
	 * @return
	 */
	public int insert(TestVo vo1, TestVo vo2, TestVo vo3) throws Exception {

		// 1 . Connection 생성 (무조건 첫번째로 만듬)
		Connection conn = getConnection();

		int result = 0; // insert 3 회 모두 성공시 1, 아님 0 지정

		int res1 = dao.insert(conn, vo1); // 1 or 0의 값이 res2 에 저장
		int res2 = dao.insert(conn, vo2);
		int res3 = dao.insert(conn, vo3);

		if (res1 + res2 + res3 == 3) { // 모두 insert 성공한 경우

			commit(conn);
			result = 1;

		} else {
			rollback(conn);
		}
		return result; // insert 3 회 결과 반환 ( 1 / 0 )
	}

	/**
	 * 번호가 일치하는 행의 제목, 내용 수정 service
	 * 
	 * @param vo
	 * @return result
	 */
	public int update(TestVo vo) throws Exception {

		Connection conn = getConnection();

		int result = dao.update(conn, vo);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;
	}

	public int delete(int testNo) throws Exception {

		Connection conn = getConnection();

		int result = dao.delete(conn, testNo);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);
		return result;
	}

}
