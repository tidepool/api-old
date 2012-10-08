package com.tidepool.api.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class Account implements Serializable, UserDetails {
	
	public static final byte[] user_name_column = Bytes.toBytes("user_name");
	public static final byte[] email_column = Bytes.toBytes("email");
	public static final byte[] password_column = Bytes.toBytes("password");
	public static final byte[] role_column = Bytes.toBytes("element_group");
	public static final byte[] first_name = Bytes.toBytes("first_name");
	public static final byte[] last_name = Bytes.toBytes("last_name");
	public static final byte[] gender_column = Bytes.toBytes("gender");
	public static final byte[] education_column = Bytes.toBytes("education");
	public static final byte[] zipcode_column = Bytes.toBytes("zipcode");
	public static final byte[] birth_year_column = Bytes.toBytes("birth_year");
	public static final byte[] element_group_id_column = Bytes.toBytes("element_group_id");
	public static final byte[] registration_level_id_column = Bytes.toBytes("registration_level_id_column");
	public static final byte[] explicit_image_folder_column = Bytes.toBytes("explicit_image_folder");
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_PRO_USER = "ROLE_PRO_USER";
	
	private static final GrantedAuthority adminAuth = new GrantedAuthorityImpl(ROLE_ADMIN);
	private static final GrantedAuthority userAuth = new GrantedAuthorityImpl(ROLE_USER);
	private static final GrantedAuthority financialUserAuth = new GrantedAuthorityImpl(ROLE_PRO_USER);
	
	private static final Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
	static {
		authorities.add(userAuth);
	}
	
	private Long userId;
	private String username;
	private String email;	
	private String password;
	private String firstName;
	private String lastName;
	private Integer role;
	private String elementGroupId;
	private int registrationLevel = 0;
	private String gender;
	private String education;
	private String birthYear;
	private String zipCode;
	private String explicitImageFolder;
	
		
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {		
		return password;
	}

	public String getUsername() {		
		return username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static GrantedAuthority getUserauth() {
		return userAuth;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getElementGroupId() {
		return elementGroupId;
	}

	public void setElementGroupId(String elementGroupId) {
		this.elementGroupId = elementGroupId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getRegistrationLevel() {
		return registrationLevel;
	}

	public void setRegistrationLevel(int registrationLevel) {
		this.registrationLevel = registrationLevel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	public boolean isAdmin() {
		return getElementGroupId().equals("0");
	}

	public String getExplicitImageFolder() {
		return explicitImageFolder;
	}

	public void setExplicitImageFolder(String explicitImageFolder) {
		this.explicitImageFolder = explicitImageFolder;
	}
	
}
