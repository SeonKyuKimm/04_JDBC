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
	
}
