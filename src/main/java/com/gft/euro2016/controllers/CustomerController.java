package com.gft.euro2016.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gft.euro2016.domain.Customer;
import com.gft.euro2016.exceptions.CustomerNotFoundException;
import com.gft.euro2016.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer input){
		log.info("Adding customer");
		Customer result = customerService.addCustomer(input.getFirstName(), input.getLastName(), input.getEmail());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{customer_id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomer(@PathVariable long customer_id){
		customerService.deleteCustomer(customer_id);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{customer_id}")
	public Customer getCustomer(@PathVariable long customer_id) throws CustomerNotFoundException{
		Customer customer = customerService.findCustomerById(customer_id);
		if(customer == null){
			throw new CustomerNotFoundException(customer_id);
		}
		return customer;
	}
}
