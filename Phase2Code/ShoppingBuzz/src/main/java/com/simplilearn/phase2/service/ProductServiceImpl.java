package com.simplilearn.phase2.service;

import java.util.Set;

import com.simplilearn.phase2.dao.ProductDao;
import com.simplilearn.phase2.dao.ProductDaoImpl;
import com.simplilearn.phase2.dto.Product;

public class ProductServiceImpl implements ProductService {
	private ProductDao dao;

	public ProductServiceImpl() {
		dao = new ProductDaoImpl();
	}

	@Override
	public Set<Product> getAllProducts() {
		return dao.getAllProducts();
	}

}
