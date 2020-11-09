package com.springboot.bookshop.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.ItemInfo;



@Repository
public interface ItemInfoRepository extends JpaRepository<ItemInfo, Long>{
	Optional<ItemInfo> findByProductId(String productId);
	List<ItemInfo> findAllByBrand(String brand);
	List<ItemInfo> findAllByCategory(String category);
}