package emp2.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import emp2.model.service.Employee2Service;
import emp2.model.vo.Employee2;

public class Employee2View {

	private Scanner sc = new Scanner(System.in);
	private Employee2Service service = new Employee2Service();

	public void displayMenu() {

		int input = 0;
		
		do {
			
			try {
				
				/* View 에 나타날 메뉴 */
				
				
				System.out.println("┌─────────────────────────────────────────────────┐");
				System.out.println("├　　　　　　　　사원 관리 프로그램　　　　　　　 ┤");
				System.out.println("└─────────────────────────────────────────────────┘");
				
				System.out.println("┌─────────────────────────────────────────────────┐");
				System.out.println("│ 1 . 전체 사원 정보 조회　　　　　　　　　　　　 │");
				System.out.println("│ 2 . 새로운 사원 추가　　　　　　　　　　　　　　│");
				System.out.println("│ 3 . 사번이 일치하는 사원 정보 조회　　　　　　　│");
				System.out.println("│ 4 . 사번이 일치하는 사원 정보 수정　　　　　　　│");
				System.out.println("│ 5 . 사번이 일치하는 사원 정보 삭제　　　　　　　│");
				System.out.println("└─────────────────────────────────────────────────┘");
				
				System.out.println("              ┌───────────────────┐");
				System.out.println("              │ 0 . 프로그램 종료 │");
				System.out.println("              └───────────────────┘");
				System.out.println("┌─────────────────┐");
				System.out.println("│ 메뉴 선택  >>>> │ ");
				System.out.print("└─────────────────┘     ");
				input = sc.nextInt();
				
				
				switch (input) {
				
				case 1 : selectAll(); break;
				case 2 : //insertEmployee(); break;
				case 3 : //selectEmpId(); break;
				case 4 : //updateEmployee(); break; 
				case 5 : //deleteEmployee(); break;
				case 0 : System.out.println("프로그램을 종료합니다"); break;
				
				default : System.out.println(" 메뉴에 있는 번호만 입력하세요");
					
				}
							
			}catch(InputMismatchException e){
				System.out.print(" 정수만 입력하세요 ~");
				input = -1;
				sc.nextLine();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}while ( input != 0);
		
	}
	
	
	public void selectAll() throws Exception  {

		System.out.println("┌───────────────────────┐");
		System.out.println("│* 전체 사원 정보 조회 *│");
		System.out.println("└───────────────────────┘");
		System.out.println();
	
	
		List<Employee2> empList = service.selectAll();
		
		printAll(empList);
		
	
	
	
	
	
	
	
	
	}

	
	
	// 보조 메서드

	private void printAll(List<Employee2> empList) {
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
		}else{	
			System.out.println("사번  |   이름  | 주민 등록 번호 |        이메일        |  전화 번호  |  부서  | 직책 | 급여 ");
			System.out.println(
					"────────────────────────────────────────────────────────────────────────────────────────────────");
			for (Employee2 emp : empList) {
				System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n", emp.getEmpId(), emp.getEmpName(),
						emp.getEmpNo(), emp.getEmail(), emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(),
						emp.getSalary());
			}
		}
		
	}
	
	
	
	
}
