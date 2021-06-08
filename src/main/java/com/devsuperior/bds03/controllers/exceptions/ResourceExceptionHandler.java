package com.devsuperior.bds03.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		final HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;	// code 422
		
		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation exception");
		err.setMessage(e.getMessage());

		e.getBindingResult().getFieldErrors().forEach(fe -> {
			err.addError(fe.getField(), fe.getDefaultMessage());
		});
		
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
}
