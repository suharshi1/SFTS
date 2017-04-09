package ispm.controller;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoDAO {
	
	DB db = null;
	PdfReader reader = null;
	DBCollection docCollection = null;
	int pagecount = 0;
	String filepath = null;
	DocPageCounter pc = null;
	MongoConnection mg = null;
	int did = 0;//this has to be automated for every single project and should differntiate from project by project
	String projectId = null;//has to capture this from the interface
	String name_file = null;
	
	public MongoDAO(DB db,PdfReader reader,DBCollection docCollection,int pagecount,String filepath,DocPageCounter pc,MongoConnection mg,int did,String projectId, String name_file)
	{
		this.db = db;
		this.reader = reader;
		this.docCollection = docCollection;
	    this.pagecount = pagecount;
	    this.filepath = filepath;
		this.pc = pc;
		this.did = did;
		this.projectId = projectId;
		this.name_file =name_file;
	}
	
	
	public void insertDocument(String dbName,String collectionName) throws IOException
	{
		pc = new DocPageCounter(reader,filepath);
		mg = new MongoConnection(db);
		DB g = mg.createMongoConnection(dbName);
	    docCollection = g.getCollection(collectionName);
		
		 int page_count = pc.pagecount();
		 
		 
         String pcontent = null;
         DBObject project = new BasicDBObject();
		 DBObject document = new BasicDBObject();
         project.put("projectId", projectId);
         document.put("docId", did);
         ((BasicDBObject) document).append("seq",1);
         document.put("docName", name_file);
        
         for(int i=1;i<page_count+1;i++)
         {
        	 pcontent = pc.pagecontent(i);
        	 //System.out.println(pcontent);
        	 if(pcontent.length()==0)
        	 {
        		 continue;
        	 }
        	 else
        	 {
        		 
        		 
        		 BasicDBObject pages = new BasicDBObject("pageId", "page" + i).append("content",pcontent);
        		 document.put("pages", pages);
                
        	 }
        		 
         }
         project.put("document", document);
         docCollection.insert(project);
         System.out.println("Document inserted successfully!!!!!!!");
         System.out.println(projectId);
         DBObject firstDoc = docCollection.findOne();
         System.out.println(firstDoc);
         getDocument();
	}
	
	
	
	public String getDocument()
	{
		//DBObject results = docCollection.findOne("document.docId");
	     // DBObject record = null;
	      
	      DBObject query = new BasicDBObject("projectId","ABC1");
	      DBCursor cursor = docCollection.find(query);
	      String doc = (String) cursor.one().get("document.docId");
	      
	     /* while (results.hasNext()) {
	        record = results.next();
	        System.out.println(record);
	      }*/
	      System.out.println(doc);
	     return doc; 
	}
	

}
