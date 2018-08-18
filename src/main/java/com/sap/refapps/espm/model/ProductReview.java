package com.sap.refapps.espm.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This is the Product entity class 
 * which defines the data model for product.
 *
 */
@Entity
@Table(name = "PRODUCT_REVIEW")
public class ProductReview {
	
	@Id
	@Column(length = 100, name = "REVIEW_ID", unique = true)
	private String reviewId;

	@Column(length = 10, name = "PRODUCT_ID", unique = false)
	private String productId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Column(length = 500, name = "REVIEW_DESCRIPTION", nullable = false)
	private String reviewDescription;
	
	@Column(name = "RATING", precision = 13, scale = 4)
	private BigDecimal rating;
	
	@Column(length = 50, name = "SENTIMENTS")
	private String sentiments;
	
	@Column(name = "LIKES")
	private int likes;
	
	@Column(name = "DISLIKES")
	private int dislikes;
	
	@Column(name = "TIMESTAMP")
	private String timestamp;

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getReviewDescription() {
		return reviewDescription;
	}

	public void setReviewDescription(String reviewDescription) {
		this.reviewDescription = reviewDescription;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getSentiments() {
		return sentiments;
	}

	public void setSentiments(String sentiments) {
		this.sentiments = sentiments;
	}	
}
