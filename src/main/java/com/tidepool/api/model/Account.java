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
	public static final byte[] registration_level_id_column = Bytes.toBytes("registration_level_id");
	public static final byte[] explicit_image_folder_column = Bytes.toBytes("explicit_image_folder");
	public static final byte[] account_status_column = Bytes.toBytes("account_status");
	public static final byte[] assess_code_column = Bytes.toBytes("assess_code_status");
	public static final byte[] company_column = Bytes.toBytes("company");
	public static final byte[] phone_number_column = Bytes.toBytes("phone_number");
	public static final byte[] age_column = Bytes.toBytes("age");
	public static final byte[] job_title_column = Bytes.toBytes("job_title");
		
	public static final byte[] password_reset_challenge_column = Bytes.toBytes("password_reset_challenge");
	public static final byte[] password_reset_challenge_timestamp_column = Bytes.toBytes("password_reset_challenge_timestamp");
	
	public static final byte[] extraverted_column = Bytes.toBytes("extraverted");
	public static final byte[] critical_column = Bytes.toBytes("critical");
	public static final byte[] dependable_column = Bytes.toBytes("dependable");
	public static final byte[] anxious_column = Bytes.toBytes("anxious");
	public static final byte[] open_column = Bytes.toBytes("open");
	public static final byte[] reserved_column = Bytes.toBytes("reserved");
	public static final byte[] sympathetic_column = Bytes.toBytes("sympathetic");
	public static final byte[] disorganized_column = Bytes.toBytes("disorganized");
	public static final byte[] calm_column = Bytes.toBytes("calm");
	public static final byte[] conventional_column = Bytes.toBytes("conventional");
	
	public static final byte[] artistic_appeal_column = Bytes.toBytes("artistic_appeal");
	public static final byte[] effective_user_interface_column = Bytes.toBytes("effective_user_interface");
	public static final byte[] interest_in_measurement_column = Bytes.toBytes("interest_in_measurement");
	public static final byte[] understanding_personality_column = Bytes.toBytes("understanding_personality");
	public static final byte[] interesting_dating_partners_column = Bytes.toBytes("interesting_dating_partners");
	
	public static final byte[] ip_column = Bytes.toBytes("ip");
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_PRO_USER = "ROLE_PRO_USER";
	
	public static final String FRAMEWORK_ADMIN = "FRAMEWORK_ADMIN";
	public static final String FRAMEWORK_USER = "FRAMEWORK_USER";
	
	private static final GrantedAuthority adminAuth = new GrantedAuthorityImpl(ROLE_ADMIN);
	private static final GrantedAuthority userAuth = new GrantedAuthorityImpl(ROLE_USER);
	private static final GrantedAuthority financialUserAuth = new GrantedAuthorityImpl(ROLE_PRO_USER);
	
	private static final Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
	static {
		authorities.add(userAuth);
	}
	
	private String userId;
	private String username;
	private String email;	
	private String password;
	private String firstName;
	private String lastName;
	private Integer role;
	private String elementGroupId;
	private String registrationLevel = "0";
	private String gender;
	private String education;
	private String birthYear;
	private String zipCode;
	private String explicitImageFolder;
	private String accountStatus;
	private String assessCode;
	private String company;
	private String phoneNumber;
	private String jobTitle;
	private String age;
	
	private String passwordResetChallenge;
	private long passwordResetChallengeTimestamp;
	
	
	public int extraverted;
	public int critical;
	public int dependable;
	public int anxious;
	public int open;
	public int reserved;
	public int sympathetic;
	public int disorganized;
	public int calm;
	public int conventional;
	
	public int artistic_appeal;
	public int effective_user_interface;
	public int interest_in_measurement;
	public int understanding_personality;
	public int interesting_dating_partners;
	
	public String extravertedLabel = "Extraverted, enthusiastic.";
	public String criticalLabel = "Critical, quarrelsome.";
	public String dependableLabel = "Dependable, self-disciplined.";
	public String anxiousLabel = "Anxious, easily upset.";
	public String openLabel = "Open to new experiences, complex.";
	public String reservedLabel = "Reserved, quiet.";
	public String sympatheticLabel = "Sympathetic, warm.";
	public String disorganizedLabel = "Disorganized, careless.";
	public String calmLabel = "Calm, emotionally stable.";
	public String conventionalLabel = "Conventional, uncreative.";
	
	public String ip;
	
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRegistrationLevel() {
		return registrationLevel;
	}

	public void setRegistrationLevel(String registrationLevel) {
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
		if (getElementGroupId() == null) return false; 
		return getElementGroupId().equals("0");
	}

	public boolean isFrameworkUser() {
		if (getElementGroupId() == null) return false; 
		return getElementGroupId().equals(FRAMEWORK_USER);
	}
	
	public boolean isFrameworkAdmin() {
		if (getElementGroupId() == null) return false; 
		return getElementGroupId().equals(FRAMEWORK_ADMIN);
	}
	
	public String getExplicitImageFolder() {
		return explicitImageFolder;
	}

	public void setExplicitImageFolder(String explicitImageFolder) {
		this.explicitImageFolder = explicitImageFolder;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getExtraverted() {
		return extraverted;
	}

	public void setExtraverted(int extraverted) {
		this.extraverted = extraverted;
	}

	public int getCritical() {
		return critical;
	}

	public void setCritical(int critical) {
		this.critical = critical;
	}

	public int getDependable() {
		return dependable;
	}

	public void setDependable(int dependable) {
		this.dependable = dependable;
	}

	public int getAnxious() {
		return anxious;
	}

	public void setAnxious(int anxious) {
		this.anxious = anxious;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public int getSympathetic() {
		return sympathetic;
	}

	public void setSympathetic(int sympathetic) {
		this.sympathetic = sympathetic;
	}

	public int getDisorganized() {
		return disorganized;
	}

	public void setDisorganized(int disorganized) {
		this.disorganized = disorganized;
	}

	public int getCalm() {
		return calm;
	}

	public void setCalm(int calm) {
		this.calm = calm;
	}

	public int getConventional() {
		return conventional;
	}

	public void setConventional(int conventional) {
		this.conventional = conventional;
	}

	public String getExtravertedLabel() {
		return extravertedLabel;
	}

	public void setExtravertedLabel(String extravertedLabel) {
		this.extravertedLabel = extravertedLabel;
	}

	public String getCriticalLabel() {
		return criticalLabel;
	}

	public void setCriticalLabel(String criticalLabel) {
		this.criticalLabel = criticalLabel;
	}

	public String getDependableLabel() {
		return dependableLabel;
	}

	public void setDependableLabel(String dependableLabel) {
		this.dependableLabel = dependableLabel;
	}

	public String getAnxiousLabel() {
		return anxiousLabel;
	}

	public void setAnxiousLabel(String anxiousLabel) {
		this.anxiousLabel = anxiousLabel;
	}

	public String getOpenLabel() {
		return openLabel;
	}

	public void setOpenLabel(String openLabel) {
		this.openLabel = openLabel;
	}

	public String getReservedLabel() {
		return reservedLabel;
	}

	public void setReservedLabel(String reservedLabel) {
		this.reservedLabel = reservedLabel;
	}

	public String getSympatheticLabel() {
		return sympatheticLabel;
	}

	public void setSympatheticLabel(String sympatheticLabel) {
		this.sympatheticLabel = sympatheticLabel;
	}

	public String getDisorganizedLabel() {
		return disorganizedLabel;
	}

	public void setDisorganizedLabel(String disorganizedLabel) {
		this.disorganizedLabel = disorganizedLabel;
	}

	public String getCalmLabel() {
		return calmLabel;
	}

	public void setCalmLabel(String calmLabel) {
		this.calmLabel = calmLabel;
	}

	public String getConventionalLabel() {
		return conventionalLabel;
	}

	public void setConventionalLabel(String conventionalLabel) {
		this.conventionalLabel = conventionalLabel;
	}

	public int getArtistic_appeal() {
		return artistic_appeal;
	}

	public void setArtistic_appeal(int artistic_appeal) {
		this.artistic_appeal = artistic_appeal;
	}

	public int getEffective_user_interface() {
		return effective_user_interface;
	}

	public void setEffective_user_interface(int effective_user_interface) {
		this.effective_user_interface = effective_user_interface;
	}

	public int getInterest_in_measurement() {
		return interest_in_measurement;
	}

	public void setInterest_in_measurement(int interest_in_measurement) {
		this.interest_in_measurement = interest_in_measurement;
	}

	public int getUnderstanding_personality() {
		return understanding_personality;
	}

	public void setUnderstanding_personality(int understanding_personality) {
		this.understanding_personality = understanding_personality;
	}

	public int getInteresting_dating_partners() {
		return interesting_dating_partners;
	}

	public void setInteresting_dating_partners(int interesting_dating_partners) {
		this.interesting_dating_partners = interesting_dating_partners;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAssessCode() {
		return assessCode;
	}

	public void setAssessCode(String assessCode) {
		this.assessCode = assessCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPasswordResetChallenge() {
		return passwordResetChallenge;
	}

	public void setPasswordResetChallenge(String passwordResetChallenge) {
		this.passwordResetChallenge = passwordResetChallenge;
	}

	public long getPasswordResetChallengeTimestamp() {
		return passwordResetChallengeTimestamp;
	}

	public void setPasswordResetChallengeTimestamp(
			long passwordResetChallengeTimestamp) {
		this.passwordResetChallengeTimestamp = passwordResetChallengeTimestamp;
	}
	
}
