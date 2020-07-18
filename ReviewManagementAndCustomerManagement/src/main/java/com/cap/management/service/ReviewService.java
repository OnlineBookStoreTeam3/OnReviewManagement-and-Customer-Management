package com.cap.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.cap.management.entities.Review;

public interface ReviewService {

	ResponseEntity<Review> updateReview(Review review);

	Optional<List<Review>> getReviewByMailId(String mailId);

	Optional<List<Review>> getReviewByBookId(int bookId);

	List<Review> getReviews();

	String deleteReview(int reviewId);

	ResponseEntity<Review> createReview(Review review);
}
