package daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager 
{
	public static final String URL = "jdbc:oracle:thin:@project1db.cn3lqnky3avv.us-east-1.rds.amazonaws.com" + 
			":1521:ORCL";
	public static final String username="proj1expensereim";
	public static final String password= "reimpass";
	
	
	public static Connection getConnection() throws SQLException 
	{
		try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return DriverManager.getConnection(URL,username,password);
	}
}
