package com.simplilearn.trg.jdbc.dao;

import java.util.Set;
import com.simplilearn.trg.jdbc.dto.Product;

//client will communicate with service, service will communicate with dao
public interface ProductDao {
	public boolean addProduct(Product product);

	public boolean updateProductCost(int productId, double productCost);

	public boolean deleteProduct(int productId);

	public Set<Product> getAllProduct();

	public Product searchProductById(int productId);

	public Product searchProductByName(String productName);

}
