package com.cap.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cap.management.dao.CustomerDao;
import com.cap.management.dao.OrderDao;
import com.cap.management.dao.ReviewDao;
import com.cap.management.entities.Customer;
import com.cap.management.entities.Order;
import com.cap.management.entities.Review;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao cDao;
	OrderDao oDao;
	ReviewDao rDao;
	ReviewService rService; // ReviewService

	public void setcDao(CustomerDao cDao) {
		this.cDao = cDao;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return cDao.save(customer);
	}

	@Override
	public String editCustomer(Customer newCustomer) //
	{
		Customer customer = cDao.findById(newCustomer.getEmailAddress()).get();
		if (customer != null) {
			cDao.save(newCustomer);
			return "Customer information is  Modified";
		}
		return "Update Failed";
	}

	@Override
	public List<Customer> getCustomers() {
		return cDao.findAll();
	}

	@Override
	public List<Review> getReviews() {
		return rDao.findAll();
	}

	@Override
	public ResponseEntity<String> deleteCustomer(String emailId) {

		try {
			Optional<List<Order>> order = oDao.getCustId(emailId);
			Optional<List<Review>> review = rService.getReviewByMailId(emailId);
			if (review != null || order != null) {
				return new ResponseEntity<String>("Customer Not Deleted", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>("Customer Not Deleted", HttpStatus.BAD_REQUEST);
		} catch (NullPointerException s) {
			cDao.deleteById(emailId);

			return new ResponseEntity<String>("Customer Deleted", HttpStatus.OK);
		}

	}

	@Override
	public Optional<List<Order>> getOrderByMailId(String emailId) {
		Optional<List<Order>> ord = oDao.getCustId(emailId);
		return ord;
	}

	@Override
	public Optional<List<Review>> deleteCustomerByReview(String emailId) {
		Optional<List<Review>> rev = rService.getReviewByMailId(emailId);
		return rev;

	}

}
