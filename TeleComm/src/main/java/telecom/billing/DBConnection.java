package telecom.billing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection con = null;
    public static Connection getConnection() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/telecom_billing_system", "root", "My123sql");
			//System.out.println("Connection Done");
		} catch (Exception e) {
			System.out.println("Connection Not Createted : " + e);
		}
		return con;
	}
}