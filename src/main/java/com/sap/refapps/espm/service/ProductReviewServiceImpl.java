package com.sap.refapps.espm.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.refapps.espm.model.ProductReview;
import com.sap.refapps.espm.repository.ProductReviewRepository;

/**
 * This is the implementation class for the product service 
 *
 */
@Service
public class ProductReviewServiceImpl implements ProductReviewService {
	
	@Autowired
	private ProductReviewRepository productReviewRepository;
	
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
		flag = productReviewRepository.save(productReview);
		if(flag != null){
			return true;
		}
			return false;
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
