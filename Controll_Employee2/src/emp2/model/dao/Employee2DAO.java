package emp2.model.dao;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static emp2.common.JDBCTemplate_for_Employee2.*;
import emp2.model.vo.Employee2;

public class Employee2DAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private Properties prop;
	
	public Employee2DAO() {
		
		try {
			
			prop = new Properties();
			prop.loadFromXML( new FileInputStream("query_for_emp2.xml") );
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Employee2> selectAll(Connection conn) throws Exception {
		
		List<Employee2> list = new ArrayList<Employee2>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			while (rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee2 emp = new Employee2(empId, empName, empNo, email, phone, deptTitle,
											  jobName, salary);
				
				list.add(emp);
			}
			
			
			
		}finally {
			close(rs);
			close(stmt);
			
		}
		return list;
	}

	public int insertEmployee(Connection conn, Employee2 emp) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("insertEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, emp.getEmpId() );
			
			pstmt.setString(2, emp.getEmpName());
			
			pstmt.setString(3, emp.getEmpNo());
			
			pstmt.setString(4, emp.getEmail());
			
			pstmt.setString(5, emp.getPhone());
			
			pstmt.setString(6, emp.getDeptCode());
			
			pstmt.setString(7, emp.getJobCode());
			
			pstmt.setString(8, emp.getSalLevel());
			
			pstmt.setInt(9, emp.getSalary());
			
			pstmt.setDouble(10, emp.getBonus());
			
			pstmt.setInt(11, emp.getManagerId());
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
