package com.simplilearn.trg.jdbc.client;

import java.util.Scanner;
import java.util.Set;

import com.simplilearn.trg.jdbc.dto.Product;
import com.simplilearn.trg.jdbc.service.ProductService;
import com.simplilearn.trg.jdbc.service.ProductServiceImpl;

/*
 * Client -> Service -> DAO -> Database
 * DTO-> (Data Transfer Object)Product detail || Signature
 */
public class Client {
	/**
	 * @param Product
	 */
	/**
	 * @param Scanner
	 */
	private static Scanner scan = new Scanner(System.in);

	public static void main(String args[]) {
		ProductService service = new ProductServiceImpl();

		int exit;
		int product_id;
		String product_name;
		Double product_cost;
		do {
			switch (menuChoice()) {
			case 1:
				System.out.print("Enter Product_Id:");
				product_id = scan.nextInt();
				System.out.print("Enter Product_Name:");
				product_name = scan.next();
				System.out.print("Enter Product_Cost:");
				product_cost = scan.nextDouble();
				Product product = new Product(product_id, product_name, product_cost);
				boolean isProductAdded = service.addProduct(product);
				if (isProductAdded == true) {
					System.out.println("Product added successfully");
				} else {
					System.out.println("Product not added");
				}
				break;

			case 2:
				System.out.print("Enter Product_Id:");
				product_id = scan.nextInt();
				System.out.print("Enter Product_Cost:");
				product_cost = scan.nextDouble();
				boolean isupdated = service.updateProductCost(product_id, product_cost);
				if (isupdated)
					System.out.println("product updated");
				else
					System.out.print("not updated");
				break;

			case 3:
				System.out.print("Enter Product_Id:");
				product_id = scan.nextInt();
				boolean isDeleted = service.deleteProduct(product_id);
				if (isDeleted)
					System.out.println("product deleted");
				else
					System.out.println("Not deleted");
				break;

			case 4:
				Set<Product> allProducts = service.getAllProduct();
				for (Product prod : allProducts) {
					System.out.println(prod);
				}
				break;

			case 5:
				System.out.print("Enter Product_Id:");
				product_id = scan.nextInt();
				Product product2 = service.searchProductById(product_id);
				if (null != product2)
					System.out.println(product2);
				else
					System.out.println("Product ID does not exist");
				break;

			case 6:
				System.out.print("Enter Product_Name:");
				product_name = scan.next();
				Product product3 = service.searchProductByName(product_name);
				if (null != product3)
					System.out.println(product3);
				else
					System.out.println("Product name does not exist");
				break;
			case 8:
				System.exit(1);
				break;

			default:
				System.out.println("Enter valid option");
			}
			System.out.println("************************************");
			System.out.println("7 to GOTO MainMenu....8 to EXIT");
			System.out.println("************************************");
			exit = scan.nextInt();
			if (exit == 8)
				scan.close();
		} while (exit != 8);
	}

	private static int menuChoice() {
		System.out.println("******Select the OPERATION******");
		System.out.println("1.ADD Product\t\t2.UPDATE product cost");
		System.out.println("3.DELETE Product\t4.GET all product");
		System.out.println("5.SEARCH by ID\t\t6.SEARCH by Name");
		System.out.println("************************************");
		int choice = scan.nextInt();
		return choice;
	}
}
