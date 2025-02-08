package com.invex.example.jlmb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invex.example.jlmb.data.dto.WebResponseDTO;
import com.invex.example.jlmb.service.IEmployeeService;

import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	@InjectMocks
	EmployeeController controller;
	
	@Mock
	IEmployeeService service;
	
	@Mock
	HttpServletResponse response;
	
	@BeforeEach
	void setUp() {
		reset( service, response );
	}
	
	
	@Test
	void getAll() {
		WebResponseDTO expected = mock(WebResponseDTO.class);
		
		when( service.getAll(any(HttpServletResponse.class)) ).thenReturn( expected );
		
		WebResponseDTO actual = service.getAll( response );
		
		assertNotNull( actual );
		assertEquals( expected, actual );
	}

}
