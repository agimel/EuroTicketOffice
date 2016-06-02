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
	
	@RequestMapping(value="/{customerId}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomer(@PathVariable long customerId){
		customerService.deleteCustomer(customerId);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{customerId}")
	public Customer getCustomer(@PathVariable long customerId) throws CustomerNotFoundException{
		Customer customer = customerService.findCustomerById(customerId);
		if(customer == null){
			throw new CustomerNotFoundException(customerId);
		}
		return customer;
	}
}
