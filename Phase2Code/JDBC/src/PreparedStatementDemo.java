import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementDemo {

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
			String query = "insert into studentdata values(?,?,?,?,?)";

			// https://www.geeksforgeeks.org/difference-between-statement-and-preparedstatement/
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, 106);
			preparedStatement.setString(2, "Anuj");
			preparedStatement.setString(3, "Rude");
			preparedStatement.setInt(4, 10);
			preparedStatement.setString(5, "A");

			// Insert data to Table
			int returnValue = preparedStatement.executeUpdate();
			if (returnValue > 0)
				System.out.println("Record inserted: " + returnValue);
			else
				System.out.println("Record not inserted");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
