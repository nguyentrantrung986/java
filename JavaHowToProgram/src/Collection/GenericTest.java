package Collection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GenericTest<T> {
	public static void main(String[] args){
//		System.out.println("Max of 7, 4, 5 is"+maximum(new Integer(7), new Integer(4), new Integer(5)));
		writeToFile(new Color("Black"));
	}
	
	public static void writeToFile(Serializable s){
		try {
			ObjectOutputStream os = new ObjectOutputStream(Files.newOutputStream(Paths.get("obj")));
			os.writeObject(s);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dummyGenericMethod(GenericTest<? super Color> gt){
		
	}
}
