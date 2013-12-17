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
			var elementName = ui.draggable.attr('element-name');
			var elementType = ui.draggable.attr('element-type');
			var newElement = document.createElement(elementName);
			newElement.setAttribute("id", newElementId);
			if (elementType != null) {
				newElement.setAttribute("type", elementType);
			}
			newElement.setAttribute("onclick", "openFeatureBar('" + newElementId + "')");
			newElement.innerHTML = ui.draggable.text();
			this.appendChild(newElement);
			$(newElement).draggable({
				containment : "parent",
				cancel : false
			});
			newElementId++;
		}
	});
}

function openFeatureBar(elementId) {
	if ($('.componentFeature').css("display") == "block") {
		closeFeatureBar();
	}
	$('.componentFeature').css("display", "block");
}

function closeFeatureBar() {
	$('.componentFeature').css("display", "none");
}


function escapeRegExp(str) {
	return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
}

function replaceAll(find, replace, str) {
	return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}