var newElementId = 1;
function UIGeneration() {
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
			var cloneElement = ui.draggable.find(".temp-component").clone();
			var elementName = ui.draggable.attr('element-name');
			var elementType = ui.draggable.attr('element-type');
			cloneElement.removeClass("temp-component");
			if (elementType) {
				cloneElement.attr("id", elementName + "-" + elementType + "-" + newElementId);
				cloneElement.attr("onclick", "openFeatureBar('" + newElementId + "', '" + elementName  + "', '" + elementType + "')");
			} else {
				cloneElement.attr("id", elementName + "-" + newElementId);
				cloneElement.attr("onclick", "openFeatureBar('" + newElementId + "', '" + elementName  + "', " + elementType + ")");
			}
			cloneElement.draggable({
				containment : "parent",
				cancel : false
			});
			$(this).append(cloneElement);
			newElementId++;
		}
	});
}

function createNewFeatureBar(elementId, elementName, elementType) {
	var cloneFeatureBar;
	var tempFeatureBar;
	if (elementType) {
		tempFeatureBar = $('#' + elementName + '-' + elementType + '-feature-bar');
		cloneFeatureBar = tempFeatureBar.clone();
		cloneFeatureBar.attr("id", elementName + '-' + elementType + '-' + elementId + '-feature-bar');
		cloneFeatureBar.find(".close").attr("onclick", "closeFeatureBar('" + elementId + "', '" + elementName + "', '" + elementType + "')");
	} else {
		tempFeatureBar = $('#' + elementName + '-feature-bar');
		cloneFeatureBar = tempFeatureBar.clone();
		cloneFeatureBar.attr("id", elementName + '-' + elementId + '-feature-bar');
		cloneFeatureBar.find(".close").attr("onclick", "closeFeatureBar('" + elementId + "', '" + elementName + "', " + elementType + ")");
	}
	cloneFeatureBar.find(".processes").combobox();
	tempFeatureBar.parent().append(cloneFeatureBar);
	return cloneFeatureBar;
}

function openFeatureBar(elementId, elementName, elementType) {
	$('.feature-container').find(".feature-bar:visible").hide();
	var featureBar;
	if (elementType) {
		featureBar = $('#' + elementName + '-' + elementType + '-' + elementId + '-feature-bar');
	} else {
		featureBar = $('#' + elementName + '-' + elementId + '-feature-bar');
	}
	if (featureBar.length == 0) {
		featureBar = createNewFeatureBar(elementId, elementName, elementType);
	}
	if (featureBar.hasClass("display-none")) {
		featureBar.removeClass("display-none");
	} else {
		featureBar.show();
	}
}

function closeFeatureBar(elementId, elementName, elementType) {
	var featureBar;
	if (elementType) {
		featureBar = $('#' + elementName + '-' + elementType + '-' + elementId + '-feature-bar');
	} else {
		featureBar = $('#' + elementName + '-' + elementId + '-feature-bar');
	}
	if (featureBar) {
		featureBar.hide();
	}
}


function escapeRegExp(str) {
	return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
}

function replaceAll(find, replace, str) {
	return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}

function notify(notificationId, alertType, notificationText, showTime) {
	if ($('#' + notificationId).length == 0) {
		var newNotification = $('.appStore-notification').clone();
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

function openViewModal(basePath) {
	$('#openViewModal').modal('toggle');
}

function goToUIView(button, basePath) {
	var viewName = $(button).parent().find('.ui-selected').text();
	if (viewName) {
		window.location.href = basePath + "/UIGeneration/" + viewName;
	} else {
		notify('noViewSelectedErrorNotification', 'alert-info', 'No UIView Selected!!! Please Select UI View.', 5000);
	}
}

function changeButtonLabel(input) {
	var uiComponentId = $(input).parent().parent().parent().parent().parent().parent().parent().attr("id").replace("-feature-bar", "");
	$('#' + uiComponentId).find('span').text($(input).val());
}

function openSaveBar() {
	$(".view-bar").css("left", "-165px");
	$(".save-bar").css("left", "0px");
}

function closeSaveBar() {
	$(".save-bar").css("left", "-255px");
	$(".view-bar").css("left", "0px");
}

function runProcess(button, basePath) {
	var l = Ladda.create(button);
	l.start();
	var processName = $(button).parent().find('.processes').find(
			'option:selected').val();
	$.ajax({
		type : "POST",
		data : "processName=" + processName,
		url : basePath + "/UIGeneration/runProcess",
		success : function(data) {
			l.stop();
			if (data.status == 1) {
				notify('runProcessSuccessNotification', 'alert-info', data.message, 5000);
			} else if (data.status < 1) {
				notify('runProcessErrorNotification', 'alert-danger', data.message, 5000);
			}
		}
	});
}

function loadElementToView(component) {
	var tempElement;
	if (component["elementType"]) {
		tempElement = $("#products").find("li[element-name='" + component["elementName"] + "'][element-type='" + component["elementType"] + "']").find(".temp-component");
	} else {
		tempElement = $("#products").find("li[element-name='" + component["elementName"] + "']").find(".temp-component");
	}
	var cloneElement = tempElement.clone();
	cloneElement.removeClass("temp-component");
	if (component["elementType"]) {
		cloneElement.attr("id", component["elementName"] + "-" + component["elementType"] + "-" + newElementId);
		cloneElement.attr("onclick", "openFeatureBar('" + newElementId + "', '" + component["elementName"]  + "', '" + component["elementType"] + "')");
	} else {
		cloneElement.attr("id", component["elementName"] + "-" + newElementId);
		cloneElement.attr("onclick", "openFeatureBar('" + newElementId + "', '" + component["elementName"]  + "', " + component["elementType"] + ")");
	}
	cloneElement.css("position", "relative");
	cloneElement.css("left", component["positionLeft"]);
	cloneElement.css("top", component["positionTop"]);
	cloneElement.find('span').text(component["componentLabel"]);
	cloneElement.draggable({
		containment : "parent",
		cancel : false
	});
	$('.view').append(cloneElement);
	newElementId++;
	return cloneElement;
}

function loadComponentFeatureBar(elementId, component) {
	var featureBar = createNewFeatureBar(elementId, component["elementName"], component["elementType"]);
	featureBar.find('.component-name').val(component["componentName"]);
	featureBar.find('.component-label').val(component["componentLabel"]);
	featureBar.find('.processes').val(component["componentProcessName"]);
	featureBar.find('.custom-combobox-input').val(component["componentProcessName"]);
}

function onload(uiView) {
	if (uiView) {
		$('#view-input').val(uiView["viewName"]);
		var components = uiView["components"];
		var component;
		var loadedElement;
		for ( var i in components) {
			component = components[i];
			loadedElement = loadElementToView(component);
			loadComponentFeatureBar(
					loadedElement.attr("id").split("-")[1], component);
		}
	}
}

function saveView(button, basePath) {
	var l = Ladda.create(button);
	l.start();
	var componentFeatures;
	var components = [];
	$('.view').children().each(
			function() {
				var elementName = $(this).attr("element-name");
				var elementType = $(this).attr("element-type");
				var positionLeft = $(this).css("left");
				var positionTop = $(this).css("top");
				var featureBar = $("#" + $(this).attr("id") + "-feature-bar");
				var componentName = featureBar.find('.component-name').val();
				var componentLabel = featureBar.find('.component-label').val();
				var componentProcessName = featureBar.find('option:selected').val();
				componentFeatures = {
					"componentName" : componentName,
					"componentLabel" : componentLabel,
					"positionLeft" : positionLeft,
					"positionTop" : positionTop,
					"componentProcessName" : componentProcessName,
					"elementName" : elementName,
					"elementType" : elementType
				};
				components.push(componentFeatures);
			});
	var viewName = $('#view-input').val();
	var uiView = { "viewName" : viewName, "components" : components };
	$.ajax({
		type : "POST",
		dataType : 'json',
		contentType : "application/json",
		url : basePath + "/UIGeneration/saveView",
		data : JSON.stringify(uiView),
		success : function(data) {
			l.stop();
			if (data.status == 1) {
				notify('runProcessSuccessNotification', 'alert-info', data.message, 5000);
			} else if (data.status < 1) {
				notify('runProcessErrorNotification', 'alert-danger', data.message, 5000);
			}
		}
	});
}