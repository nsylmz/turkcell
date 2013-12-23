package com.ns.deneme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavigationController {

	private static Logger logger = LoggerFactory.getLogger(NavigationController.class);

	@RequestMapping(value = "/UIGeneration", method = RequestMethod.GET)
	public String showUIGeneration(Model model) {

		logger.debug("Received request to show ui generation screen");

		return "UIGeneration";
	}

}
