package emp2.model.service;

import java.sql.Connection;
import java.util.List;

import static emp2.common.JDBCTemplate_for_Employee2.*;
import emp2.model.dao.Employee2DAO;
import emp2.model.vo.Employee2;

public class Employee2Service {

	Employee2DAO dao = new Employee2DAO();

		
	public List<Employee2> selectAll() throws Exception {
		
		Connection conn = getConnection();
		
		List<Employee2> list = dao.selectAll(conn);
		
		close(conn);
		return list;
	}


	/** 사원정보 추가
	 * @param emp
	 * @return 1 / 0
	 */
	public int insertEmployee(Employee2 emp) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.insertEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}


	/** 사번이 일치하는 사원 정보 조회
	 * @param empId
	 * @return
	 */
	public Employee2 selectEmpId(int empId) throws Exception {
		
		Connection conn = getConnection();
		
		Employee2 emp = dao.selectEmpId(conn, empId);
		
		close(conn);
		
		
		
		return emp;
	}

	

	/** 4 . 사번이 일치하는 사원 정보 수정 (update , dml )
	 * @param emp
	 * @return result
	 */
	public int updateEmployee(Employee2 emp) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);	
		
		return result;
	}


	/** 사번이 일치하는 사원 정보 삭제 서비스
	 * @param empId
	 * @return result
	 */
	public int deleteEmployee(int empId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.deleteEmployee(conn, empId);
		
		if( result > 0 ) commit(conn);
		else			 rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
