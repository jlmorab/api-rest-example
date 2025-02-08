package com.invex.example.jlmb.service;

import java.util.List;

import com.invex.example.jlmb.data.dto.WebResponseDTO;
import com.invex.example.jlmb.data.entities.Employee;

import jakarta.servlet.http.HttpServletResponse;

public interface IEmployeeService {
	WebResponseDTO getAll(HttpServletResponse response);
	WebResponseDTO delete(HttpServletResponse response, int id);
	WebResponseDTO update(HttpServletResponse response, int id, Employee request);
	WebResponseDTO add(HttpServletResponse response, List<Employee> request);
}
