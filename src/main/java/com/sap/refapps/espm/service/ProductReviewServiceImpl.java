package com.sap.refapps.espm.service;

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
		ProductReview flag;
		String reviewId = UUID.randomUUID().toString();
        productReview.setReviewId(reviewId);
		flag = productReviewRepository.save(productReview);
		if(flag != null){
			return true;
		}
			return false;
	}
	
	

}
