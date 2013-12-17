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
			cloneElement.removeClass("list-item-container").addClass("generated-item");
			cloneElement.find('.list-item-label').remove();
			//cloneElement.width(60 + ui.draggable.find('.list-item-label').width());
			//cloneElement.find('.list-item-label').removeClass("list-item-label").addClass("generated-item-label");
			cloneElement.attr("id", "flowchart" + newElementId);
			cloneElement.attr("onclick", "openFeatureBar('#flowchart" + newElementId + "')");
			$(this).append(cloneElement);

			if (newElementId == 1) {
				flowchart = drawFlowchart(newElementId);
			} else {
				flowchart.doWhileSuspended(function() {
                	
                		_addEndpoints(newElementId, ["TopCenter", "BottomCenter",], ["LeftMiddle", "RightMiddle"]);  
                        
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