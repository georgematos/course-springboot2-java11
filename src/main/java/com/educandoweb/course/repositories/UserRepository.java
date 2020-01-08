package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.entities.User;

@Repository // opicional pois JpaRepository já está anotado
public interface UserRepository extends JpaRepository<User, Long> {

}
