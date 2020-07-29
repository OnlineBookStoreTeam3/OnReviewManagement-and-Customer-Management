package com.cap.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cap.management.dao.CustomerDao;
import com.cap.management.dao.ReviewDao;
import com.cap.management.entities.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewDao reviewDao;
	
	@Autowired
	CustomerDao customerDao;

	/*
	 * It updates the review based on the review id
	 * 
	 * If review is not present it returns not found status
	 * 
	 * It updates only Headline and comments(Rating will not be updated)
	 */
	@Override
	public ResponseEntity<Review> updateReview(Review review) {
		Optional<Review> reviews = reviewDao.findById(review.getReviewId());
		if (!reviews.isPresent())
			return new ResponseEntity<>(review, HttpStatus.NOT_FOUND);
		else {
			review.setBook(reviews.get().getBook());
			review.setCustomer(reviews.get().getCustomer());
			review.setRating(reviews.get().getRating());
			return new ResponseEntity<>(reviewDao.save(review), HttpStatus.OK);
		}
	}

	
	/*
	 * It fetches all the reviews based on the mail id
	 * 
	 * If there are no reviews made by mail id passed it will return empty
	 */
	@Override
	public Optional<List<Review>> getReviewByMailId(String mailId) {
		return reviewDao.getCustId(mailId);
	}

	/*
	 * It fetches all the reviews based on the book id
	 * 
	 * If there are no reviews made on book of that book id passed it will return
	 * empty
	 */
	@Override
	public Optional<List<Review>> getReviewByBookId(int bookId) {
		return reviewDao.getReviewByBookId(bookId);

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
	 * It will delete review based on the review id
	 * 
	 * It will return not found if there is no review based on the review id
	 */
	@Override
	public String deleteReview(int reviewId) {
		Optional<Review> review=reviewDao.findById(reviewId);
		reviewDao.deleteById(reviewId);
		customerDao.save(review.get().getCustomer());
		return "review Deleted";
	}

	/*
	 * It creates the review based on the review id
	 * 
	 * If it is the first review by the mail id on that book then it creates review
	 * 
	 * If there is already a review given by that mail id on that book it returns
	 * found status
	 */
	@Override
	public ResponseEntity<Review> createReview(Review review) {
		Optional<Review> orderReview = reviewDao.getReviewsByBookId(review.getBook().getBookId());
		Optional<Review> customerReview = reviewDao.getReviewsByCustomerId(review.getCustomer().getEmailAddress());
		if (orderReview.equals(customerReview) && orderReview.isPresent()) {
			review.setRating(orderReview.get().getRating());
			review.setComments(orderReview.get().getComments());
			review.setHeadline(orderReview.get().getHeadline());
			return new ResponseEntity<>(review, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(reviewDao.save(review), HttpStatus.OK);
		}
	}
}
