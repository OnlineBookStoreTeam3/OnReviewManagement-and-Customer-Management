package com.cap.management.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cap.management.dao.CustomerDao;
import com.cap.management.dao.OrderDao;
import com.cap.management.dao.ReviewDao;
import com.cap.management.entities.Customer;
import com.cap.management.entities.Order;
import com.cap.management.entities.Review;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao customerDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	ReviewDao reviewDao;
	ReviewService reviewService; // ReviewService

	private Random rand = new Random();

	public void setcDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/*
	 * It updates the customer based on the mail id
	 * 
	 * If customer is not present it returns update failed
	 * 
	 * It updates all customer details passed
	 */
	@Override
	public String editCustomer(Customer newCustomer) //
	{
		Customer customer = customerDao.findById(newCustomer.getEmailAddress()).get();
		if (customer != null) {
			customerDao.save(newCustomer);
			return "Customer information is  Modified";
		}
		return "Update Failed";
	}

	/*
	 * It fetches all the Customers present
	 * 
	 * If there is no customer at all it will return empty
	 */
	@Override
	public List<Customer> getCustomers() {
		return customerDao.findAll();
	}

	/*
	 * It fetches all the reviews present
	 * 
	 * If there is no review at all it will return empty
	 */
	@Override
	public List<Review> getReviews() {
		return reviewDao.findAll();
	}

	/*
	 * It creates the customer based on the mail id
	 * 
	 * If there is no customer present on that mail id it will create a new customer
	 * 
	 * If there is already a customer present on that mail id then customer is not
	 * created
	 */
	@Override
	public Customer createCustomer(Customer customer) {
		customer.setCustomerId(rand.nextInt(100));
		return customerDao.save(customer);
	}

	/*
	 * It will delete customer based on the mail id
	 * 
	 * It will return false if there is no customer based on the mail id
	 */
	@Override
	public Boolean deleteCustomer(String emailId) {

		Optional<List<Order>> order = orderDao.getOrderByMailId(emailId);

		Optional<List<Review>> review = reviewDao.getCustId(emailId);
		if (!(review.isPresent() || order.isPresent())) {

			Optional<Customer> customer = customerDao.findById(emailId);
			if (customer.isPresent()) {
				customerDao.deleteById(emailId);
				return true;
			} else
				return false;
		} else
			return false;
	}

	/*
	 * It fetches all the orders based on the mail id
	 * 
	 * If there are no orders made by mail id passed it will return empty
	 */
	@Override
	public Optional<List<Order>> getOrderByMailId(String emailId) {
		return orderDao.getCustId(emailId);
	}

	/*
	 * It will fetches review based on the mail id
	 * 
	 * It will return empty if there is no review based on the mail id
	 */
	@Override
	public Optional<List<Review>> deleteCustomerByReview(String emailId) {
		return reviewService.getReviewByMailId(emailId);

	}

	/*
	 * It will fetch customer based on the mail id
	 * 
	 * It will return empty if there is no customer based on the mail id
	 */
	@Transactional(readOnly = true)
	public Optional<Customer> getCustomerByMailId(String emailId) {
		return customerDao.findById(emailId);

	}

}
