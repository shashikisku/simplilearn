package com.company.lockers;

public class StringExample {
	public static void main(String args[]) {
		String s1="ho";
		String s2="ho";
		String s3=new String("ho");
		String s4=new String("ho");
		System.out.println(s1==s2);
		System.out.println(s1==s3);
		System.out.println(s3==s4);
		System.out.println(s1.equals(s2));
		System.out.println(s2.equals(s4));
		System.out.println(s3.equals(s4));
	}

}
