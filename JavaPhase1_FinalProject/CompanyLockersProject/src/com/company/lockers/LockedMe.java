package com.company.lockers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LockedMe {
	final static String projectPath=new File("").getAbsolutePath();
	static Scanner scanner=new Scanner(System.in);
	static int end;

	public static void main(String[] args) throws IOException {
		do{
			displayMenu();
			System.out.print("Enter your choice:");
			int choice=scanner.nextInt();
			switch(choice){
			case 1:getAllFiles();
			break;
			case 2:createFiles();
			break;
			case 3:deleteFiles();
			break;
			case 4:searchFiles();
			break;
			case 5:System.exit(1);
			}
		}
		while(end!=5);



	}
	public static void displayMenu() {
		System.out.println("***************************************************");
		System.out.println("\tWelcome to Company Lockers - LockedMe.com");
		System.out.println("\tDeveloper name : Shashi Kisku");
		System.out.println("***************************************************");
		System.out.println("\t1. Display all the Files");
		System.out.println("\t2. Add Files to existing directory");
		System.out.println("\t3. Delete a File");
		System.out.println("\t4. Search a File");
		System.out.println("\t5. Exit");
		System.out.println("***************************************************");	
	}
	public static void getAllFiles() {
		int index=1;
		File file=new File(projectPath);
		File[] listOfFiles=file.listFiles();
		System.out.println("List of files present in "+file);
		for(File f:listOfFiles) {
			System.out.println(index+++") "+f.getName());
		}
		String userDirectory=new File("").getAbsolutePath();
		System.out.println(userDirectory);
		System.out.print("Enter 7 to go to MAIN MENU or 5 to EXIT the console:");
		end=scanner.nextInt();

	}
	public static void createFiles() throws IOException {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String data=null;
		System.out.print("Enter the name of file: ");
		String fileName=scanner.next();
		String folderPath=projectPath+fileName;
		FileWriter writer = new FileWriter(folderPath);
		System.out.print("Enter number of lines of content:");
		int lines=scanner.nextInt();
		for(int line=1;line<=lines;line++) {
			System.out.println("Enter content for line "+line);
			data=buffer.readLine();
			writer.write(data+"\n");
		}
		System.out.println("FILE created successfully");
		writer.close();
		System.out.print("Enter 7 to go to MAIN MENU or 5 to EXIT the console:");
		end=scanner.nextInt();

	}
	public static void deleteFiles() {
		File []listOfFiles=new File(projectPath).listFiles();
		boolean found=false;
		System.out.print("Enter file name with EXTn to be deleted:");
		String deleteFile=scanner.next();
		if(deleteFile.equalsIgnoreCase(".classpath")||deleteFile.equalsIgnoreCase(".gitignore")||
				deleteFile.equalsIgnoreCase(".project")||deleteFile.equalsIgnoreCase(".settings")||
				deleteFile.equalsIgnoreCase("bin")||deleteFile.equalsIgnoreCase("src")) {
			System.out.println("***WARNING***, i am not authorized to delete the mentioned FILE");
			return;
		}
		for(File file:listOfFiles) {
			if(deleteFile.equalsIgnoreCase(file.getName())) {
				file.delete();
				System.out.println("FILE DELETED SUCCESSFULLY");
				found=true;
			}
		}
		if(found==false) {
			System.out.println("CANNOT FIND THE FILE");
		}
		System.out.print("Enter 7 to go to MAIN MENU or 5 to EXIT the console:");
		end=scanner.nextInt();

	}
	public static void searchFiles() {
		File []listOfFiles=new File(projectPath).listFiles();
		boolean found=false;
		System.out.print("Enter file name with EXTn you want Search:");
		String searchFile=scanner.next();
		for(File file:listOfFiles) {
			if(searchFile.equalsIgnoreCase(file.getName())) {
				System.out.println("FILE FOUND");
				found=true;
			}
		}
		if(found==false) {
			System.out.println("CANNOT FIND THE FILE");
		}
		System.out.print("Enter 7 to go to MAIN MENU or 5 to EXIT the console:");
		end=scanner.nextInt();

	}
}
