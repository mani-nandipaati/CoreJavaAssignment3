package com.cts.serialization.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtils {
	
	public static void serialize(Object obj, String fileName) {
		try
		{    
			FileOutputStream fileIn = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileIn); 
			out.writeObject(obj); 
			out.close(); 
			fileIn.close(); 
		} 
		catch(Exception ex) 
		{ 
			ex.printStackTrace();
		}
	}

	public static Object deSerialize(String fileName) {
		try
		{   
			File file = new File(fileName);
			if(file.exists()) {
				FileInputStream fileOut = new FileInputStream(fileName); 
				ObjectInputStream in = new ObjectInputStream(fileOut); 
				Object obj = in.readObject(); 
				in.close(); 
				fileOut.close(); 
				return obj;
			}
			else {
				return null;
			}
		} 
		catch(Exception ex) 
		{ 
			ex.printStackTrace();
		}
		return null;
	}

}
