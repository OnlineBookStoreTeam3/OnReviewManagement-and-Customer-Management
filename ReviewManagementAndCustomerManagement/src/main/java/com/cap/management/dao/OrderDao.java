package com.cap.management.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cap.management.entities.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, String> {
	@Query("select o from Order o where o.customer.emailAddress=:emailId")
	public Optional<List<Order>> getCustId(@Param("emailId") String emailId);
}
