package daointer;

import java.util.List;

import beans.Employee;

public interface RegistrarInterface 
{
	public List<Employee> getAllEmp();
	public List<Employee> getAllMan();
	public void resetPassword(String username, String password);
	public void addUser(String username, String password, String email, String name);
	public void removeUser(Employee e);
	public void updateUser(Employee emp);
	public Employee getEmp(String username);
}
