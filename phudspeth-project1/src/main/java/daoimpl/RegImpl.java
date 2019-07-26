package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import beans.Employee;
import daointer.RegistrarInterface;

public class RegImpl implements RegistrarInterface {

	private Connection conn;
	
	public  RegImpl(){
		try {
			conn = ConnectionManager.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Connection getDBConnection() {
		return this.conn;
	}
	
	public List<Employee> getAllEmp() 
	{
		List<Employee> employees = new ArrayList<>();
		try {
			String sql = "SELECT * FROM employees WHERE access_level = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			ResultSet results = stmt.executeQuery();
			while(results.next()) 
			{
				String user = results.getString("emp_username");
				String pass = results.getString("emp_password");
				String email = results.getString("emp_email");
				String name = results.getString("emp_name");
				int id = results.getInt("emp_id");
				Employee employee = new Employee(user, pass, email, name, id, 1);
//				System.out.println(employee.getName());
//				System.out.println(employee.getEmail());
//				System.out.println(employee.getId());
				employees.add(employee);
			}
			
			return employees;
		} catch (SQLException e) {
			e.printStackTrace();
			return employees;
		}

	}
	
	public Employee getEmp(String username) 
	{
		Employee retemp = null;
		try {
			System.out.println(username);
			String sql = "SELECT emp_password, emp_email, emp_name, emp_id, access_level FROM employees WHERE emp_username = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet results = stmt.executeQuery();
			while(results.next())
			{
				String pass = results.getString("emp_password");
				String email = results.getString("emp_email");
				String name = results.getString("emp_name");
				int id = results.getInt("emp_id");
				int accessLevel = results.getInt("access_level");
				retemp = new Employee(username, pass, email, name, id, accessLevel);
			}
			
			return retemp;
		} catch (SQLException e) {
			e.printStackTrace();
			return retemp;
		}

	}

	public List<Employee> getAllMan() 
	{
		List<Employee> managers = new ArrayList<>();
		try {
			String sql = "SELECT * FROM employees WHERE access_level = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 2);
			ResultSet results = stmt.executeQuery();
			while(results.next()) 
			{
				String user = results.getString("emp_username");
				String pass = results.getString("emp_password");
				String email = results.getString("emp_email");
				String name = results.getString("emp_name");
				int id = results.getInt("emp_id");
				Employee manager = new Employee(user, pass, email, name, id, 2);
				managers.add(manager);
			}
			return managers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return managers;
		}
	}

	public void resetPassword(String username, String email) 
	{
		
	}

	public void addUser(String username, String password, String email, String name) 
	{
//		Employee e = new Employee(username, password, email, name, 1);
		try 
		{
			String sql = "INSERT INTO employees(emp_username, emp_password, emp_email, emp_name, access_level) "
					+ "VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, email);
			stmt.setString(4, name);
			stmt.setInt(5, 1);
			stmt.executeUpdate();
		}
		catch(Exception exc) 
		{
			exc.printStackTrace();
		}
	}

	public void removeUser(Employee e) 
	{
		String sql = "DELETE FROM employees WHERE user_id = ?";
		try 
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, e.getId());
			stmt.executeUpdate();
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}


	public void updateUser(Employee emp) 
	{
		try 
		{
			String sql = "UPDATE employees SET emp_password = ?, emp_email = ?, emp_name = ? WHERE emp_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, emp.getPassword());
			stmt.setString(2, emp.getEmail());
			stmt.setString(3, emp.getName());
			stmt.setInt(4, emp.getId());
			stmt.executeUpdate();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
