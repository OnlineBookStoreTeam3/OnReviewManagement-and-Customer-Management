package com.cap.management.service;

import java.util.List;
import java.util.Optional;

import com.cap.management.entities.Customer;
import com.cap.management.entities.Order;
import com.cap.management.entities.Review;

public interface CustomerService {

	public Customer createCustomer(Customer customer);

	public String editCustomer(Customer newCustomer);

	public List<Customer> getCustomers();

	public Boolean deleteCustomer(String emailId);

	public List<Review> getReviews();

	public Optional<List<Order>> getOrderByMailId(String emailId);

	public Optional<List<Review>> deleteCustomerByReview(String emailId);
	Optional<Customer> getCustomerByMailId(String emailId);

}
