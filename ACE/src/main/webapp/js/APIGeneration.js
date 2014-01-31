var flowchart;
var newElementId = 1;
function APIGeneration() {
	$("#catalog").accordion({ heightStyle: "content" });
	$("#catalog li").draggable({
		appendTo : "body",
		helper : "clone"
	});
	$(".view").droppable({
		activeClass : "ui-state-default",
		hoverClass : "ui-state-hover",
		accept : "li",
		drop : function(event, ui) {
			var cloneElement = ui.draggable.find('.list-item-container').clone();
			var elementName = ui.draggable.attr('element-name');
			cloneElement.removeClass("list-item-container").addClass("generated-item");
			cloneElement.find('.list-item-label').remove();
			cloneElement.attr("id", elementName + "-" + newElementId);
			cloneElement.attr("onclick", "openFeatureBar('" + newElementId + "', '" + elementName + "')");
			$(this).append(cloneElement);

			if (newElementId == 1) {
				flowchart = drawFlowchart(cloneElement.attr("id"));
			} else {
				addElementToFlowChart(cloneElement);
			}
			newElementId++;
		}
	});
}

function addElementToFlowChart(newElement) {
	flowchart.doWhileSuspended(function() {
    	
		_addEndpoints(newElement.attr("id"), ["TopCenter", "BottomCenter"], ["LeftMiddle", "RightMiddle"]);  
        
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

function createNewFeatureBar(elementId, elementName) {
	var cloneFeatureBar;
	var tempFeatureBar;
	tempFeatureBar = $('#' + elementName + '-feature-bar');
	cloneFeatureBar = tempFeatureBar.clone();
	cloneFeatureBar.attr("id", elementName + '-' + elementId + '-feature-bar');
	cloneFeatureBar.find(".close").attr("onclick", "closeFeatureBar('" + elementId + "', '" + elementName + "')");
	cloneFeatureBar.find(".processes").combobox();
	cloneFeatureBar.find(".delete-button-container button").attr("onclick", "confirmNotify('deneme', 'Are You Sure About Deleting The Component?', 'Ok', 'Cancel', 'deleteComponent', ['" + elementName + "-" + elementId + "-feature-bar'])");
	tempFeatureBar.parent().append(cloneFeatureBar);
	
	cloneFeatureBar.find(".feature-tabs").tabs();
	cloneFeatureBar.find('#WebService-feature').scroll();
	return cloneFeatureBar;
}

function openFeatureBar(elementId, elementName) {
	$('.feature-container').find(".feature-bar:visible").hide();
	var	featureBar = $('#' + elementName + '-' + elementId + '-feature-bar');
	if (featureBar.length == 0) {
		featureBar = createNewFeatureBar(elementId, elementName);
	}
	if (featureBar.hasClass("display-none")) {
		featureBar.removeClass("display-none");
	} else {
		featureBar.show();
	}
}

function closeFeatureBar(elementId, elementName) {
	var	featureBar = $('#' + elementName + '-' + elementId + '-feature-bar');
	if (featureBar) {
		featureBar.hide();
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
	var processView = {};
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
    		processView["startProcessName"] = processName;
		}
	});
	var connections = {};
	for (var sourceId in connectionIds) {
		connections[connectionIdAndName[sourceId]] = connectionIdAndName[connectionIds[sourceId]];
	}
	var viewName = $('#view-input').val();
	processView["viewName"] = viewName;
	processView["components"] = components;
	processView["connections"] = connections;
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
		addElementToFlowChart(cloneElement);
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

function escapeRegExp(str) {
	return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
}

function replaceAll(find, replace, str) {
	return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}

function notify(notificationId, alertType, notificationText, showTime) {
	if ($("#" + notificationId).length == 0) {
		var newNotification = $("#temp-notification").clone();
		newNotification.attr("id", notificationId);
		newNotification.css("display", "block");
		newNotification.addClass(alertType);
		newNotification.append(notificationText);
		newNotification.find("button").attr("onclick", "closeNotification('" + notificationId + "')");
		newNotification.appendTo(".notification-container");
		setTimeout(function () { closeNotification(notificationId); }, showTime);
	}
}

function closeNotification(notificationId) {
	var notification = $("#" + notificationId);
	notification.remove();
}

function confirmNotify(notificationId, notificationText, confirmText, cancelText, confirmMethodName, confirmMethodParams) {
	if ($("#" + notificationId).length == 0) {
		var newNotification = $('#temp-confirm').clone();
		newNotification.attr("id", notificationId);
		newNotification.css("display", "block");
		newNotification.addClass("alert-info");
		newNotification.find(".confirm-text-container").append(notificationText);
		newNotification.find(".confirm-button span").text(confirmText);
		newNotification.find(".cancel-button span").text(cancelText);
		newNotification.find(".cancel-button").attr("onclick", "closeConfirmNotification('" + notificationId + "')");
		var confirmOnclik = confirmMethodName + "(";
		for (var i in confirmMethodParams) {
			if (i == 0 && i == confirmMethodParams.length-1) {
				confirmOnclik = confirmOnclik + "'" + confirmMethodParams[i] + "'";
			} else if (i == 0) {
				confirmOnclik = confirmOnclik + "'" + confirmMethodParams[i] + "', '";
			} else if (i != confirmMethodParams.length-1) {
				confirmOnclik = confirmOnclik + confirmMethodParams[i] + "', '";
			} else {
				confirmOnclik = confirmOnclik + confirmMethodParams[i] + "'";
			}
		}
		confirmOnclik = confirmOnclik + ");" + "closeConfirmNotification('" + notificationId + "')";
		newNotification.find(".confirm-button").attr("onclick", confirmOnclik);
		newNotification.appendTo(".notification-container");
		
		var modalFadeDiv = $(document.createElement("div"));
		modalFadeDiv.addClass("modal-backdrop");
		modalFadeDiv.addClass("fade");
		modalFadeDiv.addClass("in");
		$('body').append(modalFadeDiv);
	}
}

function closeConfirmNotification(notificationId) {
	closeNotification(notificationId);
	$(".modal-backdrop.fade.in").remove();
}

function getURLParameter(name) {
    return decodeURI(
        (new RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]);
}