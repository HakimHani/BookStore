package com.springboot.bookshop.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.bookshop.entity.Sales;





@Repository
public interface SalesRepository  extends JpaRepository<Sales, Long>{
	Optional<Sales> findById(Long id);
	Optional<Sales> findBySalesId(String salesId);
	Optional<Sales> findByCheckoutId(String checkoutId);
	List<Sales> findAllByCustomerEmail(String customerEmail);
	List<Sales> findAllByItemId(String itemId);
	
	@Query("select s.itemId,s.date from Sales s")
	List<Object> getAllSalesForPartner();
	
	@Query("select s.itemId,s.date from Sales s where itemId = ?1")
	List<Object> getAllByItemIdForPartner(String sProductId);
	
	
	@Query("select s from Sales s where month(s.date) = ?2 and year(s.date) = ?1")
	List<Object> getAllByDate(int sYear,int sMonth);
	
	@Query("select s from Sales s where itemId = ?1 and month(s.date) = ?3 and year(s.date) = ?2")
	List<Object> getAllByItemIdAndDate(String sProductId,int sYear,int sMonth);
	
	@Query("select s.itemId,s.date from Sales s where month(s.date) = ?2 and year(s.date) = ?1")
	List<Object> getAllByDatePartner(int sYear,int sMonth);
	
	@Query("select s.itemId,s.date from Sales s where itemId = ?1 and month(s.date) = ?3 and year(s.date) = ?2")
	List<Object> getAllByItemIdAndDatePartner(String sProductId,int sYear,int sMonth);
}
