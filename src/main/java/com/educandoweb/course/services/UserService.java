package com.educandoweb.course.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DataBaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
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
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public User update(Long id, User entity) {
		try {
			User originalUser = repository.getOne(id);
			updateData(originalUser, entity);

			return repository.save(originalUser);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(entity);
		}
	}

	private void updateData(User originalUser, User entity) {
		originalUser.setName(entity.getName());
		originalUser.setEmail(entity.getEmail());
		originalUser.setPhone(entity.getPhone());
	}

}
