<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="docs-assets/ico/favicon.png">
	
	<title>Turkcell ACE</title>
	<link rel="shortcut icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
	<link rel="icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
	<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/ladda.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/ladda-themeless.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/UIGeneration.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/flowchart.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/featureBar.css" rel="stylesheet" type="text/css" />
</head>
<c:choose>
    <c:when test="${not empty processView}">
      <body onload="onload(${processView}, '${pageContext.request.contextPath}')">
    </c:when>
    <c:otherwise>
        <body>
    </c:otherwise>
</c:choose>
	<div class="notification-container">
	 	<div class="alert appStore-notification">
			<button type="button" class="close notification-close" aria-hidden="true" onclick="closeNotification()">&times;</button>
	    </div>
	</div>
	<div id="htmlPallet-container" class="container">
		<div class="navbar-wrapper">
			<div class="container">
				<div class="navbar navbar-inverse navbar-static-top">
					<div class="container">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".navbar-collapse">
								<span class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath}/UIGeneration">Application Creation Environment</a>
						</div>
						<div class="navbar-collapse collapse">
							<ul class="nav navbar-nav">
								<li><a href="${pageContext.request.contextPath}/UIGeneration">UI Generation</a></li>
								<li class="active"><a href="${pageContext.request.contextPath}/APIGeneration">API Generation</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="view-bar bar-transition">
			<div class="view-bar-item item-first" onclick="openSaveBar()">
				<img class="view-item-img" title="Save View" alt="Save View" src="${pageContext.request.contextPath}/img/save-icon.png">
				<label>Save Process</label>
			</div>
	        <div class="view-bar-item item-last" onclick="openViewModal('${pageContext.request.contextPath}')">
	        	<img class="view-item-img" title="Open View" alt="Open View" src="${pageContext.request.contextPath}/img/open-icon.png">
		        <label>Open Process</label>
	        </div>
	    </div>
	     <div class="modal fade" id="openViewModal" tabindex="-1" role="dialog" aria-labelledby="openViewModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">Application Creation Environment</h3>
					</div>
					<div class="modal-body" id="openViewModalBody">
						<c:if test="${not empty viewNames}">
							<ol id="selectable">
		    					<c:forEach var="viewName" items="${viewNames}">
									<li class="ui-widget-content">${viewName}</li>
								</c:forEach>
							</ol>
							<button type="button" class="ace-button go-to-view-button" onclick="goToAPIView(this, '${pageContext.request.contextPath}')">go to view</button>
						</c:if>
					</div>
                    <div class="modal-footer">
                      	<button type="button" class="ace-button modal-close" data-dismiss="modal">close</button>
					</div>
				</div>
			</div>
		</div>
	    <div class="save-bar bar-transition">
	    	<button type="button" class="close save-bar-close" aria-hidden="true" onclick="closeSaveBar()">&times;</button>
			<label>Process Name</label>
			<input id="view-input" class="view-name-input" type="text" name="viewInput">
			<button id="save-view-button" class="ace-button btn btn-primary ladda-button" data-style="expand-right"
		        onclick="saveProcess(this, '${pageContext.request.contextPath}')">
		        <span class="ladda-label">Save Process</span>
	        </button>
	    </div>
		<div id="products">
			<h1 class="ui-widget-header">API Elements</h1>
			<div id="catalog">
				<h2>
					<a href="#">Process</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<li class="list-group-item" element-name="begin">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/start.png">
								<div class="list-item-label">Begin</div>
							</div>
						</li>
						<li class="list-group-item" element-name="end">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/end.png">
								<div class="list-item-label">End</div>
							</div>
						</li>
						<li class="list-group-item" element-name="if">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/if.png">
								<div class="list-item-label">If</div>
							</div>
						</li>
					</ul>
				</div>
				<h2>
					<a href="#">End Points</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<li class="list-group-item" element-name="webservice">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/web-service.png">
								<div class="list-item-label">Web Service</div>
							</div>
						</li>
						<li class="list-group-item" element-name="restservice">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/rest-service.png">
								<div class="list-item-label">Rest Service</div>
							</div>
						</li>
					</ul>
				</div>
				<h2>
					<a href="#">Java</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<li class="list-group-item" element-name="javamethod">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/java-big.png">
								<div class="list-item-label">Java API Method</div>
							</div>
						</li>
					</ul>
				</div>	
			</div>
		</div>

		<div id="cart">
			<h1 class="ui-widget-header">API Process</h1>
			<div id="flowchart-view" class="ui-widget-content view flowchart-view"></div>
		</div>

		<div class="feature-container">
			<div id="webservice-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
						<li><a href="#WebService-feature">WebService</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
					</div>
					<div id="WebService-feature">
						<div class="component-feature-container">
							<div class="component-feature">
								<label>Wsdl URL</label>
								<input class="wsdl-input feature-input" type="text" name="wsdlUrl">
								<button class="read-wsdl-button ace-button btn btn-primary ladda-button" data-style="expand-down"
		                                onclick="readWsdlAndGetOperationName(this, '${pageContext.request.contextPath}')">
		                            <span class="ladda-label">Read WSDL</span>
		                        </button>
								<div class="ws-select ui-widget">
									<label>Operations </label>
									<select class="ws-opreations">
										<option value="">Select Operation...</option>
									</select>
									<button class="get-operation-button ace-button btn btn-primary ladda-button" data-style="expand-down"
		                                onclick="getOpDetail(this, '${pageContext.request.contextPath}')">
			                            <span class="ladda-label">Get Operation Detail</span>
			                        </button>
								</div>
								<button class="ws-run-button ace-button btn btn-primary ladda-button" data-style="expand-left"
		                                onclick="runWS(this, '${pageContext.request.contextPath}')">
			                            <span class="ladda-label">Test Ws Operation</span>
			                    </button>
							</div>
							<div class="component-feature">
								<div id="param-name-container" class="param-container">
									<label class="param-name">Parameter Name</label>
									<input id="param-name-input" class="param-input feature-input" type="text" name="param-name">
									<label class="param-type">Parameter Type</label>
								</div>
								<div class="params-container"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="begin-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="end-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="if-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="wsResponseModal" tabindex="-1" role="dialog" aria-labelledby="wsResponseModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">Application Creation Environment</h3>
					</div>
					<div class="modal-body" id="wsResponseModalBody">
						<label class="class-name">ClassName</label>
						<ul class="list-group ws-response-content">
							<li class="list-group-item ws-response-item">
								<div class="ws-item-field-name">fieldName</div>
								<div class="ws-item-field-value">fieldValue</div>
							</li>
						</ul>
					</div>
                    <div class="modal-footer">
                      	<button type="button" class="ace-button modal-close" data-dismiss="modal">close</button>
					</div>
				</div>
			</div>
		</div>

	<script src="${pageContext.request.contextPath}/js/jquery-2.0.3.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/holder.js" type="text/javascript"></script>
	
	<script src="${pageContext.request.contextPath}/js/flowchart/jquery.ui.touch-punch.min.js"></script>        
    <script src="${pageContext.request.contextPath}/js/flowchart/jsBezier-0.6.js"></script>     
    <script src="${pageContext.request.contextPath}/js/flowchart/jsplumb-geom-0.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/util.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/dom-adapter.js"></script>        
    <script src="${pageContext.request.contextPath}/js/flowchart/jsPlumb.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/endpoint.js"></script>                
    <script src="${pageContext.request.contextPath}/js/flowchart/connection.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/anchors.js"></script>        
    <script src="${pageContext.request.contextPath}/js/flowchart/defaults.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connectors-bezier.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connectors-statemachine.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connectors-flowchart.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connector-editors.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/renderers-svg.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/renderers-canvas.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/renderers-vml.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/jquery.jsPlumb.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/flowchart.js"></script>
    
    <script src="${pageContext.request.contextPath}/js/ladda/spin.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ladda/ladda.min.js"></script>
    
	<script src="${pageContext.request.contextPath}/js/jquery-ui.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/ComboBox.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/APIGeneration.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		APIGeneration();
		$( "#selectable" ).selectable();
		
		function openViewModal(basePath) {
			$('#openViewModal').modal('toggle');
		}
		
		function goToAPIView(button, basePath) {
			var viewName = $(button).parent().find('.ui-selected').text();
			if (viewName) {
				window.location.href = basePath + "/APIGeneration/" + viewName;
			} else {
				notify('noViewSelectedErrorNotification', 'alert-info', 'No APIView Selected!!! Please Select API View.', 5000);
			}
		}
		
		function readWsdlAndGetOperationName(button, basePath) {
			var wsdlUrl = $(button).parent().find('.feature-input').val();
		    var combobox = $(button).parent().find(".ws-opreations");
		    var l = Ladda.create(button);
		    l.start();
		    $.ajax({
		        type: "POST",
		        url: basePath + "/ws/readWsdl",
		        data: "wsdlUrl=" + wsdlUrl,
		        success: function (data) {
		            l.stop();
		            if (data.status == 1) {
		            	notify('readWSDLSuccessNotification', 'alert-info', data.message, 5000);
		            	
		            	var operations = data.operations;
		            	var cloneOption = combobox.children().first().clone();
		            	combobox.empty();
		            	combobox.append(cloneOption);
		            	var newOption;
		            	for (var i in operations) {
		            		newOption = cloneOption.clone();
		            		newOption.attr("value", operations[i]);
		            		newOption.text(operations[i]);
		            		combobox.append(newOption);
						}
		            	$(button).parent().find('.ws-run-button').css("display", "none");
		            	createWsOpSelect(combobox);
		            	return combobox;
		            } else if (data.status < 1) {
		            	notify('readWSDLErrorNotification', 'alert-danger', data.message, 5000);
		            }
		        }
		    });
		}
		
		function getOpDetail(button, basePath) {
			var wsdlUrl = $(button).parent().parent().find('.wsdl-input').val();
			var operationName = $(button).parent().find('option:selected').val();
			var paramsContainer = $(button).parent().parent().parent().find('.params-container');
		    var l = Ladda.create(button);
		    l.start();
		    $.ajax({
		        type: "POST",
		        url: basePath + "/ws/getOpDetail",
		        data: "wsdlUrl=" + wsdlUrl + "&operationName=" + operationName,
		        success: function (data) {
		            l.stop();
		            if (data.status == 1) {
		            	notify('getOpDetailSuccessNotification', 'alert-info', data.message, 5000);
		            	var tempParamContainer = $(button).parent().parent().parent().find("#param-name-container");
		            	
		            	paramsContainer.empty();
		            	var inputParams = data.inputParams;
		            	var newParam;
		            	var paramType;
		            	for (var paramName in inputParams) {
		            		paramType = inputParams[paramName];
		            		if (paramType == "string" || paramType == "int" || paramType == "long" 
		            				|| paramType == "float" || paramType == "double" || paramType == "boolean") {
			            		newParam = tempParamContainer.clone();
			            		newParam.attr("id", paramName + "-container");
			            		newParam.find('.param-name').text(paramName);
			            		newParam.find('.param-type').text(paramType);
			            		newParam.find('.feature-input').attr("id", paramName + "-input");
			            		newParam.find('.feature-input').attr("name", paramName);
			            		paramsContainer.append(newParam);
							} else if (paramType == "dateTime") {
								newParam = tempParamContainer.clone();
			            		newParam.attr("id", paramName + "-container");
			            		newParam.find('.param-name').text(paramName);
			            		newParam.find('.param-type').text(paramType);
			            		newParam.find('.feature-input').attr("id", paramName + "-input");
			            		newParam.find('.feature-input').attr("name", paramName);
			            		newParam.find('.feature-input').datetimepicker();
			            		paramsContainer.append(newParam);
							} else if (paramType == "date") {
								newParam = tempParamContainer.clone();
			            		newParam.attr("id", paramName + "-container");
			            		newParam.find('.param-name').text(paramName);
			            		newParam.find('.param-type').text(paramType);
			            		newParam.find('.feature-input').attr("id", paramName + "-input");
			            		newParam.find('.feature-input').attr("name", paramName);
			            		newParam.find('.feature-input').datepicker("option", "showAnim", "slide");
			            		paramsContainer.append(newParam);
							}
						}
		            	if(paramsContainer.children().length > 5) {
		            		paramsContainer.parent().css("overflow-y", "scroll");
		            		paramsContainer.parent().css("overflow-x", "hidden");
		            	}
		            	$(button).parent().parent().find(".ws-run-button").css("display", "block");
		            } else if (data.status < 1) {
		            	notify('getOpDetailErrorNotification', 'alert-danger', data.message, 5000);
		            }
		        }
		    });
		}
		
		function runWS(button, basePath) {
		    var l = Ladda.create(button);
		    l.start();
			var wsdlUrl = $(button).parent().find('.wsdl-input').val();
			var operationName = $(button).parent().find('.ws-opreations').find('option:selected').val();
			var wsRunParams = {"wsdlUrl" : wsdlUrl, "operation" : operationName, "paramNameAndparamValue" : null};
			var paramNameAndparamValue = {};
			var param;
			var paramName;
			var paramType;
			var paramValue;
			$(button).parent().parent().find('.params-container').children().each(function() {
				paramName = $(this).find('.param-name').text();
				paramType = $(this).find('.param-type').text();
				paramValue = $(this).find('.param-input').val();
				paramNameAndparamValue[paramName] = { "paramType" : paramType, "paramValue" : paramValue};
			});
			wsRunParams["paramNameAndparamValue"] = paramNameAndparamValue;
		    $.ajax({
		        type: "POST",
		        dataType:'json',
		        contentType:"application/json",
		        url: basePath + "/ws/runWS",
		        data:JSON.stringify(wsRunParams),
		        success: function (data) {
		            l.stop();
		            if (data.status == 1) {
		            	showWsResponse(data.wsResponse);
		            } else if (data.status < 1) {
		            	notify('runWsErrorNotification', 'alert-danger', data.message, 5000);
		            }
		        }
		    });
		}
		
		function showWsResponse(wsResponse) {
			var wsResponseMadolBody = $('#wsResponseModalBody');
			wsResponseMadolBody.find('.class-name').text(wsResponse["className"]);
			var wsFieldList = wsResponseMadolBody.find('.ws-response-content');
			var tempFieldItem = wsFieldList.children().first().clone();
			wsFieldList.empty();
			var field;
			var newField;
			for ( var i in wsResponse["fields"]) {
				field = wsResponse["fields"][i];
				newField = tempFieldItem.clone();
				newField.find('.ws-item-field-name').text(field["fieldName"]);
				newField.find('.ws-item-field-value').text(field["fieldValue"]);
				wsFieldList.append(newField);
			}
			$('#wsResponseModal').modal("toggle");
		}
		
		function createWsOpSelect(combobox) {
		    combobox.parent().css("display", "block");
		    combobox.combobox();
		}
		
		function openSaveBar() {
			$(".view-bar").css("left", "-165px");
			$(".save-bar").css("left", "0px");
		}
		
		function closeSaveBar() {
			$(".save-bar").css("left", "-255px");
			$(".view-bar").css("left", "0px");
		}
		
		function saveProcess(button, basePath) {
			var l = Ladda.create(button);
		    l.start();
			var connectionIds = {};
			var connection;
			for (var i in flowchart.getAllConnections()) {
				connection = flowchart.getAllConnections()[i];
				connectionIds[connection.source.id] = connection.target.id;
			}
			var startProcessName;
			var connectionIdAndName = {};
			var components = [];
			var componentFeatures;
			var paramNameAndParamValue;
			var featureBar;
			var processName;
	    	var processType;
	    	var positionLeft;
	    	var positionTop;
	    	var wsdlUrl;
	    	var operationName;
			$('.view').children('.generated-item').each( function() {
		    	featureBar = $("#" + $(this).attr("id") + "-feature-bar");
		    	paramNameAndParamValue = {};
		    	featureBar.find('.params-container').children().each(function() {
					paramName = $(this).find('.param-name').text();
					paramType = $(this).find('.param-type').text();
					paramValue = $(this).find('.param-input').val();
					paramNameAndParamValue[paramName] = { "paramType" : paramType, "paramValue" : paramValue};
				});
				
		    	processName = featureBar.find('.component-name input').val();
		    	processType = $(this).attr("id").split("-")[0];
		    	positionLeft = $(this).css("left");
		    	positionTop = $(this).css("top");
		    	if (processType == "webservice") {
			    	wsdlUrl = featureBar.find('.wsdl-input').val();
				    paramNameAndParamValue["wsdlUrl"] = { "paramType" : "string", "paramValue" : wsdlUrl};
					operationName = featureBar.find('.ws-opreations option:selected').val();
					paramNameAndParamValue["operationName"] = { "paramType" : "string", "paramValue" : operationName};
				}
		    	componentFeatures = {"processName" : processName, "processType" : processType, "positionLeft" : positionLeft, 
		    						 "positionTop" : positionTop, "params" : paramNameAndParamValue};
		    	components.push(componentFeatures);
		    	connectionIdAndName[$(this).attr("id")] = processName;
		    	if (processType == "begin") {
		    		startProcessName = processName;
				}
			});
			var connections = {};
			for (var sourceId in connectionIds) {
				connections[connectionIdAndName[sourceId]] = connectionIdAndName[connectionIds[sourceId]];
			}
			var viewName = $('#view-input').val();
			var processView = {};
			processView["viewName"] = viewName;
			processView["components"] = components;
			processView["connections"] = connections;
			processView["startProcessName"] = startProcessName;
		    $.ajax({
		        type: "POST",
		        dataType:'json',
		        contentType:"application/json",
		        url: basePath + "/APIGeneration/save",
		        data:JSON.stringify(processView),
		        success: function (data) {
		            l.stop();
		            if (data.status == 1) {
		            	notify('saveProcessSuccessNotification', 'alert-info', data.message, 5000);
		            } else if (data.status < 1) {
		            	notify('saveProcessErrorNotification', 'alert-danger', data.message, 5000);
		            }
		        }
		    });
		}
		
		function loadComponentFeatureBar(elementId, component, basePath) {
			var elementName = component["processType"];
			var featureBar = createNewFeatureBar(elementId, elementName);
			processName = featureBar.find('.component-name input').val(component["processName"]);
			var params;
			var wsRequestParam;
			if (elementName == "webservice") {
				params = component["params"];
				wsRequestParam = params["wsdlUrl"];
				var wsdlUrl = wsRequestParam["paramValue"];
				featureBar.find('.wsdl-input').val(wsdlUrl);
			    $.ajax({
			        type: "POST",
			        url: basePath + "/ws/readWsdl",
			        data: "wsdlUrl=" + wsdlUrl,
			        success: function (data) {
			            if (data.status == 1) {
			            	var combobox = featureBar.find(".ws-opreations");
			            	var operations = data.operations;
			            	var cloneOption = combobox.children().first().clone();
			            	combobox.empty();
			            	combobox.append(cloneOption);
			            	var newOption;
			            	for (var i in operations) {
			            		newOption = cloneOption.clone();
			            		newOption.attr("value", operations[i]);
			            		newOption.text(operations[i]);
			            		combobox.append(newOption);
							}
			            	featureBar.find('.ws-run-button').css("display", "none");
			            	createWsOpSelect(combobox);
			            	wsRequestParam = params["operationName"];
			    			var operationName = wsRequestParam["paramValue"];
			            	combobox.val(operationName);
			            	combobox.parent().find('.custom-combobox-input').val(operationName);
			    			var paramsContainer = featureBar.find('.params-container');
			    		    $.ajax({
			    		        type: "POST",
			    		        url: basePath + "/ws/getOpDetail",
			    		        data: "wsdlUrl=" + wsdlUrl + "&operationName=" + operationName,
			    		        success: function (data) {
			    		            if (data.status == 1) {
			    		            	var tempParamContainer = featureBar.find("#param-name-container");
			    		            	paramsContainer.empty();
			    		            	var inputParams = data.inputParams;
			    		            	var newParam;
			    		            	var paramType;
			    		            	for (var paramName in inputParams) {
			    		            		paramType = inputParams[paramName];
			    							wsRequestParam = params[paramName];
			    		            		if (paramType == "string" || paramType == "int" || paramType == "long" 
			    		            				|| paramType == "float" || paramType == "double" || paramType == "boolean") {
			    			            		newParam = tempParamContainer.clone();
			    			            		newParam.attr("id", paramName + "-container");
			    			            		newParam.find('.param-name').text(paramName);
			    			            		newParam.find('.param-type').text(paramType);
			    			            		newParam.find('.feature-input').attr("id", paramName + "-input");
			    			            		newParam.find('.feature-input').attr("name", paramName);
			    			            		newParam.find('.feature-input').val(wsRequestParam["paramValue"]);
			    			            		paramsContainer.append(newParam);
			    							} else if (paramType == "dateTime") {
			    								newParam = tempParamContainer.clone();
			    			            		newParam.attr("id", paramName + "-container");
			    			            		newParam.find('.param-name').text(paramName);
			    			            		newParam.find('.param-type').text(paramType);
			    			            		newParam.find('.feature-input').attr("id", paramName + "-input");
			    			            		newParam.find('.feature-input').attr("name", paramName);
			    			            		newParam.find('.feature-input').datetimepicker();
			    			            		newParam.find('.feature-input').val(wsRequestParam["paramValue"]);
			    			            		paramsContainer.append(newParam);
			    							} else if (paramType == "date") {
			    								newParam = tempParamContainer.clone();
			    			            		newParam.attr("id", paramName + "-container");
			    			            		newParam.find('.param-name').text(paramName);
			    			            		newParam.find('.param-type').text(paramType);
			    			            		newParam.find('.feature-input').attr("id", paramName + "-input");
			    			            		newParam.find('.feature-input').attr("name", paramName);
			    			            		newParam.find('.feature-input').datepicker("option", "showAnim", "slide");
			    			            		newParam.find('.feature-input').val(wsRequestParam["paramValue"]);
			    			            		paramsContainer.append(newParam);
			    							}
			    						}
			    		            	if(paramsContainer.children().length > 5) {
			    		            		paramsContainer.parent().css("overflow-y", "scroll");
			    		            		paramsContainer.parent().css("overflow-x", "hidden");
			    		            	}
			    		            	featureBar.find(".ws-run-button").css("display", "block");
			    		            } else if (data.status < 1) {
			    		            	notify('getOpDetailErrorNotification', 'alert-danger', data.message, 5000);
			    		            }
			    		        }
			    		    });
			            } else if (data.status < 1) {
			            	notify('readWSDLErrorNotification', 'alert-danger', data.message, 5000);
			            }
			        }
			    });
			}
		}
		
		function loadElementToView(component) {
			var elementName = component["processType"];
			var cloneElement = $("#products").find("li[element-name='" + elementName + "']").find('.list-item-container').clone();
			cloneElement.removeClass("list-item-container").addClass("generated-item");
			cloneElement.find('.list-item-label').remove();
			cloneElement.attr("id", elementName + "-" + newElementId);
			cloneElement.attr("onclick", "openFeatureBar('" + newElementId + "', '" + elementName + "')");
			cloneElement.css("left", component["positionLeft"]);
			cloneElement.css("top", component["positionTop"]);
			$('.view').append(cloneElement);

			if (newElementId == 1) {
				flowchart = drawFlowchart(cloneElement.attr("id"));
			} else {
				flowchart.doWhileSuspended(function() {
                	
                		_addEndpoints(cloneElement.attr("id"), ["TopCenter", "BottomCenter"], ["LeftMiddle", "RightMiddle"]);  
                        
                		flowchart.bind("connection", function(connInfo, originalEvent) { 
                                init(connInfo.connection);
                        });                        
                                                
                		flowchart.draggable(jsPlumb.getSelector("#flowchart-view .generated-item"), { grid: [20, 20] });                

                		flowchart.bind("click", function(conn, originalEvent) {
                                if (confirm("Delete connection from " + conn.sourceId + " to " + conn.targetId + "?"))
                                        jsPlumb.detach(conn); 
                        });        
                        
                		flowchart.bind("connectionDrag", function(connection) {
                                console.log("connection " + connection.id + " is being dragged. suspendedElement is ", connection.suspendedElement, " of type ", connection.suspendedElementType);
                        });                
                        
                		flowchart.bind("connectionDragStop", function(connection) {
                                console.log("connection " + connection.id + " was dragged");
                        });

                		flowchart.bind("connectionMoved", function(params) {
                                console.log("connection " + params.connection.id + " was moved");
                        });
                });
			}
			newElementId++;
			return cloneElement;
		}

		function loadConnections(connections) {
			flowchart.doWhileSuspended(function() {
            	
        		flowchart.bind("connection", function(connInfo, originalEvent) { init(connInfo.connection); });                        
                                        
        		flowchart.draggable(jsPlumb.getSelector("#flowchart-view .generated-item"), { grid: [20, 20] });
        		
        		var targetId;
        		for (var sourceId in connections) {
        			targetId = connections[sourceId];
        			flowchart.connect({uuids:[sourceId + "BottomCenter", targetId + "LeftMiddle"], editable:true});
				}

        		flowchart.bind("click", function(conn, originalEvent) {
                        if (confirm("Delete connection from " + conn.sourceId + " to " + conn.targetId + "?"))
                                jsPlumb.detach(conn); 
                });        
                
        		flowchart.bind("connectionDrag", function(connection) {
                        console.log("connection " + connection.id + " is being dragged. suspendedElement is ", connection.suspendedElement, " of type ", connection.suspendedElementType);
                });                
                
        		flowchart.bind("connectionDragStop", function(connection) {
                        console.log("connection " + connection.id + " was dragged");
                });

        		flowchart.bind("connectionMoved", function(params) {
                        console.log("connection " + params.connection.id + " was moved");
                });
        });
		}

		function onload(processView, basePath) {
			if (processView) {
				$('#view-input').val(processView["viewName"]);
				var components = processView["components"];
				var component;
				var loadedElement;
				var connectionIdAndName = {};
				for ( var i in components) {
					component = components[i];
					loadedElement = loadElementToView(component);
					connectionIdAndName[component["processName"]] = loadedElement.attr("id");
					loadComponentFeatureBar(loadedElement.attr("id").split("-")[1], component, basePath);
				}
				
				var connections = {};
				var processNameConnections = processView["connections"];
				for (var sourceProcessName in processNameConnections) {
					connections[connectionIdAndName[sourceProcessName]] = connectionIdAndName[processNameConnections[sourceProcessName]];
				}
				loadConnections(connections);
			}
		}
	</script>
</body>
</html>