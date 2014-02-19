var entityTable;
$(document).ready(function() {
	$.ajax({
        type: "POST",
        data: "appId=" + appId,
        url:  basePath + "/dev/entity/getEntities",
        success: function (data) {
            if (data.status == 1) {
            	entityTable = $('#entityTable').dataTable({
                    "bProcessing": true,
                    "aaData": data.entities,
                    "bJQueryUI": true,
                    "aoColumns": [ 
                      			/* Id */    				{ "bSearchable": false,
									  						  "bSortable" : false,
         			                 						  "bVisible":    false },
                      			/* Name */   	null
                      			]
                });
            	entityTable.fnSort([[1,'asc']]);
            	$('#entityTable').delegate('tr','click', function() {
                    if ($(this).hasClass('row_selected')) {
                        $("#entityName").val("");
                        $("#createEntityButtonLabel").html("Add Entity");
                        $("#createEntityButtonLabel").parent().attr("onclick", "createEntity(this)");
                        $("#deleteEntity").attr('disabled', 'disabled');
                        $(this).removeClass('row_selected');
                    } else {
                    	var data = entityTable.fnGetData(this);
                    	var aPos = entityTable.fnGetPosition(this);
                        $("#entityName").val(data[1]);
                        $("#createEntityButtonLabel").html("Update Entity");
                        $("#createEntityButtonLabel").parent().attr("onclick", "updateEntity(this," + aPos +")");
                    	$("#deleteEntity").attr("onclick", "confirmNotify('deleteAnEntity', 'Are You Sure Delete This Entity?', 'Delete', 'Cancel', 'deleteEntity', ['deleteAnEntityConfirmButton'])");
                        $("#deleteEntity").removeAttr('disabled');
                        entityTable.$('tr.row_selected').removeClass('row_selected');
                        $(this).addClass('row_selected');
                    }
                });
            } else if (data.status == -1) {
                notify('entityTableErrorNotification', 'alert-danger', data.message, 10000);
            }
        }
    });
});

function createEntity(button, aPos) {
	var l = Ladda.create(button);
    l.start();
    var appEntity = {};
    appEntity["appId"] = appId;
    appEntity["entityName"] = $("#entityName").val();
    if (!appEntity["entityName"]) {
    	notify('warnNoDataEntredNotification', 'alert-info', 'No Values Entred!!!', 10000);
	} else {
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(appEntity),
	        url:  basePath + "/dev/entity/createEntity",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	entityTable.fnAddData(
	            				[data.id,
	            				 appEntity["entityName"]]);
	            	$("#entityName").val("");
	            	$("#createEntityButtonLabel").html("Add Entity");
                    $("#createEntityButtonLabel").parent().attr("onclick", "createEntity(this)");
	            	notify('addMenuSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('addMenuErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function deleteEntity(button) {
	var l = Ladda.create($("#" + button)[0]);
    l.start();
    var selectedRow = fnGetSelected(entityTable);
    var rowData = entityTable.fnGetData(selectedRow);
    var rowNum = entityTable.fnGetPosition(selectedRow);
	if (rowData != null && rowData[0] != null && rowData[0] > 0) {
		$.ajax({
	        type: "POST",
	        data: "entityId=" + rowData[0],
	        url: basePath + "/dev/entity/deleteEntity",
	        success: function (data) {
	        	l.stop();
	            if (data.status == 1) {
	            	$("#entityName").val("");
	            	$("#createEntityButtonLabel").html("Add Entity");
                    $("#createEntityButtonLabel").parent().attr("onclick", "createEntity(this)");
	            	entityTable.fnDeleteRow(rowNum);
	            	notify('deleteEntitySuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('deleteEntityErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function updateEntity(button, aPos) {
	var l = Ladda.create(button);
    l.start();
    var rowData = entityTable.fnGetData(aPos);
    var entity = {};
    entity["entityId"] = rowData[0];
    entity["entityName"] = $("#entityName").val();
    if (rowData[1] == entity["entityName"]) {
    	notify('warnNoDataChangesNotification', 'alert-info', 'No Values Changed!!!', 10000);
	} else {
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(entity),
	        url:  basePath + "/dev/entity/updateEntity",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	entityTable.fnUpdate(
	            				[entity["entityId"],
	            				 entity["entityName"]], aPos);
	            	notify('updateEntitySuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('updateEntityErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}