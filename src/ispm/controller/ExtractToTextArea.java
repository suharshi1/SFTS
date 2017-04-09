package ispm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

public class ExtractToTextArea {
	
	public ExtractToTextArea()
	{
		
	}
	
    public String DirToTextArea() throws IOException
    {
//    	String everything = null;
//    	FileInputStream inputStream = new FileInputStream("c:/uploads/req.txt");
//    	try {
//    	      everything = IOUtils.toString(inputStream);
//    	} finally {
//    	    inputStream.close();
//    	}
//    	return everything;
    	String r;
    	File krokiPath = new File("c:/Research/docname1.txt");
    	BufferedReader in = new BufferedReader(
    	        new InputStreamReader(new FileInputStream(krokiPath), "UTF8"));

    	while((r = in.readLine()) != null) {
    	    System.out.println(r);
    	}
    	in.close();
    	return r;
    }
}
