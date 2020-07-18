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
public class ReviewTest {
	@Autowired
	ReviewService reviewService;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void testGetReviewByCustomerIdPositive() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByMailId("mohan@gmail.com");
		Assertions.assertEquals(true, review.isPresent());
	}

	@Test
	public void testGetReviewByBookIdsPositive() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByBookId(123);
		Assertions.assertEquals(true, review.isPresent());
	}

	@Test
	public void testGetReviewByCustomerIdNegative() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByMailId("sycdj@gmail.com");
		Assertions.assertEquals(false, review.isPresent());
	}

	@Test
	public void testGetReviewByBookIdsNegative() throws Exception {
		Optional<List<Review>> review = reviewService.getReviewByBookId(100);
		Assertions.assertEquals(false, review.isPresent());
	}

	@Test
	public void testGetReviewNegative() throws Exception {
		List<Review> review = reviewService.getReviews();
		Assertions.assertEquals(false, review.isEmpty());
	}

	public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
		this.testRestTemplate = testRestTemplate;
	}

	@LocalServerPort
	int localServerPort;

	@Test
	public void testGetReviewByCustomIdPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/CustomerReview/getReviewByMailId/'xyz@gmail.com'";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	public void testGetReviewByCustomIdNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviewByCustomerId/9";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	public void testGetReviewByBookIdPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviewByBookId/1";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	public void testGetReviewByBookIdNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviewByBookId/9";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	public void testGetReviewsPositive() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviews";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}

	@Test
	public void testGetReviewsNegative() throws Exception {
		String url = "http://localhost:" + localServerPort + "/getReviews";
		ResponseEntity<Review> review = testRestTemplate.getForEntity(url, Review.class);
		Assertions.assertEquals(404, review.getStatusCodeValue());
	}


	@Test
	public void testinsertReviewPositive() throws Exception {
		Review review = new Review(2, 4, "goodabcd", "abcdefgf");
		ResponseEntity<Review> r = reviewService.createReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());
	}

	@Test
	public void testupdatetReviewNull() throws Exception {
		Review review = new Review();
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());

	}

	@Test
	public void testupdateReviewUpdating() throws Exception {
		Review review = new Review(1, 4, "good", "abc");
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());
	}

	@Test
	public void testupdateReviewCreate() throws Exception {
		Review review = new Review(45, 4, "good", "abc");
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());
	}

	@Test
	public void testupdateReviewRating() throws Exception {
		Review review = new Review(1, 5, "good", "abcd");
		ResponseEntity<Review> r = reviewService.updateReview(review);
		Assertions.assertEquals(r.getBody().getReviewId(), review.getReviewId());
	}

	@Test
	public void testDelete() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8085/CustomerReview/deleteReview/2";// Enter present id 
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
		Assertions.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void testDeleteNull() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8085/CustomerReview/deleteReview/85";// Enter present id
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
		Assertions.assertEquals(500, result.getBody());
	}

	@Test
	public void testUpdateReview() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + 8085 + "/CustomerReview/UpdateReview";
		URI uri = new URI(baseUrl);
		Review review = new Review(2, 4, "good abc", "abcdefg");
		ResponseEntity<Review> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(review),
				Review.class);
		Assertions.assertEquals(200, result.getStatusCodeValue());
		Assertions.assertNotNull(result);
	}

	@Test
	public void testUpdateReviewRating() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + 8085 + "/CustomerReview/UpdateReview";
		URI uri = new URI(baseUrl);
		Review review = new Review(2, 4, "good abc", "abcdefg");
		ResponseEntity<Review> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(review),
				Review.class);
		Assertions.assertEquals(200, result.getStatusCodeValue());
		Assertions.assertNotNull(result);
	}
}

