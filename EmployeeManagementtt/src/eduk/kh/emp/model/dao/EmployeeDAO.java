package eduk.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.vo.Employee;

	//DB랑 직접적 연결
public class EmployeeDAO {

	
	// JDBC 객체 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// query 폴더에서 읽어온 SELECT 문을 저장해 둘 Properties
	private Properties prop; 

	public EmployeeDAO() {

		try {

			prop = new Properties(); // 객체 생성
			
 			prop.loadFromXML(new FileInputStream("query.xml")); 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 전체 사원 정보 조회 DAO
	 * @param conn
	 * @return list
	 */
	public List<Employee> selectAll(Connection conn) throws Exception {
		
		// (결과를 만들어 보내줘야하니까) 결과 저장용 변수를 선언
		List<Employee> list = new ArrayList<Employee>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			// Statement 객체 생성 바로 할거다
			stmt = conn.createStatement();
			
			// SQL 을 실어서 수행 후 결과(ResultSet) 반환 받기
			rs = stmt.executeQuery(sql);
			
			
			// 조회 결과를 얻어왔으니까, 한 행씩 접근하여
			// Employee 객체를 생성한 후, 각각 컬럼의 값을 담을것입니다
			// -> List 에 담기
			while( rs.next() ) {
					
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 에 저장된 컬럼은 문자열(varchar)컬럼이지만
				// 실제 저장된 값들은 숫자형태
				// -> DB에서 자동으로 형변환 진행해서 얻어옴
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, 
											departmentTitle, jobName, salary);
				
				list.add(emp); // list에 담기
				
			} // while문 종료
		
			
		}finally {
			
			close(rs);
			close(stmt);
		}
		return list;
	}

	
	
	public int insertEmployee(Connection conn, Employee emp) throws Exception {
		
		int result = 0;
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("insertEmployee");
			//INSERT INTO EMPLOYEE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL, DEFAULT)
			
			// 플레이스 홀더를 채워줄 PreaparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? (위치홀더 , 플레이스 홀더)
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

	
	//
	/**
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public Employee selectEmpId(Connection conn, int empId) throws Exception {
		
		Employee emp = null;
		
		
		try {
						
			String sql = prop.getProperty("selectEmpId");
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			// stmt ; ? (위치홀더 없을 때)
			// pstmt ; ? (위치홀더가 있을 때)
			// executeQuery - Select할때 다 씀
			// executeUpdate - DML (Update / Delete / Insert) -> int (성공한 행의 개수)
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");				
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs. getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
			
				
				emp = new Employee(empId, empName, empNo, email, phone, 
								   departmentTitle, jobName, salary);
			}
					
		}finally {
			
			close(rs);
			close(pstmt);
			
		}
		return emp;
	}

	/** 사번이 일치하는 사원 정보 수정 DAO
	 * @param conn
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Connection conn, Employee emp) throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("updateEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
			
		}
		
	
		return result;
	}

	
	
	/**  사번이 일치하는 사원 정보 삭제 DAO
	 * @param conn
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(Connection conn, int empId) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		
		return result;
	}

	
	
	
	
	
	
}