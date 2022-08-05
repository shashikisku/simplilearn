package com.simplilearn.phase2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.simplilearn.phase2.dto.User;
import com.simplilearn.phase2.util.ConnectionManagerImpl;

public class UserDaoImpl implements UserDao {
	private Connection connection;

	public UserDaoImpl() {
		connection = new ConnectionManagerImpl().getConnection();
	}

	@Override
	public boolean validateUser(User user) {
		boolean isUserValid = false;
		String query = "select * from userdetails where user_name=? and password=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next() == true) {
				isUserValid = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUserValid;
	}

	@Override
	public boolean registerUser(User user) {
		boolean isUserRegister = false;
		String query = "insert into userdetails values(?,?,?,?,?)";
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, user.getFirstname());
			prepareStatement.setString(2, user.getLastname());
			prepareStatement.setString(3, user.getAddress());
			prepareStatement.setString(4, user.getUsername());
			prepareStatement.setString(5, user.getPassword());
			int data = prepareStatement.executeUpdate();
			if (data > 0) {
				isUserRegister = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUserRegister;
	}
}
