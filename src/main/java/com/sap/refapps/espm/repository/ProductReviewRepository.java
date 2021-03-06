package com.sap.refapps.espm.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sap.refapps.espm.model.ProductReview;

/**
 * This is the product repository interface 
 * which is responsible for communicating with database.
 *
 */
public interface ProductReviewRepository extends CrudRepository<ProductReview, String>{

	/**
	 * Returns product based on product id.
	 * 
	 * @param productId
	 * @return product
	 */
	@Query(value = "SELECT * FROM PRODUCT_REVIEW WHERE PRODUCT_ID = ?1", nativeQuery = true)
	Iterable<ProductReview> findReviewByProductId(@Param("PRODUCT_ID") String productId);
	
	@Modifying(clearAutomatically = true)
	@Transactional(readOnly=false)
	@Query(value = "UPDATE PRODUCT_REVIEW SET LIKES = LIKES + 1 WHERE REVIEW_ID = ?1", nativeQuery = true)
	void updateReviewLikes(@Param("REVIEW_ID") String reviewId);
	
	@Modifying()
	@Transactional(readOnly=false)
	@Query(value = "UPDATE PRODUCT_REVIEW SET DISLIKES = DISLIKES + 1 WHERE REVIEW_ID = ?1", nativeQuery = true)
	void updateReviewDislikes(@Param("REVIEW_ID") String reviewId);
}
