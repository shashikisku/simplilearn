package workingwithFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {
	public static void main(String args[]) throws IOException {
		File file=new File("D:\\classes\\marks.txt");
		FileReader reader;
		String data = new String();
		reader=new FileReader(file);
		try (BufferedReader buffer = new BufferedReader(reader)) {
			data=buffer.readLine();
			
			while(data!=null) {
				String array[]=data.split(",");
				System.out.println(array[0]+"->"+array[1]);
				data=buffer.readLine();
			}
		}
	}
}