package com.kavin.productservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kavin.productservice.dto.ProductDTO;
import com.kavin.productservice.service.ProductService;

@RestController
@RequestMapping("/productservice")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/getAllproducts")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		try {
			logger.info("in ProductController-getAllproducts method..");
			
			List<ProductDTO> listOfProducts = service.getAllProducts();
			
			logger.info("Response of getAllProducts--> " + listOfProducts);
			return new ResponseEntity<>(listOfProducts, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Exception occured at getAllProducts.." + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveProduct")
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO dto){
		try {
			logger.info("At ProductController-saveProduct method..");
			
			ProductDTO productDto = service.saveProduct(dto);
			
			logger.info("Response after saved product--> " + productDto);
			return new ResponseEntity<>(productDto, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Exception occured while saving the product.." + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getproduct/{name}")
	public ResponseEntity<List<ProductDTO>> getByProductName(@PathVariable String name){
		try {
			logger.info("At ProductController-getProductByName method.. ");
			
			List<ProductDTO> listOfProducts = service.getByProductName(name);
			
			logger.info("Response for getByProductName - " + name + listOfProducts);
			return new ResponseEntity<>(listOfProducts, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Exception occured while fetching the product by name --> " + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/removeproduct/{id}")
	public ResponseEntity<Map<String, String>> deleteProductById(@PathVariable Long id) {
	    try {
			logger.info("At ProductController-deleteProductById method.. ");
	        service.deleteProductById(id);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Product has been deleted successfully");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
			logger.error("Exception occured while removing the product from DB --> " + e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	@PutMapping("/updateproduct")
	public ResponseEntity<ProductDTO> updateProductDetails(@RequestBody ProductDTO productdto){
		try {
			logger.info("At ProductController-updateProductDetails method.. ");
			logger.info("Request sent to service class--> " + productdto);
			
			ProductDTO updatedProduct = service.updateProductDetails(productdto);
			
			logger.info("Updated product --> " + updatedProduct);
			return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
		}catch (Exception e) {
			logger.error("Exception occured while updating the product details --> " + e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	@GetMapping("/getproducts")
	public ResponseEntity<List<ProductDTO>> getFilteredProducts(@RequestParam(required = false) String category,
			@RequestParam(required = false) Double minPrice,@RequestParam(required = false) Double maxPrice,
			@RequestParam(required = false) String sortBy){
		try {
			logger.info("At ProductController-getFilteredProducts method.. ");
			logger.info("Filtered by --> " + "Category : " + category + " & minPrice : " + minPrice + 
					" & maxprice : "+ maxPrice + " & sortBy : " + sortBy);
			
			List<ProductDTO> filteredProducts = service.getFilteredProducts(category, minPrice, maxPrice, sortBy);
			
			logger.info("Response after applied filter --> "+ filteredProducts);
			return new ResponseEntity<>(filteredProducts,HttpStatus.OK);
		}catch (Exception e) {
			logger.error("Exception occured while filtering thr products --> " + e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
}
