package com.educandoweb.course.services;

import java.util.List;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (IllegalArgumentException e) {
			System.out.println("Id is null");
		} catch (DataIntegrityViolationException e) {
			System.out.println("The entity owns other entity associated.");
		}
	}

	public User update(Long id, User entity) {
		User originalUser = repository.getOne(id);
		updateData(originalUser, entity);
		return repository.save(originalUser);
	}

	private void updateData(User originalUser, User entity) {
		originalUser.setName(entity.getName());
		originalUser.setEmail(entity.getEmail());
		originalUser.setPhone(entity.getPhone());
	}

}
