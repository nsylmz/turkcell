var menuItemTable;
$(document).ready(function() {
	$("#menuSelect").combobox();
	$("#pageSelect").combobox();
	$.ajax({
        type: "POST",
        data: "appId=" + appId,
        url:  basePath + "/dev/menu/getMenuItems",
        success: function (data) {
            if (data.status == 1) {
            	menuItemTable = $('#menuItemTable').dataTable({
                    "bProcessing": true,
                    "aaData": data.menuItems,
                    "bJQueryUI": true,
                    "aoColumns": [ 
                      			/* Id */    				{ "bSearchable": false,
									  						  "bSortable" : false,
         			                 						  "bVisible":    false },
                      			/* Menu Item Name */   		null,
                      			/* Menu Id */    			{ "bSearchable": false,
         			                 						  "bSortable" : false,
         			                 						  "bVisible":    false },
                      			/* Menu Name */   			null,
		                      	/* Page Id */   			{ "bSearchable": false,
						            						  "bSortable" : false,
						              						  "bVisible":    false },
                      			/* Page Name */   			null,
                      			]
                });
            	menuItemTable.fnSort([[1,'asc']]);
            	$('#menuItemTable').delegate('tr','click', function() {
                    if ($(this).hasClass('row_selected')) {
                        $("#menuItemName").val("");
                        $("#menuSelect").val("");
                        $("#menuSelect").next().find("input").val("");
                        if ($("#pageSelect")) {
                        	$("#pageSelect").val("");
                        	$("#pageSelect").next().find("input").val("");
						}
                        $("#createMenuItemButtonLabel").html("Add Menu Item");
                        $("#createMenuItemButtonLabel").parent().attr("onclick", "createMenuItem(this)");
                        $("#deleteMenu").attr('disabled', 'disabled');
                        $(this).removeClass('row_selected');
                    } else {
                    	var data = menuItemTable.fnGetData(this);
                    	var aPos = menuItemTable.fnGetPosition(this);
                        $("#menuItemName").val(data[1]);
                        $("#menuSelect");
                        if ($("#menuSelect").find("option[value*=" + data[2] + "]")) {
                        	$("#menuSelect").val(data[2]);
                        	$("#menuSelect").next().find("input").val(data[3]);
                        } else {
                        	notify('menuUnknownNotification', 'alert-danger', "Menu(" + data[3] + ") Is Unkown!!!", 10000);
                        }
                        if ($("#pageSelect") && data[5] != "No Page Is Set") {
                        	if ($("#pageSelect").find("option[value*=" + data[2] + "]")) {
                        		$("#pageSelect").val(data[4]);
                        		$("#pageSelect").next().find("input").val(data[5]);
							} else {
								notify('pageUnknownNotification', 'alert-danger', "Page(" + data[5] + ") Is Unkown!!!", 10000);
							}
                        }
                        $("#createMenuItemButtonLabel").html("Update Menu Item");
                        $("#createMenuItemButtonLabel").parent().attr("onclick", "updateMenuItem(this," + aPos +")");
                    	$("#deleteMenuItem").attr("onclick", "confirmNotify('deleteAnMenuItem', 'Are You Sure Delete This Menu Item?', 'Delete', 'Cancel', 'deleteMenuItem', ['deleteAnMenuItemConfirmButton'])");
                        $("#deleteMenuItem").removeAttr('disabled');
                        menuItemTable.$('tr.row_selected').removeClass('row_selected');
                        $(this).addClass('row_selected');
                    }
                });
            } else if (data.status == -1) {
                notify('menuItemTableErrorNotification', 'alert-danger', data.message, 10000);
            }
        }
    });
});

function createMenuItem(button, aPos) {
	var l = Ladda.create(button);
    l.start();
    var menuItem = {};
    menuItem["menuId"] = 0;
    menuItem["menuItemName"] = $("#menuItemName").val();
    if (!menuItem["menuItemName"]) {
    	notify('warnNoDataEntredNotification', 'alert-info', 'No Values Entred!!!', 10000);
	} else if (!($('#menuSelect') && $('#menuSelect').val())) {
		notify('warnNoMenuDefinedNotification', 'alert-info', 'Please Define A Menu And Go To Menu Tab!!!', 10000);
	} else {
		var l = Ladda.create(button);
	    l.start();
	    menuItem["menuId"] = $('#menuSelect').val();
	    var menuName = $('#menuSelect').text().trim();
	    var menuItemPageName;
	    if ($('#pageSelect') && $('#pageSelect').val()) {
	    	menuItem["menuItemPageId"] = $('#pageSelect').val();
	    	menuItemPageName  = $('#pageSelect').text().trim();
		} else {
			menuItemPageName = "No Page Is Set";
			menuItem["menuItemPageId"] = null;
		}
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(menuItem),
	        url:  basePath + "/dev/menu/createMenuItem",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	menuItemTable.fnAddData(
	            				[data.id,
	            				 menuItem["menuItemName"],
	            				 menuItem["menuId"],
	            				 menuName,
	            				 menuItem["menuItemPageId"],
	            				 menuItemPageName
	            				 ]);
	            	$("#menuItemName").val("");
	            	$("#menuSelect").val("");
	            	$("#menuSelect").next().find("input").val("");
	            	if ($("#pageSelect")) {
	            		$("#pageSelect").val("");
	            		$("#pageSelect").next().find("input").val("");
					}
	            	$("#createMenuItemButtonLabel").html("Add Menu Item");
                    $("#createMenuItemButtonLabel").parent().attr("onclick", "createMenuItem(this)");
	            	notify('addMenuItemSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('addMenuItemErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function deleteMenuItem(button) {
	var l = Ladda.create($("#" + button)[0]);
    l.start();
    var selectedRow = fnGetSelected(menuItemTable);
    var rowData = menuItemTable.fnGetData(selectedRow);
    var rowNum = menuItemTable.fnGetPosition(selectedRow);
	if (rowData != null && rowData[0] != null && rowData[0] > 0) {
		var l = Ladda.create(button);
	    l.start();
		$.ajax({
	        type: "POST",
	        data: "menuItemId=" + rowData[0],
	        url: basePath + "/dev/menu/deleteMenuItem",
	        success: function (data) {
	        	l.stop();
	            if (data.status == 1) {
	            	$("#menuItemName").val("");
	            	$("#menuSelect").val("");
	            	$("#menuSelect").next().find("input").val("");
	            	if ($("#pageSelect")) {
	            		$("#pageSelect").val("");
	            		$("#pageSelect").next().find("input").val("");
					}
	            	$("#createMenuItemButtonLabel").html("Add Menu Item");
                    $("#createMenuItemButtonLabel").parent().attr("onclick", "createMenuItem(this)");
	            	menuItemTable.fnDeleteRow(rowNum);
	            	notify('deleteMenuItemSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('deleteMenuItemErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function updateMenuItem(button, aPos) {
    var rowData = menuItemTable.fnGetData(aPos);
    var menuItem = {};
    menuItem["menuItemId"] = rowData[0];
    menuItem["menuItemName"] = $("#menuItemName").val();
    if (rowData[1] == menuItem["menuItemName"]) {
    	notify('warnNoDataChangesNotification', 'alert-info', 'No Values Changed!!!', 10000);
	} else if (!($('#menuSelect') && $('#menuSelect').val())) {
		notify('warnNoMenuDefinedNotification', 'alert-info', 'Please Define A Menu And Go To Menu Tab!!!', 10000);
	} else {
		var l = Ladda.create(button);
	    l.start();
		menuItem["menuId"] = $('#menuSelect').val();
	    var menuName = $('#menuSelect').text().trim();
	    var menuItemPageName;
	    if ($('#pageSelect') && $('#pageSelect').val()) {
	    	menuItem["menuItemPageId"] = $('#pageSelect').val();
	    	menuItemPageName  = $('#pageSelect').text().trim();
		} else {
			menuItemPageName = "No Page Is Set";
			menuItem["menuItemPageId"] = null;
		}
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(menuItem),
	        url:  basePath + "/dev/menu/updateMenuItem",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	menuItemTable.fnUpdate(
	            				[menuItem["menuItemId"],
	            				 menuItem["menuItemName"],
	            				 menuItem["menuId"],
	            				 menuName,
	            				 menuItem["menuItemPageId"],
	            				 menuItemPageName], aPos);
	            	notify('updateMovieSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('updateMovieErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}