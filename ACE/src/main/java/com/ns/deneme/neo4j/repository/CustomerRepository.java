package com.ns.deneme.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.ns.deneme.neo4j.domain.Customer;

public interface CustomerRepository extends GraphRepository<Customer> {

        Customer findOne(Long id);

        <C extends Customer> C save(C customer);

        Customer findByEmailAddress(String emailAddress);
}