package com.gft.euro2016.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.domain.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository cr) {
		this.customerRepo=cr;
	}
	@Override
	public Customer addCustomer(String firstName, String lastName, String email) {
	
		return customerRepo.save(new Customer(firstName, lastName, email));
		
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepo.delete(id);

	}

	@Override
	public Customer findCustomerById(long customer_id) {

		return customerRepo.findOne(customer_id);
	}
	@Override
	public Customer addCustomer(Customer customer) {
		
		return customerRepo.save(customer);
	}

}
