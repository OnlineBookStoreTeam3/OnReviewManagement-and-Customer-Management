package com.cap.management.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "book")
public class BookInformation {
	@Id
	@Column(length = 20,name = "bookId")
	@Min(1)@Max(200)
	private int bookId;
	@Column(name = "category")
	private String category;
	@Column(length = 128,name = "title")
	private String title;
	@Column(length = 64,name = "author")
	private String author;
	@Column(length = 15,name = "isbnNo")
	private String isbnNo;
	@Column(name = "price")
	private float price;
	
	public BookInformation() {	}

	public BookInformation(int bookId, String category, String title, String author, String isbnNo, float price)
	{
		this.bookId = bookId;
		this.category = category;
		this.title = title;
		this.author = author;
		this.isbnNo = isbnNo;
		this.price = price;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbnNo() {
		return isbnNo;
	}

	public void setIsbnNo(String isbnNo) {
		this.isbnNo = isbnNo;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "BookInformation [bookId=" + bookId + ", category=" + category + ", title=" + title + ", author=" + author
				+ ", isbnNo=" + isbnNo + ", price=" + price + "]";
	}
	
	
}
