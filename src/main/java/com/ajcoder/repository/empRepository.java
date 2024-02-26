package com.ajcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajcoder.entity.Employee;

public interface empRepository extends JpaRepository<Employee, Integer> {

}
