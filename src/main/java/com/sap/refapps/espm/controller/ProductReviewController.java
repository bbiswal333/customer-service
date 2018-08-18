package com.sap.refapps.espm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.refapps.espm.model.ProductReview;
import com.sap.refapps.espm.service.CustomerService;
import com.sap.refapps.espm.service.ProductReviewService;

/**
 * This class is a controller class of product service 
 * which is responsible for handling all endpoints.
 *
 */
@RestController
@RequestMapping("/review.svc/api/v1")
public class ProductReviewController {

	private final Logger logger = LoggerFactory.getLogger(ProductReviewController.class);

	@Autowired
	private ProductReviewService productReviewService;
	
	@Autowired
	private CustomerService customerService;

	/**
	 * To get all product review by product id
	 * 
	 * @return list of product review
	 * 
	 */
	@GetMapping("/reviews/{id}")
	public ResponseEntity<Iterable<ProductReview>> getProductReviewByProductId(@PathVariable(value = "id") final String productId) {
		final Iterable<ProductReview> productReview;
		
		productReview = productReviewService.getProductReviewByProductId(productId);
		if(productReview != null){
			return new ResponseEntity<>(productReview, HttpStatus.OK);
		}
		return errorMessage("Not found", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/reviews/{customerId}")
	public ResponseEntity<String> postReview(
			@RequestBody final ProductReview productReview,
			@PathVariable(value = "customerId") final String customerId) {
		productReview.setCustomer(customerService.getCustomerById(customerId));
        if(!productReviewService.postReview(productReview))
			return errorMessage("Service Currently Unavailable",HttpStatus.SERVICE_UNAVAILABLE);
		return new ResponseEntity<>("Product review created", HttpStatus.CREATED);
	}
	
	@PutMapping("/reviews/{id}/likes")
	public void updateReviewLikes(@PathVariable(value = "id") String reviewId){
		productReviewService.updateReviewLikes(reviewId);
	}
	
	@PutMapping("/reviews/{id}/dislikes")
	public void updateReviewDislikes(@PathVariable(value = "id") String reviewId){
		productReviewService.updateReviewDislikes(reviewId);
	}
	
	
	/**
	 * @param message
	 * @param status
	 * @return ResponseEntity with HTTP status,headers and body
	 */
	public static ResponseEntity errorMessage(String message, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.TEXT_PLAIN);

		return ResponseEntity.status(status).headers(headers).body(message);
	}

}
