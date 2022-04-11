package day4;

import java.util.Scanner;

public class EvenOdd {

	public static void main(String[] args) {		
		System.out.print("Enter any number : ");
		Scanner scanner=new Scanner(System.in);
		int a=scanner.nextInt();
		if(a%2==0)
			System.out.println(a+" is EVEN");
		else
			System.out.println(a+" is ODD");
		scanner.close();
	}

}
