package com.sap.refapps.espm.service;

import org.springframework.dao.DataAccessException;

import com.sap.refapps.espm.model.ProductReview;

/**
 * This interface defines all the methods
 * for the product service.
 *
 */
public interface ProductReviewService {

	/**
	 * Returns the list of all product.
	 * 
	 * @return list of all product
	 * @throws DataAccessException
	 */
	Iterable<ProductReview> getProductReviewByProductId(String productId);
	
	/**
	 * @param productReview
	 * @return true if saved successfully
	 */
	boolean postReview(ProductReview productReview);
	
	void updateReviewLikes(String reveiwId);
	
	void updateReviewDislikes(String reveiwId);
	
}
