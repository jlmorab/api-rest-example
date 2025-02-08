package com.invex.example.jlmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.invex.example.jlmb.data.entities.Employee;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Query(value = "SELECT deleteEmployeeById(:id)", nativeQuery = true)
    boolean deleteEmployeeById(@Param("id") Integer id);
	
}
