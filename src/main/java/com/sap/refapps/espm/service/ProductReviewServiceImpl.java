package com.sap.refapps.espm.service;

import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sap.refapps.espm.model.ProductReview;
import com.sap.refapps.espm.model.SentimentAnalysis;
import com.sap.refapps.espm.repository.ProductReviewRepository;

/**
 * This is the implementation class for the product service 
 *
 */
@Service
public class ProductReviewServiceImpl implements ProductReviewService {
	
	private final RestTemplate restTemplate;
	
	@Value("${sentiment.service}")
	private String sentimentServiceEndpoint;
	
	@Autowired
	private ProductReviewRepository productReviewRepository;
	
	@Autowired
	public ProductReviewServiceImpl(final ProductReviewRepository productReviewRepository, RestTemplate rest) {
		this.restTemplate = rest;
		this.productReviewRepository = productReviewRepository;
	}
	
	@Override
	public Iterable<ProductReview> getProductReviewByProductId(String productId) {
		return productReviewRepository.findReviewByProductId(productId);
	}
	@Override
	public boolean postReview(ProductReview productReview) {
		//date formatter
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		ProductReview flag;
		String reviewId = UUID.randomUUID().toString();
		String timestamp = sdf.format(new Date());
        
		productReview.setReviewId(reviewId);
		productReview.setTimestamp(timestamp);
		//get product review sentiment
		SentimentAnalysis sentimentAnalysis = this.getProductReviewSentiments(productReview.getReviewDescription());
		productReview.setRating(new BigDecimal(sentimentAnalysis.getRating()));
		productReview.setSentiments(sentimentAnalysis.getScoreText());
		
		flag = productReviewRepository.save(productReview);
		if(flag != null){
			return true;
		}
			return false;
	}
	private SentimentAnalysis getProductReviewSentiments(String reviewDescription) {
		URI url = URI.create(sentimentServiceEndpoint);
		SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
		sentimentAnalysis.setText(reviewDescription);
		SentimentAnalysis response = this.restTemplate.postForObject(url, sentimentAnalysis, SentimentAnalysis.class);
		return response;
	}
	@Override
	public void updateReviewLikes(String reviewId) {
		productReviewRepository.updateReviewLikes(reviewId);
	}
	@Override
	public void updateReviewDislikes(String reviewId) {
		productReviewRepository.updateReviewDislikes(reviewId);
	}
	
	

}
