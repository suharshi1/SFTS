package ispm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class IdentifyReq {
	
	public IdentifyReq()
	{
		
	}
	
	public void createreqFile(String reqfilepath, int pageno) throws IOException
	{
		    
	        PdfReader rd = new PdfReader(reqfilepath);
	        String str = PdfTextExtractor.getTextFromPage(rd, pageno);
	        String search_sentence = null;
	        String req = "requirements";
	       // String final_requirements = null;
	         PrintWriter writer = new PrintWriter("c:/uploads/req.txt", "UTF-8");
	        ArrayList<String> data = new ArrayList<>();
	       // ArrayList<String> reqdata = new ArrayList<>();
	        int count = 0;
	        int res = 1;
	        
	        
	       
	        
	        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
	        Matcher reMatcher = re.matcher(str);
	      
	        while (reMatcher.find()) 
	        {
	       	 search_sentence = reMatcher.group();
	            System.out.println(reMatcher.group());
	            
	            if ( search_sentence.toLowerCase().indexOf(req.toLowerCase()) != -1 )
	            {

	           	   System.out.println("I found the keyword");
	           	   String[] words = search_sentence.split("\\s+");
	           	   for (int i = 0; i < words.length; i++) 
	           	   {
	           	    // You may want to check for a non-word character before blindly
	           	    // performing a replacement
	           	    // It may also be necessary to adjust the character class
	           	    words[i] = words[i].replaceAll("[^\\w]", "");
	           	    int length = words.length;
	           	    //System.out.println(words[i]);
	           	    
	           	    if(words[i].equals(req.toLowerCase()) && i==length-1)
	           	      {
	           	    	System.out.println("Topic!!!!!!!!!!!!!!");
	           	    	count = 1;
	           	    	break;
	           	      }
	           	      else
	           	      {
	           	    	continue;
	           	      }
	               }

	           	}
	            else
	            {

	           	   System.out.println("not found");

	           	}
	            
	            if(count==1)
	            {
	            	data.add(search_sentence);
	            }
	            	
	        }
	       
	        System.out.println("After req identified/////////////////////////");
	        for(String x : data)
	        {
	        	if(res!=1){
	            writer.println(x);
	        	//reqdata.add(x);
	        	System.out.println(x);
	        	}
	        	res++;
	        	
	        }
	        writer.close();
	        
	}

}
