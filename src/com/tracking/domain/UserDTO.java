package com.tracking.domain;

import java.io.Serializable;
import java.sql.Date;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 6025244324911937335L;

	private int userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    private String country;
    private String userImage;
    private String address1 ;
    private String dateOfBirth;
    private Date dob;
    private String contactNumber;
    private String email;
    private String address2;
    private String street;
    private String city;
    private String zipCode;
    private int role;
    
    private DeviceDTO userDevice;
    
    
    
    public UserDTO(){
    	
    }
    
    public UserDTO(String userName , String password){
    	this.userName = userName;
    	this.password = password;
    }
    
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}	
	
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public DeviceDTO getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(DeviceDTO userDevice) {
		this.userDevice = userDevice;
	}	
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public int getDeviceDid() {
		if (this.userDevice != null) {
			return this.userDevice.getDeviceDid();
		}
		else return -1;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", birthDate=" + birthDate
				+ ", gender=" + gender + ", country=" + country
				+ ", userImage=" + userImage + ", address1=" + address1
				+ ", dateOfBirth=" + dateOfBirth + ", contactNumber="
				+ contactNumber + ", email=" + email + ", address2=" + address2
				+ ", street=" + street + ", city=" + city + ", zipCode="
				+ zipCode + "]";
	}



	
}
