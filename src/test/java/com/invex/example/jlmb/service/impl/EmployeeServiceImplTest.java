package com.invex.example.jlmb.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invex.example.jlmb.data.TestData;
import com.invex.example.jlmb.data.dto.MetaDTO;
import com.invex.example.jlmb.data.dto.WebResponseDTO;
import com.invex.example.jlmb.data.entities.Employee;
import com.invex.example.jlmb.enums.MetaEnum;
import com.invex.example.jlmb.repository.IEmployeeRepository;

import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	private static final Integer 	ANY_INT 		= TestData.getRandom(1, 1000);
	private static final MetaDTO 	META_OK 		= MetaEnum.OK.getMeta();
	private static final MetaDTO 	META_ERROR 		= MetaEnum.ERROR.getMeta();
	
	@InjectMocks
	EmployeeServiceImpl service;
	
	@Mock
	IEmployeeRepository repository;
	
	@Mock
	HttpServletResponse response;
	
	@BeforeEach
	void setUp() {
		reset( repository, response );
	}
	
	@Test
	void getAllEmployee_withReturnData() {
		List<Employee> expected = TestData.employees();
		
		when( repository.findAll() ).thenReturn( expected );
		
		WebResponseDTO actual = service.getAll( response );
		System.out.println( actual );
		responseCorrect( actual );
		assertEquals( expected, actual.getData() );
	}
	
	@Test
	void delete_correctly() {
		boolean expected = true;
		
		when( repository.existsById(anyInt()) ).thenReturn( true );
		when( repository.deleteEmployeeById(anyInt()) ).thenReturn( expected );
		
		WebResponseDTO actual = service.delete(response, ANY_INT);
		
		responseCorrect( actual );
		assertEquals( expected, actual.getData() );
	}
	
	@Test
	void delete_invalidEmployee() {
		when( repository.existsById(anyInt()) ).thenReturn( false );
		
		WebResponseDTO actual = service.delete(response, ANY_INT);
		
		responseError( actual );
	}
	
	private void responseCorrect( WebResponseDTO actual ) {
		verify( response, times(1) ).setStatus( META_OK.getStatusCode() );
		assertNotNull( actual );
		assertNotNull( actual.getMeta() );
		assertEquals( META_OK.getStatus(), actual.getMeta().getStatus() );
		assertNotNull( actual.getData() );
	}
	
	private void responseError( WebResponseDTO actual ) {
		verify( response, times(1) ).setStatus( META_ERROR.getStatusCode() );
		assertNotNull( actual );
		assertNotNull( actual.getMeta() );
		assertEquals( META_ERROR.getStatus(), actual.getMeta().getStatus() );
		assertNotNull( actual.getMeta().getMessage() );
	}
	
	

}
