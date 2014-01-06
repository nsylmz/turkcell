package com.ns.deneme.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.deneme.neo4j.api.IProcessViewAPI;
import com.ns.deneme.neo4j.api.IUIVewAPI;
import com.ns.deneme.neo4j.domain.ProcessView;
import com.ns.deneme.neo4j.domain.UIComponent;
import com.ns.deneme.neo4j.domain.UIView;
import com.ns.deneme.vo.UIComponentVO;
import com.ns.deneme.vo.UIViewVO;

@Controller
@RequestMapping(value = "/UIGeneration")
public class UIGenerationController {

	private static Logger logger = LoggerFactory.getLogger(UIGenerationController.class);
	
	@Autowired
	private IProcessViewAPI processViewAPI;
	
	@Autowired
	private IUIVewAPI uiVewAPI;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showUIGeneration(Model model) {
		logger.debug("Received request to show ui generation screen");
		List<String> processNames = new ArrayList<String>();
		for (ProcessView processView : processViewAPI.findAll()) {
			processNames.add(processView.getViewName());
		}
		model.addAttribute("processNames", processNames);
		List<String> uiViewNames = new ArrayList<String>();
		for (UIView uiView : uiVewAPI.findAll()) {
			uiViewNames.add(uiView.getViewName());
		}
		model.addAttribute("viewNames", uiViewNames);
		return "/ui/UIGeneration";
	}
	
	@RequestMapping(value = "/getAllViewNames", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAllViewNames(Model model) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		List<String> uiViewNames = new ArrayList<String>();
		for (UIView uiView : uiVewAPI.findAll()) {
			uiViewNames.add(uiView.getViewName());
		}
		if (uiViewNames.size() > 0) {
			data.put("status", "1");
			data.put("message", "View Name Successfully Retrieved.");
		} else {
			data.put("status", "-1");
			data.put("message", "There Is No View For Demostrate!!!");
		}
		model.addAttribute("viewNames", uiViewNames);
		return data;
	}
	
	@RequestMapping(value = "/{viewName}", method = RequestMethod.GET)
	public String getUIView(@PathVariable String viewName, Model model) {
		logger.debug("Received request to show ui generation screen");
		try {
			List<String> processNames = new ArrayList<String>();
			for (ProcessView processView : processViewAPI.findAll()) {
				processNames.add(processView.getViewName());
			}
			model.addAttribute("processNames", processNames);
			
			ObjectMapper mapper = new ObjectMapper();
			UIView uiView = uiVewAPI.findUIViewByName(viewName);
			if (uiView != null && uiView.getViewName() != null && uiView.getViewName().length() > 0) {
				UIViewVO uiViewVO = new UIViewVO();
				uiViewVO.setViewName(uiView.getViewName());
				List<UIComponentVO> componentVOs = new ArrayList<>();
				UIComponentVO componentVO;
				for (UIComponent component : uiView.getComponents()) {
					componentVO = new UIComponentVO();
					componentVO.setComponentName(component.getComponentName());
					componentVO.setComponentLabel(component.getComponentLabel());
					componentVO.setElementName(component.getElementName());
					componentVO.setElementType(component.getElementType());
					componentVO.setPositionLeft(component.getPositionLeft());
					componentVO.setPositionTop(component.getPositionTop());
					componentVO.setComponentProcessName(component.getProcessName());
					componentVOs.add(componentVO);
				}
				uiViewVO.setComponents(componentVOs);
				String json = mapper.writeValueAsString(uiViewVO).replaceAll("\"", "'");
				model.addAttribute("uiView", json);
				
				List<String> uiViewNames = new ArrayList<String>();
				for (UIView view : uiVewAPI.findAll()) {
					uiViewNames.add(view.getViewName());
				}
				model.addAttribute("viewNames", uiViewNames);
			}
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
		return "/ui/UIGeneration";
	}
	
	@RequestMapping(value = "/saveView", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveView(@RequestBody UIViewVO uiViewVO, Model model) {
		logger.debug("Received request to show ui generation screen");
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Set<UIComponent> components = new HashSet<UIComponent>();
			UIComponent uiComponent;
			for (UIComponentVO uiComponentVO : uiViewVO.getComponents()) {
				uiComponent = new UIComponent();
				uiComponent.setComponentName(uiComponentVO.getComponentName());
				uiComponent.setComponentLabel(uiComponentVO.getComponentLabel());
				uiComponent.setElementName(uiComponentVO.getElementName());
				uiComponent.setElementType(uiComponentVO.getElementType());
				uiComponent.setPositionLeft(uiComponentVO.getPositionLeft());
				uiComponent.setPositionTop(uiComponentVO.getPositionTop());
				uiComponent.setProcessName(uiComponentVO.getComponentProcessName());
				components.add(uiComponent);
			}
			UIView uiView = new UIView();
			uiView.setViewName(uiViewVO.getViewName());
			uiView.setComponents(components);
			uiVewAPI.saveUIView(uiView);
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
