package com.kavin.productservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kavin.productservice.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

	List<Product> findByName(String name);

	void deleteById(Long id);
	
	@Transactional
    @Modifying
    @Query("UPDATE Product p SET p.name = :name, p.description = :description, p.price = :price, p.category = :category, p.isAvail = :isAvail, p.manufacturer = :manufacturer WHERE p.id = :id")
    void updateProduct(Long id, String name, String description, Double price, String category, boolean isAvail, String manufacturer);

	Product findById(Long id);

}
