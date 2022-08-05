package com.simplilearn.phase2.dto;

public class Product {private int product_id;
private String product_name;
private double product_cost;
public Product() {
}
public Product(int product_id, String product_name, double product_cost) {
	super();
	this.product_id = product_id;
	this.product_name = product_name;
	this.product_cost = product_cost;
}
public int getProduct_id() {
	return product_id;
}
public void setProduct_id(int product_id) {
	this.product_id = product_id;
}
public String getProduct_name() {
	return product_name;
}
public void setProduct_name(String product_name) {
	this.product_name = product_name;
}
public double getProduct_cost() {
	return product_cost;
}
public void setProduct_cost(double product_cost) {
	this.product_cost = product_cost;
}
@Override
public String toString() {
	return "Product [product_id=" + product_id + ", product_name=" + product_name + ", product_cost=" + product_cost
			+ "]";
}


}
