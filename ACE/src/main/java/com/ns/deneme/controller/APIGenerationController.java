package com.ns.deneme.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ns.deneme.neo4j.api.IProcessAPI;
import com.ns.deneme.neo4j.api.IViewAPI;
import com.ns.deneme.neo4j.domain.Process;
import com.ns.deneme.neo4j.domain.ProcessRunConfig;
import com.ns.deneme.util.RunConfig;
import com.ns.deneme.vo.SaveAPIGeneration;
import com.ns.deneme.ws.WSRequestParameter;

@Controller
@RequestMapping(value = "/APIGeneration")
public class APIGenerationController {

	private static Logger logger = LoggerFactory.getLogger(APIGenerationController.class);
	
	@Autowired
	private IViewAPI viewAPI;
	
	@Autowired
	private IProcessAPI processAPI;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showAPIGeneration(Model model) {
		logger.debug("Received request to show api generation creen");
		return "APIGeneration";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveViewAndProcess(@RequestBody SaveAPIGeneration saveAPIGeneration, Model model) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			viewAPI.saveView(saveAPIGeneration.getView());
			
			Process process = new Process();
			process.setProcessName(saveAPIGeneration.getProcessName());
			process.setProcessType(saveAPIGeneration.getProcessType());
			if ("WebService".equals(saveAPIGeneration.getProcessType())) {
				process.setProcessRunClass(RunConfig.WS_RUN_CLASS);
				process.setProcessRunMethod(RunConfig.WS_RUN_METHOD);
				Map<String, WSRequestParameter> params = saveAPIGeneration.getParams();
				Set<String> keySet = params.keySet();
				Iterator<String> iter = keySet.iterator();
				String paramName;
				WSRequestParameter parameter;
				ProcessRunConfig config;
				while (iter.hasNext()) {
					paramName = (String) iter.next();
					parameter = params.get(paramName);
					config = new ProcessRunConfig();
					config.setConfigParamName(paramName);
					config.setConfigParamType(parameter.getParamType());
					config.setConfigParamValue(parameter.getParamValue());
					if (RunConfig.webServiceConfig.get(paramName) == null) {
						config.setConfigMethodParamName(RunConfig.webServiceConfig.get("params"));
					} else {
						config.setConfigMethodParamName(RunConfig.webServiceConfig.get(paramName));
					}
				}
			}
			Set<ProcessRunConfig> processRunConfig = new HashSet<ProcessRunConfig>();
			process.setProcessRunConfig(processRunConfig);
			processAPI.saveProcess(process);
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
