package com.tracking.utils;

import java.util.Collection;

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
	

}
