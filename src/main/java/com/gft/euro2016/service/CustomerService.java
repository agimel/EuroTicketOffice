package com.gft.euro2016.service;

import com.gft.euro2016.domain.Customer;

public interface CustomerService {

	void deleteCustomer(Long id);
	Customer addCustomer(String firstName, String lastName, String email);
	Customer addCustomer(Customer customer);
	Customer findCustomerById(long customer_id);
}
