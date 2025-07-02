package com.cts.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.application.data.CustomerEntity;
import com.cts.application.data.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;
	
	public CustomerService(CustomerRepository repository) {
	 	this.repository = repository;
	}
	
	public List<CustomerEntity> findAll(){
		return repository.findAll();
	}
	
	public CustomerEntity findById(String id){
		Optional<CustomerEntity> optional = repository.findById(id);
		if (optional.isEmpty())
			return null;
		else
			return optional.get();
	}
	
	public void saveCustomer(CustomerEntity entity) {
		repository.save(entity);
	}
	
}
