package edu.kh.jdbc.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.main.model.service.MainService;

public class MainView {

	private Scanner sc = new Scanner(System.in);
	
	private MainService service = new MainService();
	
	/**
	 * 메인 메뉴 출력 View
	 */
	public void mainMenu() {
		
		int input = 0; // 메뉴선택용 변수
		
		do {
			
			try {
				
				// 로그인 X (제일먼저 로그인 했나 안했나 따짐)
				if(Session.loginMember == null) { // 로그인 X
					
					System.out.println("\n=====회원제 게시판 프로그램 =====\n");
					System.out.println("1 . 로그인");
					System.out.println("2 . 회원가입");
					System.out.println("0 . 프로그램 종료");
					
					System.out.print("\n메뉴선택 : ");
					input = sc.nextInt();
					sc.nextLine(); //  입력버퍼 개행문자 제거
					
					switch(input) {
					
						case 1 : login(); break;
						case 2 : //signUp(); break;
						case 0 : System.out.println("\n===프로그램 종료\n");
						default : System.out.println("\n===메뉴 번호만 입력해주세요===\n");
														
					}
					
				}else { // 로그인 O
					
					System.out.println("\n=== 로그인 메뉴 ===\n");
					System.out.println("1 . 회원 기능");
					System.out.println("2 . 게시판 기능");
					System.out.println("3 . 로그아웃");
					System.out.println("0 . 프로그램 종료");
					
					System.out.print("\n메뉴선택 : ");
					input = sc.nextInt();
					sc.nextLine(); //  입력버퍼 개행문자 제거
					
					switch(input) {
						
						case 1 : // 회원기능View
						case 2 : // 게시판기능View	
						case 3 : System.out.println("\n=== 로그아웃되었습니다 ===\n");
								 Session.loginMember = null;
								 break;
						case 0 : System.out.println("\n===프로그램 종료\n");
						default : System.out.println("\n===메뉴 번호만 입력해주세요===\n");
						
					}
					
				}
				
				
				
				
				
			}catch(InputMismatchException e) {

				System.out.println("\n*** 입력 형식이 올바르지 않습니다 ***\n");
				sc.nextLine();// 입력 버퍼에 잘못된 문자열 제거
				input =  -1; // while 문 종료 방지
			}
			
			
		}while(input != 0);
		
		
	}
	
	/**
	 * 로그인
	 */
	public void login() {
	
		System.out.print("\n[로그인]\n");
		
		System.out.print("아이디 : ");
		String memberId = sc.next();
		
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
	
		try {
			// 로그인 서비스 호출
			// -> 반환 받은 결과는 Session.loginMember();  에 저장한다
			Session.loginMember = service.login(memberId, memberPw);
			
			if(Session.loginMember == null) { // 로그인 실패
				System.out.println("\n*** 아이디 / 비밀번호가 일치하지 않습니다. ***\n");
			}else { // 로그인 성공
				
				 System.out.printf("\n====%s 님 환영합니다====\n",
						 		   Session.loginMember.getMemberName() );
			}
			
			
		}catch(Exception e){
			System.out.println("\n *** 로그인 중 예외 발생 ***\n");
			e.printStackTrace();
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
