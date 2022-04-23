package workingwithFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileClass {
	public static void main(String args[]) throws IOException {
		File file=new File("D:\\classes\\abc.txt");
		FileReader reader=new FileReader(file);
		int word=reader.read();
		while(word!=-1) {
			System.out.print((char)word);
			word=reader.read();
		}
		reader.close();
	}

}
