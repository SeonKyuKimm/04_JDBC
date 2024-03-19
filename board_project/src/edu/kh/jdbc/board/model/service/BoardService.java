package edu.kh.jdbc.board.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dto.Board;

import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {

	BoardDAO dao = new BoardDAO();

	/** 게시글 목록 조회 서비스
	 * @return boardList
	 */
	public List<Board> selectAllBoard() throws Exception {
		
		// 커넥션 생성
		Connection conn = getConnection();		
		
		// DAO 메서드 호출
		List<Board> boardList = dao.selectAllBoard(conn);
		
		// 커넥션 반환
		close(conn);
		
		// 결과 반환
		return boardList;
	}

	
	
	/** 게시글 상세 조회하는 서비스
	 * @param input
	 * @param memberNo
	 * @return board
	 */
	public Board selectBoard(int input, int memberNo) throws Exception {
				
		// 게시글 상세조회 호출
		// 조회수 증가하는 (UPDATE 문) 호출
		
		// 1 . Connection 생성
		Connection conn = getConnection();
		
		// 2 . 게시글 상세 조회 DAO 호출
		Board board = dao.selectBoard(conn, input); 
		
		// 3 . 게시글이 조회된 경우
		if(board != null) {
			
			// 4 . 조회수 증가하는 DAO 호출
			// 단, 게시글 작성자와 로그인한 회원이 다를 경우에만 조회수 증가시키기
			if( board.getMemberNo() != memberNo) {
   // 조회한 게시글 작성한 회원번호 != 로그인한 회원번호 
				
				// 5 . 조회 수 증가 DAO 메서드 호출 (UPDATE)
				int result = dao.updateReadCount(conn, input);
				
				// 6 . 트랜젝션 제어 처리 + 데이터 동기화 처리( 자바랑 DB랑 동기화)
				if( result >0 ) {
					
					commit(conn);
					
					// 조회된 board의 조회수 0
					// DB 의 조회수는 1
					// -> 조회 결과인 board의 조회수도 1 증가
					board.setReadCount( board.getReadCount() + 1 );
					
				}else {
					rollback(conn);
				}
			}
		}
		
		// 7 . 커넥션 반환
		close(conn);
		
		// 8 . 결과 반환
		return board;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
