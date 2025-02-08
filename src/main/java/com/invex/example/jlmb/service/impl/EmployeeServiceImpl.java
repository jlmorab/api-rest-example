package com.invex.example.jlmb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.invex.example.jlmb.data.dto.WebResponseDTO;
import com.invex.example.jlmb.data.entities.Employee;
import com.invex.example.jlmb.exception.LogicException;
import com.invex.example.jlmb.repository.IEmployeeRepository;
import com.invex.example.jlmb.service.IEmployeeService;
import com.invex.example.jlmb.service.ServiceAbstract;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends ServiceAbstract implements IEmployeeService {
	
	private final IEmployeeRepository repository;
	
	@Override
	public WebResponseDTO getAll(HttpServletResponse response) {
		return executeFlow( response, log, () -> repository.findAll() );
	}
	
	@Override
	public WebResponseDTO delete(HttpServletResponse response, int id) {
		return executeFlow( response, log, () -> { 
			validEmployee(id);
			return repository.deleteEmployeeById(id);
		});
	}
	
	@Override
	public WebResponseDTO update(HttpServletResponse response, int id, Employee request) {
		return executeFlow( response, log, () -> {
			validEmployee(id);
			request.setEmpleadoId(id);
			repository.save(request);
			return true;
		});
	}
	
	@Override
	public WebResponseDTO add(HttpServletResponse response, List<Employee> request) {
		return executeFlow( response, log, () -> repository.saveAllAndFlush(request) );
	}
	
	private void validEmployee(int id) {
		if( !repository.existsById(id) ) {
			throw new LogicException("Invalid employee");
		}
	}

}
