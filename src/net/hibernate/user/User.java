package net.hibernate.user;



import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {


	private int userDid ;
	private String userId;
	private String password;
	private String fName ;
	private String lName ;
	private String address1 ;
	private String address2;
	private String street;
	private String city;
	private int userrole_roledid;
	private Date dateofbirth;
	private String contactNumber;
	private String email;
	
	@Id
	@Column(name="userdid")
	public int getUserDid() {
		return userDid;
	}
	public void setUserDid(int userDid) {
		this.userDid = userDid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
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
	public int getUserrole_roledid() {
		return userrole_roledid;
	}
	public void setUserrole_roledid(int userrole_roledid) {
		this.userrole_roledid = userrole_roledid;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbDate) {
		this.dateofbirth = dateofbDate;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
