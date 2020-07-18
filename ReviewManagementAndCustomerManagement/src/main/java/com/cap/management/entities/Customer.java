package com.cap.management.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "customer")
@SequenceGenerator(name = "customer_id_gen", initialValue = 10000, allocationSize = 1)
public class Customer {

	@GeneratedValue(generator = "customer_id_gen", strategy = GenerationType.SEQUENCE)
	@Column(name = "customer_id")
	private int customerId;
	@Column(name = "fullname")
	private String fullName;
	@Id
	@Column(name = "emailaddress")
	@NotBlank(message = "Email Address Should be Entered")
	private String emailAddress;
	@Column(name = "password")
	@NotBlank(message = "Enter Password")
	private String password;
	@Column(name = "phone_No", length = 10)
	private Long phoneNumber;
	@Column(name = "address")
	@NotBlank(message = "Enter Address")
	private String address;
	@Column(name = "city")
	@NotBlank(message = "Enter City Name")
	private String city;
	@Column(name = "country")
	@NotBlank(message = "Enter Country Name")
	private String country;
	@Column(name = "zipcode", length = 6)
	private Long zipCode;
	@Column(name = "registered_date")
	private LocalDate registerDate;

	public Customer() {

	}

	public Customer(int customerId, String fullName, String emailAddress, String password, Long phoneNumber,
			String address, String city, String country, Long zipCode, LocalDate registerDate) {
		super();
		this.customerId = customerId;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.city = city;
		this.country = country;
		this.zipCode = zipCode;
		this.registerDate = registerDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getZipCode() {
		return zipCode;
	}

	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", fullName=" + fullName + ", emailAddress=" + emailAddress
				+ ", password=" + password + ", phoneNumber=" + phoneNumber + ", address=" + address + ", city=" + city
				+ ", country=" + country + ", zipCode=" + zipCode + ", registerDate=" + registerDate + "]";
	}

}
