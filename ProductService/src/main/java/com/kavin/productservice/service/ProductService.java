package com.kavin.productservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kavin.productservice.dto.ProductDTO;
import com.kavin.productservice.entity.Product;
import com.kavin.productservice.mapper.ProductMapper;
import com.kavin.productservice.repo.ProductRepo;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo repo;

	public List<ProductDTO> getAllProducts() {
		
		List<ProductDTO> dtoList = new ArrayList<>();
		List<Product> products = repo.findAll();
		
		for(Product product:products) {
			dtoList.add(ProductMapper.INSTANCE.productToProductDTO(product));
		}
		return dtoList;
	}

	public ProductDTO saveProduct(ProductDTO dto) {
		Product product =repo.save(ProductMapper.INSTANCE.productDTOToProduct(dto));
		
		return ProductMapper.INSTANCE.productToProductDTO(product);		
	}

	public List<ProductDTO> getByProductName(String name) {
		
		List<ProductDTO> dtoList = new ArrayList<>();
		List<Product> list = repo.findByName(name);
		
		if(list != null && !(list.isEmpty())){
			for(Product product:list) {
				dtoList.add(ProductMapper.INSTANCE.productToProductDTO(product));
			}
			return dtoList;
		}
		return new ArrayList<>();
	}

	@Transactional
	public void deleteProductById(Long id) {
		repo.deleteById(id);
	}

	public ProductDTO updateProductDetails(ProductDTO productdto) {
		Long id = productdto.getId();
		String name = productdto.getName();
		String description = productdto.getDescription();
		Double price = productdto.getPrice();
		String category = productdto.getCategory();
		Boolean isAvail = productdto.getIsAvail();
		String manufacturer = productdto.getManufacturer();

		repo.updateProduct(id, name, description, price, category, isAvail, manufacturer);
		
		Product product = repo.findById(id);
		return ProductMapper.INSTANCE.productToProductDTO(product);
	}

	public List<ProductDTO> getFilteredProducts(String category, Double minPrice, Double maxPrice, String sortBy) {
		
		List<Product> products = repo.findAll();
		
		List<Product> filteredProducts = products.stream().filter(product -> category == null || product.getCategory().equals(category))
				.filter(product -> minPrice ==  null || product.getPrice() >= minPrice)
				.filter(product -> maxPrice ==  null || product.getPrice() <= maxPrice)
				.collect(Collectors.toList());
		
		if(sortBy != null) {
			
			switch(sortBy) {
				case "price":
					filteredProducts.sort((p1,p2) -> p1.getPrice().compareTo(p2.getPrice()));
					break;
				case "name":
					filteredProducts.sort((p1,p2) -> p1.getName().compareTo(p2.getName()));
					break;
			}
		}
		
		return filteredProducts.stream()
				.map((ProductMapper.INSTANCE)::productToProductDTO)
				.collect(Collectors.toList());
	}	

}
