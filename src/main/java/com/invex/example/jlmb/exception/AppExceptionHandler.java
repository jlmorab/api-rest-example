package com.invex.example.jlmb.exception;

import static com.invex.example.jlmb.utils.Constants.CLIENT_ERROR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.invex.example.jlmb.data.dto.MetaDTO;
import com.invex.example.jlmb.data.dto.WebResponseDTO;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        MetaDTO meta = MetaDTO.builder()
            .status(CLIENT_ERROR)
            .statusCode(ex.getStatusCode().value())
            .build();
        meta.addMessage(ex.getReason());

        WebResponseDTO response = WebResponseDTO.builder()
            .meta(meta)
            .build();

        log.error("X -> {}", ex.getReason());

        return new ResponseEntity<>(response, httpHeaders, ex.getStatusCode());
    }
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    	return customArgumentResponse( exception );
    }
	
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleArgumentException(Exception exception) {
    	return customArgumentResponse( exception );
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception) {
    	return customArgumentResponse( exception );
    }
    
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(RuntimeException runtimeException, WebRequest webRequest) {
        String message = runtimeException.getMessage() == null ? runtimeException.getClass().toString() : runtimeException.getMessage();

        boolean rollback = false;

        HttpStatus httpStatus= HttpStatus.INTERNAL_SERVER_ERROR;

        MetaDTO meta = MetaDTO.builder()
            .status(httpStatus.name())
            .statusCode(httpStatus.value())
            .message(null)
            .rollback(rollback)
            .build();

        WebResponseDTO response = WebResponseDTO.builder()
            .meta(meta)
            .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        
        log.error("X -> {}", message, runtimeException);

        return handleExceptionInternal(
            runtimeException,
            response,
            httpHeaders,
            httpStatus,
            webRequest
        );
    }
    
    private ResponseEntity<Object> customArgumentResponse( Exception exception ) {
    	
    	List<String> errors = new ArrayList<>();
    	if( exception instanceof MethodArgumentNotValidException ex ) {
    		errors = ex.getFieldErrors().stream()
    				.map( violation -> violation.getField() + ": " + violation.getDefaultMessage() )
    				.toList();
    	} else if( exception instanceof IllegalArgumentException ex ) {
    		errors.add(ex.getMessage());
    	} else if( exception instanceof ConstraintViolationException ex ) {
    		errors = ex.getConstraintViolations().stream()
    				.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
    				.toList();
    	}//end if
    	
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        
        MetaDTO meta = MetaDTO.builder()
            .status(httpStatus.name())
            .statusCode(httpStatus.value())
            .message(errors)
            .build();

        WebResponseDTO apiResponseDTO = WebResponseDTO.builder()
            .meta(meta)
            .build();

        log.error("Validation failed: {}", errors);

        return new ResponseEntity<>(apiResponseDTO, httpHeaders, httpStatus);

    }
    
}
