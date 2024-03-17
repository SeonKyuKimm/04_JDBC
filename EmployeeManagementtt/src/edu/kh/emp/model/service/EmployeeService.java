package edu.kh.emp.model.service;

import java.sql.Connection;
import java.util.List;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.vo.Employee;
import eduk.kh.emp.model.dao.EmployeeDAO;

public class EmployeeService {

	private EmployeeDAO dao = new EmployeeDAO();

	/** 전체 사원 정보 조회 서비스
	 * @return list
	 */
	public List<Employee> selectAll() throws Exception {
		
		// 1 . 커넥션 생성
		Connection conn = getConnection();
		
		// 2 . DAO 호출
		List<Employee> list = dao.selectAll(conn);
		
		
		close(conn);
		
		
		return list;
	}

	/** 사원 정보 추가 서비스
	 * @param emp
	 * @return 1/0
	 * @throws Exception
	 */
	public int insertEmployee(Employee emp) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.insertEmployee(conn, emp);
		
		//dml 구문 수행 후 commit, rollback
		
		if(result > 0) commit(conn);
		else 		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	
	/** 사번이 일치하는 사원 정보 조회
	 * @param empId
	 * @return emp
	 */
	public Employee selectEmpId(int empId) throws Exception {
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpId(conn, empId );
				
		close(conn);
		
		return emp;
		
	}

	public int updateEmployee(Employee emp) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateEmployee(conn, emp);
		
		if(result > 0 ) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		
		return result;
	}

	
	/** 사번이 일치하는 사원 정보 삭제 서비스
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(int empId) throws Exception{

		Connection conn = getConnection();
		
		int result = dao.deleteEmployee(conn, empId);
		
		if(result > 0) commit(conn);
		else 		   rollback(conn);
		
		close(conn);
		
		
		return result;
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
