package com.ns.deneme.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ns.deneme.neo4j.api.IProcessComponentAPI;
import com.ns.deneme.vo.UIViewVO;

@Controller
@RequestMapping(value = "/UIGeneration")
public class UIGenerationController {

	private static Logger logger = LoggerFactory.getLogger(UIGenerationController.class);
	
	@Autowired
	private IProcessComponentAPI processAPI;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showUIGeneration(Model model) {
		logger.debug("Received request to show ui generation screen");
		model.addAttribute("processes", processAPI.findAll());
		return "UIGeneration";
	}
	
	@RequestMapping(value = "/saveView", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveView(@RequestBody UIViewVO uiViewVO, Model model) {
		logger.debug("Received request to show ui generation screen");
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			
			data.put("status", "1");
			data.put("message", "Saved Successfully.");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Save Failed!!!");
		}
		return data;
	}

}
