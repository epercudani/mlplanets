package com.mlinc.mlplanets.web_services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DefaultExceptionHandler {

	static Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(HttpServletRequest request, Exception ex,
			HttpServletResponse response) {

		log.error(ex.getMessage(), ex);
		response.setContentLength(0);
	}
}
