package com.simplilearn.trg.jdbc.service;

import java.util.Set;

import com.simplilearn.trg.jdbc.dao.ProductDao;
import com.simplilearn.trg.jdbc.dao.ProductDaoImpl;
import com.simplilearn.trg.jdbc.dto.Product;

public class ProductServiceImpl implements ProductService{
	private ProductDao dao;
	public ProductServiceImpl(){
		dao=new ProductDaoImpl();
	}

	@Override
	public boolean addProduct(Product product) {
		return dao.addProduct(product);
	}

	@Override
	public boolean updateProductCost(int productId, double productCost) {
		return dao.updateProductCost(productId, productCost);
	}

	@Override
	public boolean deleteProduct(int productId) {
		return dao.deleteProduct(productId);
	}

	@Override
	public Set<Product> getAllProduct() {
		return dao.getAllProduct();
	}

	@Override
	public Product searchProductById(int productId) {
		return dao.searchProductById(productId);
	}

	@Override
	public Product searchProductByName(String productName) {
		return dao.searchProductByName(productName);
	}

}
