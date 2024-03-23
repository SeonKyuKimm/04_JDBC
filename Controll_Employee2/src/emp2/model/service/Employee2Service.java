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
}
