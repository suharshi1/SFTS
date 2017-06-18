package net.hibernate.user;

import java.sql.Date;
import java.lang.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class UserManage {
	
	 protected SessionFactory sessionFactory;
	 
	    public void setup() {
	      
	    	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
	    	        .configure() // configures settings from hibernate.cfg.xml
	    	        .build();
	    	try {
	    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	    	} catch (Exception ex) {
	    	    StandardServiceRegistryBuilder.destroy(registry);
	    	    
	    	    Session session = sessionFactory.openSession();
	    	    session.beginTransaction();
	    	    
	    	    session.getTransaction().commit();
	    	    session.close();
	    	}
	    }
	 
	    protected void exit() {
	    	
	    	sessionFactory.close();
	    }
	 
	    protected void create() throws ParseException {
	      
	    	User us =  new User();
	    	us.setUserDid(104);
	    	us.setUserId("1004");
	    	us.setPassword("Rai");
	    	us.setfName("Rayan");
	    	us.setlName("Fernando");
	    	us.setAddress1("Ediriweera Mawatha");
	    	us.setAddress2("Dehiwela");
	    	us.setCity("Dehiwela");
	    	us.setUserrole_roledid(2);
	    	
	    	
	   /* 	SimpleDateFormat sm = new SimpleDateFormat("yyyy/M/dd");
	    	java.sql.Date bDate = (Date) sm.parse("1991/4/8");
	    	
	    	us.setDateofbirth((Date)bDate);*/
	    	us.setContactNumber("0119870159");
	    	us.setEmail("rai@gmail.com");
	    	
	    	Session session = sessionFactory.openSession();
	    	session.beginTransaction();
	    	
	    	session.save(us);
	    	
	    	session.getTransaction().commit();
	    	session.close();
	 
	    }
	 
	    protected void read() {
	        // code to get a book
	    }
	 
	    protected void update() {
	        // code to modify a book
	    }
	 
	    protected void delete() {
	        // code to remove a book
	    }

	public static void main(String[] args) throws ParseException {
	
		 UserManage manager = new UserManage();
		    manager.setup();
		    manager.create();
		    manager.exit();

	}

}
