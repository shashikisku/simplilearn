package com.simplilearn.trg.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.simplilearn.trg.jdbc.dto.Product;
import com.simplilearn.trg.jdbc.util.ConnectionManagerImpl;

public class ProductDaoImpl implements ProductDao {
	private Connection connection;

	public ProductDaoImpl() {
		connection = new ConnectionManagerImpl().getConnection();
	}

	@Override
	public boolean addProduct(Product product) {
		boolean isProductAdded = false;
		String query = "insert into products values(?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, product.getProduct_id());
			preparedStatement.setString(2, product.getProduct_name());
			preparedStatement.setDouble(3, product.getProduct_cost());
			int returnVal = preparedStatement.executeUpdate();
			if (returnVal > 0)
				isProductAdded = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isProductAdded;
	}

	@Override
	public boolean updateProductCost(int productId, double productCost) {
		boolean isupdated = false;
		try {
			String query = "update products set product_cost=? where product_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1, productCost);
			preparedStatement.setInt(2, productId);
			int returnValue = preparedStatement.executeUpdate();
			if (returnValue > 0)
				isupdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isupdated;
	}

	@Override
	public boolean deleteProduct(int productId) {
		boolean isDeleted = false;
		try {
			Statement statement = connection.createStatement();
			String query = "delete from products where product_id=" + productId;
			int returnValue = statement.executeUpdate(query);
			if (returnValue > 0)
				isDeleted = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isDeleted;
	}

	@Override
	public Set<Product> getAllProduct() {
		Set<Product> products = new HashSet<Product>();
		try {
			Statement statement = connection.createStatement();
			String query = "select * from products";
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Product product = new Product();
				product.setProduct_id(resultSet.getInt(1));
				product.setProduct_name(resultSet.getString(2));
				product.setProduct_cost(resultSet.getDouble(3));
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Product searchProductById(int productId) {
		Product product = null;
		String query = "select * from products where product_id =?";
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, productId);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product searchProductByName(String productName) {
		Product product = null;
		String query = "select * from products where product_name =?";
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, productName);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

}
