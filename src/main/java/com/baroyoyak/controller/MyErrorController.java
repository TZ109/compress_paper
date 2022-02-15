package com.baroyoyak.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
	
	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request) {
		//String VIEW_PATH = "/errors/";

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "redirect:/";//VIEW_PATH + "404";
			}
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "redirect:/";// VIEW_PATH + "500";
			}
		}
		return "redirect:/";
	}
	
	
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}

}
