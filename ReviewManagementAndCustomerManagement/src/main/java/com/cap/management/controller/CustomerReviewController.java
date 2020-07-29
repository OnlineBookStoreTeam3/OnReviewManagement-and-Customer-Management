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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cap.management.entities.Customer;
import com.cap.management.entities.Review;
import com.cap.management.exception.ExceptionResponse;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerReviewController.class);

	/*
	 * Maps the put request for updating a Review to this controller method
	 * 
	 * Review object is passed for updating an already present review
	 */
	@PutMapping(value = "/UpdateReview", produces = "application/json")
	public ResponseEntity<Review> updateReview(@Valid @RequestBody Review review) {
		if(review.getReviewId()==0) {
			throw new ExceptionResponse("Null values are being passed");
		}
		return reviewService.updateReview(review); 
	}

	/*
	 * Maps the get review by mail id url to this controller method
	 * 
	 * mail id as path variable is passed and return is a list of review given through the mail id
	 */
	@GetMapping(value = "/getReviewByMailId/{mailId}", produces = "application/json")
	public ResponseEntity<Optional<List<Review>>> getReviewByMailId(@Valid @PathVariable String mailId) {
		if(mailId==null) {
			throw new ExceptionResponse("Null values passed");
		}
		Optional<List<Review>> review = reviewService.getReviewByMailId(mailId);
		LOGGER.warn("Request {} _", mailId);
		if (review.isPresent())
			return new ResponseEntity<>(review, HttpStatus.OK);
		return new ResponseEntity<>(review, HttpStatus.NOT_FOUND);
	}

	/*
	 * Maps the get review by book id url to this controller method 
	 * 
	 * book id as path variable is passed and return is a list of review given through the book id
	 */	@GetMapping(value = "/getReviewByBookId/{bookId}", produces = "application/json")
	public ResponseEntity<Optional<List<Review>>> getReviewByBookId(@PathVariable int bookId) {
		 if(bookId==0) {
				throw new ExceptionResponse("Null values passed");
			}
		Optional<List<Review>> review = reviewService.getReviewByBookId(bookId);
		LOGGER.warn("Request {} _", bookId);
		if (review.isPresent())
			return new ResponseEntity<>(review, HttpStatus.OK);
		return new ResponseEntity<>(review, HttpStatus.NOT_FOUND);
	}

	/*
	 * Maps the get all reviews to this controller method 
	 * 
	 * It returns all the reviews present
	 */
	@GetMapping("/getReviews")
	public List<Review> getReviews() {
		return reviewService.getReviews();
	}

	/*
	 * Maps the delete request for deleting a review by its review id 
	 * 
	 * reviewId is passed to it and if review is present it is deleted
	 */
	@DeleteMapping("/deleteReview/{reviewId}")
	public ResponseEntity<String> deleteReview(@Valid @PathVariable int reviewId) {
		if(reviewId==0) {
			throw new ExceptionResponse("Null values passed");
		}
		LOGGER.warn("Request {} ", reviewId);
		return new ResponseEntity<>(reviewService.deleteReview(reviewId), HttpStatus.OK);
	}
	
	/*
	* Maps the post request for creating a review to this controller method
	* 
	* Review object is passed for creating a new review
	*/
	@PostMapping(value = "/CreateReview", produces = "application/json")
	public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
		if(review.getReviewId()==0) {
			throw new ExceptionResponse("Null values are being passed");
		}
		LOGGER.warn("Request {} ", review);
		return reviewService.createReview(review);
	}

	/*
	 * Maps the post request for creating a customer to this controller method
	 * 
	 * Customer object is passed for creating a new customer
	 */
	@PostMapping(value = "/addCustomer", consumes = "application/json")
	public ResponseEntity<String> addCustomerDetails(@Valid @RequestBody() Customer customer) {
		try {
			LOGGER.warn("Request {}", customer);
			customer.setRegisterDate(LocalDate.now());
			customerService.createCustomer(customer);
			return new ResponseEntity<>("Customer Added", HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage() + " Customer Already Exists", HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * Maps the put request for updating the customer to this controller method
	 * 
	 * Customer object is passed for updating an already present customer
	 */
	@PutMapping(value = "/updateCustomer", consumes = "application/json")
	public ResponseEntity<String> updateCustomer(@Valid @RequestBody() Customer customer) {
		try {
			LOGGER.warn("Request {}", customer);
			customerService.editCustomer(customer);
			return new ResponseEntity<>(" Customer Details updated", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage() + " Customer details cannot be updated",
					HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * Maps the get request for fetching all customer details to this method
	 * 
	 * It fetches all customers present
	 */
	@GetMapping(value = "/getCustomers", produces = "application/json")
	public List<Customer> getCustomerDetails() {
		return customerService.getCustomers();
	}

	/*
	 * Maps the delete request for deleting a customer to this method
	 *  
	 * Mail id is passed to it for delete the customer details which is present
	 */
	@ExceptionHandler({ SQLIntegrityConstraintViolationException.class })
	@DeleteMapping(value = "/deleteCustomer/{emailId}", produces = "application/json")
	public ResponseEntity<Boolean> deleteCustomer(@PathVariable String emailId) {
		if(emailId==null) {
			throw new ExceptionResponse("Null values passed");
		}
		if (!Boolean.TRUE.equals(customerService.deleteCustomer(emailId)))
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(true, HttpStatus.OK);

	}

	/*
	 * Maps the get request for fetching customer details based on mail id to this method
	 * 
	 * It fetches customers details based on the email id passed
	 */
	@GetMapping(value = "/getCustomer/{emailId}", produces = "application/json")
	public ResponseEntity<Optional<Customer>> getCustomerDetails(@PathVariable String emailId) {
		if(emailId==null) {
			throw new ExceptionResponse("Null values passed");
		}
		Optional<Customer> customer = customerService.getCustomerByMailId(emailId);
		if (customer.isPresent())
			return new ResponseEntity<>(customer, HttpStatus.OK);
		return new ResponseEntity<>(customer, HttpStatus.NOT_FOUND);
	}

}
