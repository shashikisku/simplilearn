package day4;

import java.util.Scanner;

public class SumOfNaturalNo {

	public static void main(String[] args) {
		System.out.print("Enter a natural number:");
		Scanner scan=new Scanner(System.in);
		int number=scan.nextInt();
		System.out.print("Sum of "+number+" is:");
		for(int i=number;i>=1;i--)
			number=number+i;
		System.out.print(number);
		scan.close();;

	}

}
