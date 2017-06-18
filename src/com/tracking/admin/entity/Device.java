package com.tracking.admin.entity;

import java.io.Serializable;

public class Device implements Serializable{
	
	
	private static final long serialVersionUID = 1057620001749268855L;
	
	private int deviceDid ;
	
	private String deviceId;
	
	private String description ;
	
	private String imei;
	
	
	public Device(){
		
	}
	
	public Device(int deviceDid){
		this.deviceDid = deviceDid;
	}
	
	
	public int getDeviceDid() {
		return deviceDid;
	}

	public void setDeviceDid(int deviceDid) {
		this.deviceDid = deviceDid;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
