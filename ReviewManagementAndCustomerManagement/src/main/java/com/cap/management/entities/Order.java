package com.cap.management.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OrderInfo")
public class Order {

	@Id
	@Column(length = 15)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private String orderId;

	@Column(length = 30)
	private String receipentName;
	@Column(length = 15)
	private Long receipentPhone;
	@Column(length = 128)
	private String streetAddress;
	@Column(length = 30)
	private String city;
	@Column(length = 24)
	private String zipCode;
	@Column(length = 24)
	private String country;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emailaddress")
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReceipentName() {
		return receipentName;
	}

	public void setReceipentName(String receipentName) {
		this.receipentName = receipentName;
	}

	public Long getReceipentPhone() {
		return receipentPhone;
	}

	public void setReceipentPhone(Long receipentPhone) {
		this.receipentPhone = receipentPhone;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", receipentName=" + receipentName + ", receipentPhone=" + receipentPhone
				+ ", streetAddress=" + streetAddress + ", city=" + city + ", zipCode=" + zipCode + ", country="
				+ country + ", customer=" + customer + "]";
	}
	
}
