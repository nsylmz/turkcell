function UIGeneration() {
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