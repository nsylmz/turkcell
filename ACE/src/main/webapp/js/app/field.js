var fieldTable;
$(document).ready(function() {
	$("#fieldEntitySelect").combobox();
	$("#fieldTypeSelect").combobox();
	$("#fieldEntityTypeSelect").combobox();
	$.ajax({
        type: "POST",
        data: "appId=" + appId,
        url:  basePath + "/dev/field/getFields",
        success: function (data) {
            if (data.status == 1) {
            	fieldTable = $('#fieldTable').dataTable({
                    "bProcessing": true,
                    "aaData": data.fields,
                    "bJQueryUI": true,
                    "aoColumns": [ 
                      			/* Id */    				{ "bSearchable": false,
									  						  "bSortable" : false,
         			                 						  "bVisible":    false },
                      			/* Field Name */   			null,
                      			/* Field Type */   			null,
                      			/* Indexed */   			null,
                      			/* Entity Id */    			{ "bSearchable": false,
									  						  "bSortable" : false,
						            						  "bVisible":    false },
                      			/* Entity Name */   		null,
                      			]
                });
            	fieldTable.fnSort([[1,'asc']]);
            	$('#fieldTable').delegate('tr','click', function() {
                    if ($(this).hasClass('row_selected')) {
                        $("#fieldName").val("");
                        $("#createFieldButtonLabel").html("Add Field");
                        $("#createFieldButtonLabel").parent().attr("onclick", "createField(this)");
                        $("#deleteField").attr('disabled', 'disabled');
                        $(this).removeClass('row_selected');
                    } else {
                    	var data = fieldTable.fnGetData(this);
                    	var aPos = fieldTable.fnGetPosition(this);
                        $("#fieldName").val(data[1]);
                        $("#createFieldButtonLabel").html("Update Field");
                        $("#createFieldButtonLabel").parent().attr("onclick", "updateField(this," + aPos +")");
                    	$("#deleteField").attr("onclick", "confirmNotify('deleteAnField', 'Are You Sure Delete This Field?', 'Delete', 'Cancel', 'deleteField', ['deleteAnFieldConfirmButton'])");
                        $("#deleteField").removeAttr('disabled');
                        fieldTable.$('tr.row_selected').removeClass('row_selected');
                        $(this).addClass('row_selected');
                    }
                });
            } else if (data.status == -1) {
                notify('fieldTableErrorNotification', 'alert-danger', data.message, 10000);
            }
        }
    });
});


function createField(button, aPos) {
    var field = {};
    field["fieldId"] = 0;
    field["fieldName"] = $("#fieldName").val();
    if (!field["fieldName"]) {
    	notify('warnNoDataEntredNotification', 'alert-info', 'No Values Entred!!!', 10000);
	} else if (!$('#fieldEntitySelect').val()) {
		notify('warnNoEntitySelectedNotification', 'alert-info', 'Please Select An Entity!!!', 10000);
	} else if (!$('#fieldTypeSelect').val() && !$('#fieldEntityTypeSelect').val()) {
		notify('warnNoTypeSelectedNotification', 'alert-info', 'Please Select A Field Type Or Field Entity Type!!!', 10000);
	} else {
		var l = Ladda.create(button);
	    l.start();
	    field["entityId"] = $('#fieldEntitySelect').val();
	    field["entityName"] = $('#fieldEntitySelect').text().trim();
	    field["indexed"] = $('#indexedField').prop('checked');
	    if ($('#fieldTypeSelect').val()) {
	    	field["fieldType"] = $('#fieldTypeSelect').val();
		} else if ($('#fieldEntityTypeSelect').val()) {
			field["fieldType"] = $('#fieldEntityTypeSelect').val();
		}
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(field),
	        url:  basePath + "/dev/field/createField",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	fieldTable.fnAddData(
	            				[data.id,
	            				 field["fieldName"],
	            				 field["fieldType"],
	            				 field["indexed"],
	            				 field["entityId"],
	            				 field["entityName"]
	            				 ]);
	            	$("#fieldName").val("");
	            	$("#createFieldButtonLabel").html("Add Field");
                    $("#createFieldButtonLabel").parent().attr("onclick", "createField(this)");
	            	notify('addFieldSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('addFieldErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function deleteField(button) {
    var selectedRow = fnGetSelected(fieldTable);
    var rowData = fieldTable.fnGetData(selectedRow);
    var rowNum = fieldTable.fnGetPosition(selectedRow);
	if (rowData != null && rowData[0] != null && rowData[0] > 0) {
		var l = Ladda.create($("#" + button)[0]);
	    l.start();
		$.ajax({
	        type: "POST",
	        data: "fieldId=" + rowData[0],
	        url: basePath + "/dev/field/deleteField",
	        success: function (data) {
	        	l.stop();
	            if (data.status == 1) {
	            	$("#fieldName").val("");
	            	$("#createFieldButtonLabel").html("Add Field");
                    $("#createFieldButtonLabel").parent().attr("onclick", "createField(this)");
	            	fieldTable.fnDeleteRow(rowNum);
	            	notify('deleteFieldSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('deleteFieldErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function updateField(button, aPos) {
    var rowData = fieldTable.fnGetData(aPos);
    var field = {};
    field["fieldId"] = rowData[0];
    field["fieldName"] = $("#fieldName").val();
    if (rowData[1] == field["fieldName"]) {
    	notify('warnNoDataChangesNotification', 'alert-info', 'No Values Changed!!!', 10000);
	} else if (!$('#fieldEntitySelect').val()) {
		notify('warnNoEntitySelectedNotification', 'alert-info', 'Please Select An Entity!!!', 10000);
	} else if (!$('#fieldTypeSelect').val() && !$('#fieldEntityTypeSelect').val()) {
		notify('warnNoTypeSelectedNotification', 'alert-info', 'Please Select A Field Type Or Field Entity Type!!!', 10000);
	} else {
		var l = Ladda.create(button);
	    l.start();
		field["entityId"] = $('#fieldEntitySelect').val();
	    field["entityName"] = $('#fieldEntitySelect').text().trim();
	    field["indexed"] = $('#indexedField').val();
	    if ($('#fieldTypeSelect').val()) {
	    	field["fieldType"] = $('#fieldTypeSelect').val();
		} else if ($('#fieldEntityTypeSelect').val()) {
			field["fieldType"] = $('#fieldEntityTypeSelect').val();
		}
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(field),
	        url:  basePath + "/dev/field/updateField",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	fieldTable.fnUpdate(
	            				[field["fieldId"],
	            				 field["fieldName"],
	            				 field["fieldType"],
	            				 field["indexed"],
	            				 field["entityId"],
	            				 field["entityName"]], aPos);
	            	notify('updateMovieSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('updateMovieErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}