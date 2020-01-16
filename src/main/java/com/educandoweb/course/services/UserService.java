package com.educandoweb.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository repository;

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		return repository.findById(id).get();
	}

	public User save(User entity) {
		User persistedEntity = new User();
		try {
			return repository.save(entity);
		} catch (IllegalArgumentException e) {
			System.out.println("Entity is null");
		}
		return persistedEntity;
	}

}
