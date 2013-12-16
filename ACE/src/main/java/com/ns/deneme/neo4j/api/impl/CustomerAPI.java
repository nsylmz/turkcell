package com.ns.deneme.neo4j.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.neo4j.api.ICustomerAPI;
import com.ns.deneme.neo4j.domain.Customer;
import com.ns.deneme.neo4j.repository.CustomerRepository;

@Component
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
public class CustomerAPI implements ICustomerAPI {
	
	@Autowired
	public CustomerRepository customerRepository; 
	
    public void createCustomer(Customer customer) {
		customerRepository.save(customer);        
    }
}
