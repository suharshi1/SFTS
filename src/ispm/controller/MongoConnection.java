package ispm.controller;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoConnection {
	
	DB db = null;
	public MongoConnection(DB db){
		this.db = db;
	}
	//creates the mongodb conenction have to provide the db name as the parameter
	public DB createMongoConnection(String dbName)
	{
	  MongoClient mongoclient = new MongoClient("localhost",27017);
	  mongoclient.dropDatabase(dbName);
      db = mongoclient.getDB(dbName);
	  System.out.println("Mongo DB Connected Successsfully!!!!!");
	  return db;
	}
}
