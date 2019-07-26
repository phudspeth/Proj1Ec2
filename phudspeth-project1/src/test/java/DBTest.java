import java.util.List;

import org.junit.Test;

import beans.Employee;
import daoimpl.RegImpl;

public class DBTest {

	@Test
	public void testGetEmp() {
		RegImpl dao = new RegImpl();
		List<Employee> emps = dao.getAllEmp();
		System.out.println(emps);
	}
	
}
