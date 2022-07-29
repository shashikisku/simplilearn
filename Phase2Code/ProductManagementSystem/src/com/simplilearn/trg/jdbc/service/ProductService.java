package com.simplilearn.trg.jdbc.service;

import java.util.Set;
import com.simplilearn.trg.jdbc.dto.Product;

public interface ProductService {
	public boolean addProduct(Product product);

	public boolean updateProductCost(int productId, double productCost);

	public boolean deleteProduct(int productId);

	public Set<Product> getAllProduct();

	public Product searchProductById(int productId);

	public Product searchProductByName(String productName);

}
