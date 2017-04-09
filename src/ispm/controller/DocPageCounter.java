package ispm.controller;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class DocPageCounter {
	PdfReader reader = null;
	String file = null;

	public DocPageCounter(PdfReader reader,String file)
	{
		this.reader = reader;
		this.file = file;
	}
		// TODO Auto-generated method stub
	
	public int pagecount() throws IOException//counts the no.of pages in a document
	{
	    
		/*RandomAccessFile raf = new RandomAccessFile(file, "r");
	     RandomAccessFileOrArray pdfFile = new RandomAccessFileOrArray(
	          new RandomAccessSourceFactory().createSource(raf));*/
	     //PdfReader reader = new PdfReader(pdfFile, new byte[0]);
	     reader = new PdfReader(file);
	     int pages = reader.getNumberOfPages();
	     
	     //String page = PdfTextExtractor.getTextFromPage(reader, 5);
         //System.out.println("Page Content:\n\n"+page+"\n\n");

	     
	     System.out.println(pages);
	     return pages;

	}
	
	public String pagecontent(int pg) throws IOException//returns the content of a page as a string
	{
		
		String pgi = PdfTextExtractor.getTextFromPage(reader, pg);
		
		return pgi;
	}
}
