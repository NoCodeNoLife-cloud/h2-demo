package code.exception.exceptionHandler;

// Copyright (c) 2024, NoCodeNoLife-cloud. All rights reserved.
// Author: NoCodeNoLife-cloud
// stay hungry, stay foolish
import code.exception.CustomWebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class WebExceptionHandlerAdvice {
	/**
	 * Handles a CustomWebException by logging the exception message and returning a ResponseEntity
	 * with the exception message and a status of HttpStatus.INTERNAL_SERVER_ERROR.
	 *
	 * @param e the CustomWebException to handle
	 *
	 * @return a ResponseEntity with the exception message and a status of HttpStatus.INTERNAL_SERVER_ERROR
	 */
	@ResponseBody
	@ExceptionHandler(value = CustomWebException.class)
	public ResponseEntity<String> exceptionHandler2(CustomWebException e) {
		log.info("WebExceptionHandlerAdvice catch Exception: {}", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles any Exception by logging the exception message and returning a ResponseEntity
	 * with the exception message and a status of HttpStatus.INTERNAL_SERVER_ERROR.
	 *
	 * @param e the Exception to handle
	 *
	 * @return a ResponseEntity with the exception message and a status of HttpStatus.INTERNAL_SERVER_ERROR
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> globalExceptionHandler(Exception e) {
		log.info("WebExceptionHandlerAdvice catch Global Exception: {}", e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
