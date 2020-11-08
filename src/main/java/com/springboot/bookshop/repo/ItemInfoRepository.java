package com.springboot.bookshop.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.ItemInfo;



@Repository
public interface ItemInfoRepository extends JpaRepository<ItemInfo, Long>{
	Optional<ItemInfo> findBySku(String sku);
}