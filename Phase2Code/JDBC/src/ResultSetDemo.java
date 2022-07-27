import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetDemo {

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
			String query = "select * from studentdata";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			System.out.println("Roll_no" + "\t" + "First" + "\t" + "Last" + "\t" + "Standard" + "\t" + "Grade");
			while (resultSet.next()) {
				int roll = resultSet.getInt(1);
				String first = resultSet.getString("first_name");
				String last = resultSet.getString(3);
				int standard = resultSet.getInt(4);
				String grade = resultSet.getString(5);
				System.out.println(roll + "\t" + first + "\t" + last + "\t" + standard + "\t\t" + grade);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
