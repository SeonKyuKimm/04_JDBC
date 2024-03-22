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
}
