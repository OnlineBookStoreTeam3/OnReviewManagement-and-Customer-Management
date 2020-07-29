package com.cap.management;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cap.management.entities.Review;
import com.cap.management.service.ReviewService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReviewTest {
	@Autowired
	ReviewService reviewService;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	void testGetReviewByCustomerIdPositive() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByMailId("cdr@gmail.com");
		Assertions.assertEquals(true, review.isPresent());
	}

	@Test
	void testGetReviewByBookIdsPositive() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByBookId(123);
		Assertions.assertEquals(false, review.isPresent());
	}

	@Test
	 void testGetReviewByCustomerIdNegative() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByMailId("sycdj@gmail.com");
		Assertions.assertEquals(false, review.isPresent());
	}

	@Test
	void testGetReviewByBookIdsNegative() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByBookId(100);
		Assertions.assertEquals(false, review.isPresent());
	}

	@Test
	void testGetReviewNegative() throws Exception {
		List<Review> review = reviewService.getReviews();
		Assertions.assertEquals(false, review.isEmpty());
	}

	 void setTestRestTemplate(TestRestTemplate testRestTemplate) {
		this.testRestTemplate = testRestTemplate;
	}

	@LocalServerPort
	int localServerPort;

	@Test
	 void testGetReviewByCustomIdPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/getReviewByMailId/'xyz@gmail.com'";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	 void testGetReviewByCustomIdNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviewByCustomerId/9";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	 void testGetReviewByBookIdPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviewByBookId/1";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	void testGetReviewByBookIdNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviewByBookId/9";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	 void testGetReviewsPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviews";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	 void testGetReviewsNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviews";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}



	@Test
	 void testupdatetReviewNull() throws Exception {
		Review review = new Review();
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());

	}

	@Test
	 void testupdateReviewUpdating() throws Exception {
		Review review = new Review(1, 4, "good", "abc");
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());
	}

	@Test
	 void testupdateReviewCreate() throws Exception {
		Review review = new Review(45, 4, "good", "abc");
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());
	}

	@Test
	 void testupdateReviewRating() throws Exception {
		Review review = new Review(1, 5, "good", "abcd");
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());
	}


	@Test
	 void testUpdateReview() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + 8085 + "/CustomerReview/UpdateReview";
		URI uri = new URI(baseUrl);
		Review review = new Review(15, 4, "Well Written Story", "Great characters and well narrated");
		ResponseEntity<Review> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(review),
				Review.class);
		Assertions.assertEquals(200, result.getStatusCodeValue());
		Assertions.assertNotNull(result);
	}

	@Test
	 void testUpdateReviewRating() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + 8085 + "/CustomerReview/UpdateReview";
		URI uri = new URI(baseUrl);
		Review review = new Review(15, 4, "Well Written Story", "Great characters and well narrated");
		ResponseEntity<Review> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(review),
				Review.class);
		Assertions.assertEquals(200, result.getStatusCodeValue());
		Assertions.assertNotNull(result);
	}
}

