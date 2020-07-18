package com.cap.management.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cap.management.entities.Customer;
import com.cap.management.entities.Review;
import com.cap.management.service.CustomerService;
import com.cap.management.service.ReviewService;

@RestController
@RequestMapping("/CustomerReview")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerReviewController {
	@Autowired
	ReviewService reviewService;

	@Autowired
	CustomerService customerService;

	static Logger logger = LoggerFactory.getLogger(CustomerReviewController.class);

	@PutMapping(value = "/UpdateReview", produces = "application/json")
	public ResponseEntity<Review> updateReview(@Valid @RequestBody Review review) {
		logger.warn("Request {}", review);
		return reviewService.updateReview(review);
	}

	@GetMapping(value = "/getReviewByMailId/{mailId}", produces = "application/json")
	public ResponseEntity<Optional<List<Review>>> getReviewByMailId(@Valid @PathVariable String mailId) {
		Optional<List<Review>> review = reviewService.getReviewByMailId(mailId);
		logger.warn("Request {} ", mailId);
		if (review.isPresent())
			return new ResponseEntity<Optional<List<Review>>>(review, HttpStatus.OK);
		return new ResponseEntity<Optional<List<Review>>>(review, HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/getReviewByBookId/{bookId}", produces = "application/json")
	public ResponseEntity<Optional<List<Review>>> getReviewByBookId(@PathVariable int bookId) {
		Optional<List<Review>> review = reviewService.getReviewByBookId(bookId);
		logger.warn("Request {} ", bookId);
		if (review.isPresent())
			return new ResponseEntity<Optional<List<Review>>>(review, HttpStatus.OK);
		return new ResponseEntity<Optional<List<Review>>>(review, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getReviews")
	public List<Review> getReviews() {
		return reviewService.getReviews();
	}

	@DeleteMapping("/deleteReview/{reviewId}")
	public String deleteReview(@Valid @PathVariable int reviewId) {
		logger.warn("Request {} ", reviewId);
		return reviewService.deleteReview(reviewId);

		// return new ResponseEntity<String>(HttpStatus.OK);
	}

	@PostMapping(value = "/CreateReview", produces = "application/json")
	public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
		logger.warn("Request {} ", review);
		return reviewService.createReview(review);
	}

	@PostMapping(value = "/addCustomer", consumes = "application/json")
	public ResponseEntity<String> addCustomerDetails(@Valid @RequestBody() Customer customer) {
		try {
			logger.warn("Request {}", customer);
			customer.setRegisterDate(LocalDate.now());
			customerService.createCustomer(customer);
			return new ResponseEntity<String>("Customer Added", HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage() + " Customer Already Exists", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/updateCustomer", consumes = "application/json")
	public ResponseEntity<String> updateCustomer(@Valid @RequestBody() Customer customer) {
		try {
			logger.warn("Request {}", customer);
			customerService.editCustomer(customer);
			return new ResponseEntity<String>(" Customer Details updated", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage() + " Customer details cannot be updated",
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/getCustomers", produces = "application/json")
	public List<Customer> getCustomerDetails() {
		try {
			return customerService.getCustomers();

		} catch (Exception ex) {
			return null;
		}
	}

	@DeleteMapping(value = "/deleteCustomer/{emailId}", produces = "application/json")
	public ResponseEntity<String> deleteCustomer(@PathVariable String emailId)
			throws SQLIntegrityConstraintViolationException {
		return customerService.deleteCustomer(emailId);

		/*
		 * try { logger.warn("Request {}", emailId); Optional<List<Review>> review =
		 * customerService.deleteCustomerByReview(emailId); Optional<List<Order>> order
		 * = customerService.getOrderByMailId(emailId); if (review != null || order !=
		 * null) { return new ResponseEntity<String>("Customer Not Deleted",
		 * HttpStatus.BAD_REQUEST); } return new
		 * ResponseEntity<String>("Customer Not Deleted", HttpStatus.BAD_REQUEST); }
		 * catch (NullPointerException s) { customerService.deleteCustomer(emailId);
		 * 
		 * return new ResponseEntity<String>("Customer Deleted", HttpStatus.OK); }
		 */
	}

}
