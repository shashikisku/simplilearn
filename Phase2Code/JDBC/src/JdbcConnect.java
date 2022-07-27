import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnect {

	public static void main(String[] args) {
		try {
			// 1. load the driver(provide driver name present in connector jar
			Class.forName("com.mysql.cj.jdbc.Driver");
			/*
			 * 2. Establish connection with database url-url of database with which
			 * application need to be connected username password
			 */
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mytrgdb", "root", "admin");
			System.out.println("Connected to database");
			Statement statement = connection.createStatement();

			// Insert data to Table
			String query = "insert into studentdata values(104,'Seeta','Sharma',10,'A++')";
			int returnValue = statement.executeUpdate(query);
			if (returnValue > 0)
				System.out.println("Record inserted: " + returnValue);
			else
				System.out.println("Record not inserted");

			// Update existing data -- SET SQL_SAFE_UPDATES=0;-- to use where command
			String updatequery = "update studentdata set grade='A+' where roll_no=104";
			returnValue = statement.executeUpdate(updatequery);
			if (returnValue > 0)
				System.out.println("Record updated");
			else
				System.out.println("NOt updated");

			// delete data from table
			String deletequer = "delete from studentdata where roll_no=105";
			returnValue = statement.executeUpdate(deletequer);
			if (returnValue > 0)
				System.out.println("Record deleted");
			else
				System.out.println("NOt deleted");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
