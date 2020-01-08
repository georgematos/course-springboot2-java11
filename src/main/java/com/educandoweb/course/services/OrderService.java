package com.educandoweb.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.repositories.OrderRepository;

@Service
public class OrderService {

	private OrderRepository repository;

	@Autowired
	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findOrderById(Long id) {
		return repository.findById(id).get();
	}

}
