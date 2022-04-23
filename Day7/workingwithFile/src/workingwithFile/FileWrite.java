package workingwithFile;

import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {
	public static void main(String args[]) throws IOException {
		FileWriter writer=new FileWriter("D:\\classes\\write.txt");
		writer.write("shashi");
		writer.close();
	}

}
