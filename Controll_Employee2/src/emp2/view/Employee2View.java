package emp2.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import emp2.model.service.Employee2Service;

public class Employee2View {

	private Scanner sc = new Scanner(System.in);
	private Employee2Service emp2Service = new Employee2Service();

	public void displayMenu() {

		int input = 0;
		
		do {
			
			try {
				
				/* View 에 나타날 메뉴 */
				
				
				switch(input) {
				
				case : ; break;
				case : ; break;
				case : ; break;
				case : ; break;
				case : ; break;
				
				
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
}
