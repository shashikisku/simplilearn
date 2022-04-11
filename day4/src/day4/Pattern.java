package day4;

import java.util.Scanner;

public class Pattern {

	public static void main(String[] args) {
		System.out.print("Enter a number to display pattern:");
		Scanner scan=new Scanner(System.in);
		int number=scan.nextInt();
		for(int row=1;row<=number;row++) {
			for(int column=1;column<=row;column++) {
				System.out.print("*");
			}
			System.out.println();
		}
		scan.close();
	}
}