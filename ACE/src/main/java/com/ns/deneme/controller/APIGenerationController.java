package com.ns.deneme.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.ns.deneme.neo4j.api.IMappingHelperAPI;
import com.ns.deneme.neo4j.api.IProcessComponentAPI;
import com.ns.deneme.neo4j.api.IProcessViewAPI;
import com.ns.deneme.neo4j.domain.MappingHelper;
import com.ns.deneme.neo4j.domain.ProcessComponent;
import com.ns.deneme.neo4j.domain.ProcessConfig;
import com.ns.deneme.neo4j.domain.ProcessInputConfig;
import com.ns.deneme.neo4j.domain.ProcessView;
import com.ns.deneme.util.RunConfig;
import com.ns.deneme.vo.ProcessComponentVO;
import com.ns.deneme.vo.ProcessViewVO;
import com.ns.deneme.vo.ViewVO;
import com.ns.deneme.ws.WSRequestParameter;

@Controller
@RequestMapping(value = "/APIGeneration")
public class APIGenerationController {

	private static Logger logger = LoggerFactory.getLogger(APIGenerationController.class);
	
	@Autowired
	private IProcessViewAPI processViewAPI;
	
	@Autowired
	private IProcessComponentAPI processComponentAPI;
	
	@Autowired
	private IMappingHelperAPI mappingHelperAPI;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showAPIGeneration(Model model) {
		logger.debug("Received request to show api generation creen");
		return "APIGeneration";
	}
	
	@RequestMapping(value = "/{viewName}", method = RequestMethod.GET)
	public String getProcessView(@PathVariable String viewName, Model model) {
		/*
		try {
			ProcessView view = viewAPI.findProcessViewByName(viewName);
			if (view != null) {
				ViewVO viewVO = new ViewVO();
				viewVO.setViewName(view.getViewName());
				viewVO.setFeatureBars(view.getFeatureBars());
				viewVO.setChart(view.getChart());
				Map<String, String> configMap = new HashMap<String, String>();
				Set<ProcessInputConfig> configs = view.getProcess().getProcessRunConfig();
				Iterator<ProcessInputConfig> iter = configs.iterator();
				ProcessInputConfig runConfig;
				while (iter.hasNext()) {
					runConfig = (ProcessInputConfig) iter.next();
					if (runConfig.getConfigParamName() != null && runConfig.getConfigParamName().length() >0) {
						configMap.put(runConfig.getConfigParamName(), runConfig.getConfigParamValue());
					}
				}
				ObjectMapper mapper = new ObjectMapper();
				viewVO.setFeatureJson(mapper.writeValueAsString(configMap));
				model.addAttribute("view", viewVO);
			}
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
		 */
		return "APIGeneration";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveViewAndProcess(@RequestBody ProcessViewVO processViewVO, Model model) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			ProcessView processView = new ProcessView();
			processView.setViewName(processViewVO.getViewName());
			
			Set<MappingHelper> helpers = mappingHelperAPI.findAll();
			
			List<ProcessComponent> components = new ArrayList<ProcessComponent>();
			ProcessComponent processComponent;
			Map<String, WSRequestParameter> params;
			Set<ProcessInputConfig> processInputConfigs;
			ProcessInputConfig processInputConfig;
			ProcessConfig processConfig;
			Set<String> paramsKeys;
			Iterator<String> paramsIter;
			String paramName;
			WSRequestParameter parameter;
			for (ProcessComponentVO processComponentVO : processViewVO.getComponents()) {
				processComponent = new ProcessComponent();
				processComponent.setProcessName(processComponentVO.getProcessName());
				processComponent.setProcessType(processComponentVO.getProcessType());
				processComponent.setPositionLeft(processComponentVO.getPositionLeft());
				processComponent.setPositionTop(processComponentVO.getPositionTop());
				
				if ("WebService".equals(processComponentVO.getProcessType())) {
					processConfig = new ProcessConfig();
					processConfig.setConfigName(processComponentVO.getProcessName() + "Config");
					processConfig.setProcessRunClass(RunConfig.WS_RUN_CLASS);
					processConfig.setProcessRunMethod(RunConfig.WS_RUN_METHOD);
					processInputConfigs = new HashSet<ProcessInputConfig>();
					params = processComponentVO.getParams();
					paramsKeys = params.keySet();
					paramsIter = paramsKeys.iterator();
					while (paramsIter.hasNext()) {
						paramName = (String) paramsIter.next();
						parameter = params.get(paramName);
						processInputConfig = new ProcessInputConfig();
						processInputConfig.setInputParamName(paramName);
						processInputConfig.setInputParamType(parameter.getParamType());
						processInputConfig.setInputParamValue(parameter.getParamValue());
						if (RunConfig.webServiceConfig.get(paramName) == null) {
							processInputConfig.setConfigMethodParamName(RunConfig.webServiceConfig.get("params"));
							processInputConfig.setConfigMethodParamMappingHelper(helpers);
						} else {
							processInputConfig.setConfigMethodParamName(RunConfig.webServiceConfig.get(paramName));
						}
						processInputConfigs.add(processInputConfig);
					}
					processComponent.setProcessConfig(processConfig);
				}
				components.add(processComponent);
			}
			// TODO create components
			
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
