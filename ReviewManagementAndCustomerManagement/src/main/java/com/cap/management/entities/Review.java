package com.cap.management.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int reviewId;
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private BookInformation book;
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "mailId")
	private Customer customer;
	@Column(name = "rating")
	@Min(1)
	@Max(5)
	private int rating;
	@Column(name = "headline")
	@Size(min = 5, max = 128)
	private String headline;
	@Column(name = "comments")
	@Size(min = 5, max = 300)
	@NotBlank(message = "Comment is mandatory")
	private String comments;

	public Review() {
	}

	public Review(int reviewId, BookInformation book, Customer customer, int rating, String headline, String comments) {
		this.reviewId = reviewId;
		this.book = book;
		this.customer = customer;
		this.rating = rating;
		this.headline = headline;
		this.comments = comments;
	}

	public Review(int reviewId, int rating, String headline, String comments) {
		super();
		this.reviewId = reviewId;
		this.rating = rating;
		this.headline = headline;
		this.comments = comments;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public BookInformation getBook() {
		return book;
	}

	public void setBook(BookInformation book) {
		this.book = book;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", book=" + book + ", customer=" + customer + ", rating=" + rating
				+ ", headline=" + headline + ", comments=" + comments + "]";
	}
}
