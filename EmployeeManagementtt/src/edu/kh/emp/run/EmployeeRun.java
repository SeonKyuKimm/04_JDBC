package edu.kh.emp.run;

import edu.kh.emp.view.EmployeeView;

public class EmployeeRun {

	public static void main(String[] args) {

		// 사용자에게 보여지는 화면 단
		// Run <-> View (출력 / 입력) <-> Service(비즈니스 로직 처리 : 데이터 가공, 트랜젝션 제어) 
		//     <-> DAO (DB연결, SQL 조회 실행, 결과 반환받) 
		//     <-> query.xml (SQU문 작성)
		new EmployeeView().displayMenu();
		
	}
}
