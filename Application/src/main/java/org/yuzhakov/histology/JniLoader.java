package org.yuzhakov.histology;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JniLoader {
	public static String loadDllFromJar(String name){
		String extractedDllPath = null;
		String dllInJar = "/"+name+".dll";
		String dllInFilesystem = name+".dll";
	    try {
			File dllLibrary = new File(dllInFilesystem);
			if (!dllLibrary.exists()){
				dllLibrary.createNewFile();
				InputStream in = JniLoader.class.getResourceAsStream(dllInJar);
				FileOutputStream out = new FileOutputStream(dllLibrary);
				byte[] buffer = new byte[1024];
			    int read = -1;
			    while((read = in.read(buffer)) != -1) {
			    	out.write(buffer, 0, read);
			    }
			    in.close();
			    out.close();
			}
			extractedDllPath = dllLibrary.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return extractedDllPath;
	}
}
