package com.ns.deneme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ns.deneme.neo4j.api.IProcessAPI;

@Controller
@RequestMapping(value = "/UIGeneration")
public class UIGenerationController {

	private static Logger logger = LoggerFactory.getLogger(UIGenerationController.class);
	
	@Autowired
	private IProcessAPI processAPI;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showUIGeneration(Model model) {
		logger.debug("Received request to show ui generation screen");
		model.addAttribute("processes", processAPI.findAll());
		return "UIGeneration";
	}

}
