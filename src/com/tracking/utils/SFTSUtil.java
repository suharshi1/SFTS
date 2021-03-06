package com.tracking.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.sql.Date;

public class SFTSUtil {
	
	
	public static boolean isEmpty(Object ob){
		if(ob == null ){
			return true;
		}
		else if(ob instanceof String ){
			if ( ob.toString().trim().equals("" )){
				return true ;
			}else{
				return false;
			}
		}
		else if(ob instanceof Collection){
			Collection colObj = (Collection) ob ;
			if(colObj.size() == 0 ){
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}		
	}
	
	
	public static boolean isNotEmpty(Object ob){
		return !isEmpty(ob);
	}
	
	
	public static Date getBirthDate(String dateStr) throws ParseException{
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_DOB);
		Date date = new Date(df.parse(dateStr).getTime());
		System.out.println("dob "+date);
		return date;
	}
	
	

}
