package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Practice1 {

	public static void main(String[] args) {
		
		// 전지연 사원이 속해있는 부서원들을 조회하시오
		// 사번 사원명 전화번호 고용일 부서명 
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh_ksk";
			String pw ="kh1234";
			
			
			conn = DriverManager.getConnection( url, user, pw );
			
			String sql ="SELECT EMP_ID, EMP_NAME, PHONE,HIRE_DATE, DEPT_TITLE"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)"
					+ " WHERE DEPT_CODE = (SELECT DEPT_CODE"
					+ "	FROM EMPLOYEE"
					+ "	WHERE EMP_NAME = '전지연')";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				String empNo = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String phone = rs.getString("PHONE");
				String hireDate = rs.getString("HIRE_DATE");
				String deptTitle = rs.getString("DEPT_TITLE"); 
				
				System.out.printf("사번 %s \n ├ 이름 %s \n ├ 연락처 %s \n ├ 입사일 %s \n ├ 부서명 %s\n",
									empNo, empName,phone,hireDate,deptTitle);
				
			}
	
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(rs != null) rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close(); 
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
	
	
	
	
	
	
	}
}
