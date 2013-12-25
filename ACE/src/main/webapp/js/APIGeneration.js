var flowchart;
function APIGeneration() {
	$("#catalog").accordion({ heightStyle: "content" });
	$("#catalog li").draggable({
		appendTo : "body",
		helper : "clone"
	});
	var newElementId = 1;
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
		}
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
/*
var displayElementName;

function openFeatureBar(elementName) {
	if ($('#' + elementName).css("display") == "block") {
		closeFeatureBar();
	}
	$('#' + elementName).css("display", "block");
	displayElementName = elementName;
}

function closeFeatureBar() {
	$('#' + displayElementName).css("display", "none");
}
*/

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