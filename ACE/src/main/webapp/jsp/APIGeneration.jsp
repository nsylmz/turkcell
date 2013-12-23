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
	<link href="${pageContext.request.contextPath}/css/Components.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/flowchart.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/featureBar.css" rel="stylesheet" type="text/css" />
</head>

<body>
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
							<a class="navbar-brand" href="${pageContext.request.contextPath}/UIGeneration">Application Creation Enviroment</a>
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
	        <div class="view-bar-item item-last" onclick="">
	        	<img class="view-item-img" title="Open View" alt="Open View" src="${pageContext.request.contextPath}/img/open-icon.png">
		        <label>Open Process</label>
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
						<li class="list-group-item" element-name="Begin">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/start.png">
								<div class="list-item-label">Begin</div>
							</div>
						</li>
						<li class="list-group-item" element-name="End">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/end.png">
								<div class="list-item-label">End</div>
							</div>
						</li>
						<li class="list-group-item" element-name="If">
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
						<li class="list-group-item" element-name="WebService">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/web-service.png">
								<div class="list-item-label">Web Service</div>
							</div>
						</li>
						<li class="list-group-item" element-name="RestService">
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
						<li class="list-group-item" element-name="JavaMethod">
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
		
		<div id="WebService" class="feature-bar">
			<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
			<div id="tabs" class="feature-tabs">
				<ul>
					<li><a href="#feature-info">Component Info</a></li>
					<li><a href="#WebService-feature">WebService</a></li>
				</ul>
				<div id="feature-info">
					<div class="component-info">
						<div class="component-name">
							<p>Component Name</p>
							<input class="feature-input" type="text" name="componentName">
						</div>
					</div>
				</div>
				<div id="WebService-feature">
					<div class="component-feature-container">
						<div class="component-feature">
							<label>Wsdl URL</label>
							<input id="wsdl-input" class="feature-input" type="text" name="wsdlUrl">
							<button id="read-wsdl-button" class="ace-button btn btn-primary ladda-button" data-style="expand-right"
	                                onclick="readWsdlAndGetOperationName(this, '${pageContext.request.contextPath}')">
	                            <span class="ladda-label">Read WSDL</span>
	                        </button>
							<div id="ws-select" class="ui-widget">
								<label>Operations </label>
								<select id="select-ws-opreations">
									<option value="">Select Operation...</option>
								</select>
								<button id="get-operation-button" class="ace-button btn btn-primary ladda-button" data-style="expand-right"
	                                onclick="getOpDetail(this, '${pageContext.request.contextPath}')">
		                            <span class="ladda-label">Get Operation Detail</span>
		                        </button>
							</div>
							<button id="ws-run-button" class="ace-button btn btn-primary ladda-button" data-style="expand-right"
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
		$("#tabs").tabs();
		$('#WebService-feature').scroll();
		function readWsdlAndGetOperationName(button, basePath) {
			var wsdlUrl = $(button).parent().find('.feature-input').val();
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
		            	var combobox = $("#select-ws-opreations");
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
		            	$('#ws-run-button').css("display", "none");
		            	createWsOpSelect();
		            } else if (data.status < 1) {
		            	notify('readWSDLErrorNotification', 'alert-danger', data.message, 5000);
		            }
		        }
		    });
		}
		
		function getOpDetail(button, basePath) {
			var wsdlUrl = $('#wsdl-input').val();
			var operationName = $(button).parent().find('option:selected').val();
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
		            	var tempParamContainer = $("#param-name-container");
		            	var paramsContainer = $('.params-container');
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
		            	if($('.params-container').children().length > 5) {
		            		paramsContainer.parent().css("overflow-y", "scroll");
		            		paramsContainer.parent().css("overflow-x", "hidden");
		            	}
		            	$('#ws-run-button').css("display", "block");
		            } else if (data.status < 1) {
		            	notify('getOpDetailErrorNotification', 'alert-danger', data.message, 5000);
		            }
		        }
		    });
		}
		
		function runWS(button, basePath) {
		    var l = Ladda.create(button);
		    l.start();
			var wsdlUrl = $('#wsdl-input').val();
			var operationName = $('#select-ws-opreations').find('option:selected').val();
			var wsRunParams = {"wsdlUrl" : wsdlUrl, "operation" : operationName, "paramNameAndparamValue" : null};
			var paramNameAndparamValue = {};
			var param;
			var paramName;
			var paramType;
			var paramValue;
			$('.params-container').children().each(function() {
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
		            	notify('runWsSuccessNotification', 'alert-info', data.message, 5000);
		            } else if (data.status < 1) {
		            	notify('runWsErrorNotification', 'alert-danger', data.message, 5000);
		            }
		        }
		    });
		}
		
		function createWsOpSelect() {
		    var combobox = $("#select-ws-opreations");
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
			
		}
			
	</script>
</body>
</html>