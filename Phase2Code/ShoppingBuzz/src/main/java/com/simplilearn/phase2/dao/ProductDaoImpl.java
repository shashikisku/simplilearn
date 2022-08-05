package com.simplilearn.phase2.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.simplilearn.phase2.dto.Product;
import com.simplilearn.phase2.util.ConnectionManagerImpl;

public class ProductDaoImpl implements ProductDao {
	private Connection connection;

	public ProductDaoImpl() {
		connection = new ConnectionManagerImpl().getConnection();
	}

	@Override
	public Set<Product> getAllProducts() {
		Set<Product> allProducts = new HashSet<Product>();
		try {
			String query = "select * from products";
			Statement statment = connection.createStatement();
			ResultSet resultSet = statment.executeQuery(query);
			while (resultSet.next()) {
				Product product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3));
				allProducts.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allProducts;
	}

}
