package com.cap.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cap.management.entities.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, String> {

}
