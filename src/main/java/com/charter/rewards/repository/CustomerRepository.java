package com.charter.rewards.repository;

import com.charter.rewards.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByCustomerId(String customerId);
}
