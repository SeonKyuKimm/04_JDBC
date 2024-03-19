package edu.kh.jdbc.main.model.service;

import java.sql.Connection;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.main.model.DAO.MainDAO;
import edu.kh.jdbc.member.model.dto.Member;

public class MainService {

	private MainDAO dao = new MainDAO();

	
	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return MEMBER
	 */
	public Member login(String memberId, String memberPw) throws Exception {
		
		// 1 . connection 생성
		Connection conn = getConnection();
		
		// 2 . DAO 호출
		Member member = dao.login(conn, memberId, memberPw);
		
		// 3 .   Connection 객체 반환
		close(conn);
		
		// 4 . 얻어온 결과 view 쪽으로 반환
		return member;
	}


	/** 아이디 중복 검사 서비스
	 * @param memberId
	 * @return result
	 */
	public int idDuplicationCheck(String memberId) throws Exception {

		Connection conn = getConnection();
		
		int result = dao.idDuplicationCheck(conn, memberId); // 1 / 0 으로 반환해줌
		
		close(conn);
		
		return result;
	}


	/** 회원 가입 서비스
	 * @param member
	 * @return
	 */
	public int signUp(Member member) throws Exception {
		
		Connection conn = getConnection();
		
		// DAO 호출
		int result = dao.signUp(conn, member);// INSERT 수행
		
		// 트랜젝션 처리
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}
	
}
