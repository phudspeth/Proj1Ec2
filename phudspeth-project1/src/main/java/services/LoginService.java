package services;
import java.util.List;

import beans.Employee;

public class LoginService 
{
	public static boolean userAuthenticate(Employee emp, String username, String password) 
	{
//		for(Employee em: emps) 
//		{
//			System.out.println("Comparing username: " + em.getUsername() + " to " + username);
//			System.out.println("Comparing password: " + em.getPassword() + " to " + password);
			if(emp.getUsername().equals(username) && emp.getPassword().equals(password)) 
			{
				return true;
			}
//		}
			else 
			{
				return false;
			}
	}
}
