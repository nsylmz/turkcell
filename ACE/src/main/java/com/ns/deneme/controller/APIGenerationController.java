package com.ns.deneme.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.ns.deneme.neo4j.api.IMappingHelperAPI;
import com.ns.deneme.neo4j.api.IProcessViewAPI;
import com.ns.deneme.neo4j.domain.MappingHelper;
import com.ns.deneme.neo4j.domain.ProcessComponent;
import com.ns.deneme.neo4j.domain.ProcessConfig;
import com.ns.deneme.neo4j.domain.ProcessInputConfig;
import com.ns.deneme.neo4j.domain.ProcessView;
import com.ns.deneme.util.RunConfig;
import com.ns.deneme.vo.ProcessComponentVO;
import com.ns.deneme.vo.ProcessViewVO;
import com.ns.deneme.ws.WSRequestParameter;

@Controller
@RequestMapping(value = "/APIGeneration")
public class APIGenerationController {

	private static Logger logger = LoggerFactory.getLogger(APIGenerationController.class);
	
	@Autowired
	private IProcessViewAPI processViewAPI;
	
	@Autowired
	private IMappingHelperAPI mappingHelperAPI;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showAPIGeneration(Model model) {
		logger.debug("Received request to show api generation creen");
		List<String> processNames = new ArrayList<String>();
		for (ProcessView processView : processViewAPI.findAll()) {
			processNames.add(processView.getViewName());
		}
		model.addAttribute("viewNames", processNames);
		return "APIGeneration";
	}
	
	@RequestMapping(value = "/{viewName}", method = RequestMethod.GET)
	public String getProcessView(@PathVariable String viewName, Model model) {
		try {
			ProcessView view = processViewAPI.findProcessViewByName(viewName);
			if (view != null) {
				ProcessViewVO viewVO = new ProcessViewVO();
				viewVO.setViewName(view.getViewName());
				List<ProcessComponentVO> components = new ArrayList<>();
				Map<String, String> connections = new LinkedHashMap<String, String>();
				Map<String, WSRequestParameter> params;
				Set<ProcessInputConfig> processInputConfigs;
				Iterator<ProcessInputConfig> paramsIter;
				ProcessInputConfig processInputConfig;
				WSRequestParameter wsRequestParameter;
				ProcessComponentVO componentVO = new ProcessComponentVO();
				
				ProcessComponent processComponent = view.getStartProcess();
				viewVO.setStartProcessName(processComponent.getProcessName());
				componentVO.setProcessName(processComponent.getProcessName());
				componentVO.setPositionLeft(processComponent.getPositionLeft());
				componentVO.setPositionTop(processComponent.getPositionTop());
				componentVO.setProcessType(processComponent.getProcessType());
				components.add(componentVO);
				
				
				while (processComponent.getNextProcess() != null) {
					connections.put(processComponent.getProcessName(), processComponent.getNextProcess().getProcessName());
//					processComponent = processComponentAPI.findProcessComponentByName(processComponent.getNextProcess().getProcessName()); 
					processComponent = processComponent.getNextProcess();
					componentVO = new ProcessComponentVO();
					componentVO.setProcessName(processComponent.getProcessName());
					componentVO.setPositionLeft(processComponent.getPositionLeft());
					componentVO.setPositionTop(processComponent.getPositionTop());
					componentVO.setProcessType(processComponent.getProcessType());
					
					if (processComponent.getProcessConfig() != null 
							&& processComponent.getProcessConfig().getInputConfig() != null) {
						params = new LinkedHashMap<String, WSRequestParameter>();
						processInputConfigs = processComponent.getProcessConfig().getInputConfig();
						paramsIter = processInputConfigs.iterator();
						while (paramsIter.hasNext()) {
							processInputConfig = (ProcessInputConfig) paramsIter.next();
							wsRequestParameter = new WSRequestParameter();
							wsRequestParameter.setParamType(processInputConfig.getInputParamType());
							wsRequestParameter.setParamValue(processInputConfig.getInputParamValue());
							params.put(processInputConfig.getInputParamName(), wsRequestParameter);
						}
						componentVO.setParams(params);
					}
					components.add(componentVO);
				}
				viewVO.setComponents(components);
				viewVO.setConnections(connections);
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(viewVO).replaceAll("\"", "'");
				model.addAttribute("processView", json);
			}
			List<String> processNames = new ArrayList<String>();
			for (ProcessView processView : processViewAPI.findAll()) {
				processNames.add(processView.getViewName());
			}
			model.addAttribute("viewNames", processNames);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
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
				
				if ("webservice".equals(processComponentVO.getProcessType())) {
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
					processConfig.setInputConfig(processInputConfigs);
					processComponent.setProcessConfig(processConfig);
				}
				components.add(processComponent);
			}
			Map<String, String> connections = processViewVO.getConnections();
			Set<String> connKeys = connections.keySet();
			Iterator<String> connIter = connKeys.iterator();
			String source;
			String target;
			ProcessComponent sourceComponent;
			ProcessComponent targetComponent;
			ProcessComponent startProcess = null;
			while (connIter.hasNext()) {
				source = (String) connIter.next();
				target = connections.get(source);
				sourceComponent = findComponent(source, components);
				targetComponent = findComponent(target, components);
				sourceComponent.setNextProcess(targetComponent);
				if (source.trim().equals(processViewVO.getStartProcessName())) {
					startProcess = sourceComponent;
				}
			}
			processView.setStartProcess(startProcess);
			processViewAPI.findProcessViewByName(processViewVO.getViewName());
			processViewAPI.saveProcessView(processView);
			data.put("status", "1");
			data.put("message", "Saved Successfully.");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			data.put("status", "-1");
			data.put("message", "Save Failed!!!");
		}
		return data;
	}
	
	private ProcessComponent findComponent(String componentName, List<ProcessComponent> components) {
		for (ProcessComponent processComponent : components) {
			if (processComponent.getProcessName().trim().equals(componentName)) {
				return processComponent;
			}
		}
		return null;
	}
}
