package com.ns.deneme.neo4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ns.deneme.neo4j.api.ICustomerAPI;
import com.ns.deneme.neo4j.domain.Address;
import com.ns.deneme.neo4j.domain.Country;
import com.ns.deneme.neo4j.domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/application-config.xml")
@Transactional(readOnly=false, propagation = Propagation.REQUIRED, noRollbackFor = { EmptyResultDataAccessException.class }, rollbackFor = { Exception.class })
@TransactionConfiguration(defaultRollback=false)
public class CustomerTest {
	
	@Autowired
	private ICustomerAPI customerAPI;
	
	@Test
	public void test() {
		Country country = new Country("tr", "TURKEY");
		Address address = new Address("Handegul", "ISTANBUL", country);
		Customer customer = new Customer("Enes", "YILMAZ", "ylmz.enes@gmail.com");
		customer.add(address);
		customerAPI.createCustomer(customer);
	}

}
