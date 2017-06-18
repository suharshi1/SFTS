package com.tracking.utils;

import com.tracking.domain.Device;

public class DeviceValidator {

	public static boolean isValidDevice(Device device){
		boolean isVaild = false;
	// TODO Suwimali add the full validation chain to this 	
	//	fName == null ||lName == null || address==null || dob==null || role==null || contactNo==null || email==null 
		
		if(device.getDeviceId() != null && device.getImei() != null  ){
		
			isVaild = true;		
		
		}
		
		return isVaild;	
		
	}	
}
