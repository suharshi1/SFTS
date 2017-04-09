package ispm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class FileLoader {
	
	public File[] getFilestoList()
	{
	
	File folder = new File("c:/uploads");
	File[] listOfFiles = folder.listFiles();
	

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        System.out.println("File " + listOfFiles[i].getName());
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }   
     
	  return listOfFiles;
	}

}
