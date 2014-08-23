package br.com.tscpontual.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

private static Logger log = Logger.getLogger(BaseController.class);
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception tr) {
		log.error(tr.getMessage(), tr);
		return "genericerror";
	}
	
}