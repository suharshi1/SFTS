package com.tracking.domain;

import java.io.Serializable;

public class DeviceDTO implements Serializable {
	

	private static final long serialVersionUID = -8330445378838150766L;

	private int deviceDid;
	
	private String deviceId;
	
	private String description ;
	
	private String imei;

	public DeviceDTO(){
		
	}
	
	public DeviceDTO(int deviceDid){
		this.deviceDid = deviceDid;
	}

	public DeviceDTO(int deviceDid ,String deviceId ,String imei , String description ){
		this.deviceDid = deviceDid;
		this.deviceId = deviceId;
		this.imei = imei;
		this.description = description;
	}
	
	public DeviceDTO (int deviceDid , String imei){
		this.deviceDid = deviceDid;
		this.imei = imei;
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

	@Override
	public String toString() {
		return "Device [deviceDid=" + deviceDid + ", deviceId=" + deviceId
				+ ", description=" + description + ", imei=" + imei + "]";
	}

}
