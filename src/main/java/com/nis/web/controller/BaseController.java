package com.nis.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nis.web.service.UserService;

public class BaseController {
	
	@Autowired
	protected UserService userService;
	
	protected final Logger logger = Logger.getLogger(this.getClass());

}
