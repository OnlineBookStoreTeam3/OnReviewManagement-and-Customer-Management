package com.cap.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cap.management.dao.ReviewDao;
import com.cap.management.entities.Review;
@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewDao reviewDao;

	@Override
	public ResponseEntity<Review> updateReview(Review review) {
		Optional<Review> reviews = reviewDao.findById(review.getReviewId());
		if (!reviews.isPresent())
			return new ResponseEntity<Review>(review, HttpStatus.NOT_FOUND);
		else {
			review.setBook(reviews.get().getBook());
			review.setCustomer(reviews.get().getCustomer());
			review.setRating(reviews.get().getRating());
			return new ResponseEntity<Review>(reviewDao.save(review), HttpStatus.OK);
		}
	}

	@Override
	public Optional<List<Review>> getReviewByMailId(String mailId) {
		return reviewDao.getCustId(mailId);
	}

	@Override
	public Optional<List<Review>> getReviewByBookId(int bookId) {
		return reviewDao.getReviewByBookId(bookId);

	}

	@Override
	public List<Review> getReviews() {
		return reviewDao.findAll();
	}

	@Override
	public String deleteReview(int reviewId) {
		reviewDao.deleteById(reviewId);
		return "review Deleted";
	}

	@Override
	public ResponseEntity<Review> createReview(Review review) {
		Optional<Review> reviews = reviewDao.findById(review.getReviewId());
		if (reviews.isPresent()) {
			review.setRating(reviews.get().getRating());
			review.setComments(reviews.get().getComments());
			review.setHeadline(reviews.get().getHeadline());
			return new ResponseEntity<Review>(review, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Review>(reviewDao.save(review), HttpStatus.OK);
		}
	}
}
