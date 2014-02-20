var menuTable;
var menuItems;
var menuItemTwinSelect;
$(document).ready(function() {
	$.ajax({
        type: "POST",
        data: "appId=" + appId,
        url:  basePath + "/dev/menu/getMenus",
        success: function (data) {
            if (data.status == 1) {
            	menuTable = $('#menuTable').dataTable({
                    "bProcessing": true,
                    "aaData": data.menus,
                    "bJQueryUI": true,
                    "aoColumns": [ 
                      			/* Id */    				{ "bSearchable": false,
									  						  "bSortable" : false,
         			                 						  "bVisible":    false },
                      			/* Name */   	null
                      			]
                });
            	menuTable.fnSort([[1,'asc']]);
            	$('#menuTable').delegate('tr','click', function() {
                    if ($(this).hasClass('row_selected')) {
                        $("#menuName").val("");
                        $("#createMenuButtonLabel").html("Add Menu");
                        $("#createMenuButtonLabel").parent().attr("onclick", "createMenu(this)");
                        $("#deleteMenu").attr('disabled', 'disabled');
                        $(this).removeClass('row_selected');
                        
                        resetSelects(select_src, select_dst);
                        disableSelectContainer(menuItemTwinSelect);
                    } else {
                    	var data = menuTable.fnGetData(this);
                    	var aPos = menuTable.fnGetPosition(this);
                        $("#menuName").val(data[1]);
                        $("#createMenuButtonLabel").html("Update Menu");
                        $("#createMenuButtonLabel").parent().attr("onclick", "updateMenu(this," + aPos +")");
                    	$("#deleteMenu").attr("onclick", "confirmNotify('deleteAnMenu', 'Are You Sure Delete This Menu?', 'Delete', 'Cancel', 'deleteMenu', ['deleteAnMenuConfirmButton'])");
                        $("#deleteMenu").removeAttr('disabled');
                        menuTable.$('tr.row_selected').removeClass('row_selected');
                        $(this).addClass('row_selected');
                        
                        $.ajax({
                            type: "POST",
                            data: "menuId=" + data[0],
                            url: basePath + "/dev/menu/getMenuItems",
                            success: function (data) {
                            	if (data.status == 1) {
                            		menuItems = data.menuItems;
                            		resetSelects(select_src, select_dst);
                                	enableSelectContainer(menuItemTwinSelect);
                                	var menuItemId;
                                	var elements = [];
                                	for (var i in menuItems) {
                                		menuItemId = menuItems[i][0];
                                		elements.push(menuItemTwinSelect.find('option[value=' + menuItemId +']')[0]);
            						}
                                	addElementsToDest(select_src, select_dst, $(elements));
                            	} else if (data.status == -1) {
                            		notify('menuItemsRetrieveErrorNotification', 'alert-danger', data.message, 10000);
                            	}
                            }
                    	});
                        $("#saveMenuItems").attr("onclick", "saveMenuItems('" + data[0] + "')");
                    }
                });
            	
            	menuItemTwinSelect = $('#menuItemSelect').tandemSelect();
            	menuItemTwinSelect.attr("disabled", "disabled");
            	var select_src = menuItemTwinSelect.find('.tandem-select-src-select');
            	var select_dst = menuItemTwinSelect.find('.tandem-select-dst-select');
            	var templateOpt = select_src.children().first().clone();
            	select_src.children().first().remove();
            	var newOpt;
            	var menuItem;
            	for (var i in menuItems) {
            		menuItem = menuItems[i];
            		newOpt = templateOpt.clone();
            		newOpt.val(menuItem[0]);
            		newOpt.text(menuItem[1]);
            		select_src.append(newOpt);
				}
            	disableSelectContainer(menuItemTwinSelect);
            } else if (data.status == -1) {
                notify('menuTableErrorNotification', 'alert-danger', data.message, 10000);
            }
        }
    });
});

function createMenu(button, aPos) {
    var name = $("#menuName").val();
    var appMenu = {};
    appMenu["appId"] = appId;
    appMenu["menuName"] = name;
    if (!name) {
    	notify('warnNoDataEntredNotification', 'alert-info', 'No Values Entred!!!', 10000);
	} else {
		var l = Ladda.create(button);
		l.start();
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(appMenu),
	        url:  basePath + "/dev/menu/createMenu",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	menuTable.fnAddData(
	            				[data.id,
	            	             name]);
	            	$("#menuName").val("");
	            	$("#createMenuButtonLabel").html("Add Menu");
                    $("#createMenuButtonLabel").parent().attr("onclick", "createMenu(this)");
	            	notify('addMenuSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('addMenuErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function deleteMenu(button) {
    var selectedRow = fnGetSelected(menuTable);
    var rowData = menuTable.fnGetData(selectedRow);
    var rowNum = menuTable.fnGetPosition(selectedRow);
	if (rowData != null && rowData[0] != null && rowData[0] > 0) {
		var l = Ladda.create($("#" + button)[0]);
		l.start();
		$.ajax({
	        type: "POST",
	        data: "menuId=" + rowData[0],
	        url: basePath + "/dev/menu/deleteMenu",
	        success: function (data) {
	        	l.stop();
	            if (data.status == 1) {
	            	$("#menuName").val("");
	            	$("#createMenuButtonLabel").html("Add Menu");
                    $("#createMenuButtonLabel").parent().attr("onclick", "createMenu(this)");
	            	menuTable.fnDeleteRow(rowNum);
	            	notify('deleteMenuSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('deleteMenuErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}

function updateMenu(button, aPos) {
    var rowData = menuTable.fnGetData(aPos);
    var name = $("#menuName").val();
    var menu = {};
    menu["menuId"] = rowData[0];
    menu["menuName"] = name;
    if (rowData[1] == name) {
    	notify('warnNoDataChangesNotification', 'alert-info', 'No Values Changed!!!', 10000);
	} else {
		var l = Ladda.create(button);
		l.start();
	    $.ajax({
	        type: "POST",
	        dataType: "json",
	        contentType: "application/json",
	        data: JSON.stringify(menu),
	        url:  basePath + "/dev/menu/updateMenu",
	        success: function (data) {
	            l.stop();
	            if (data.status == 1) {
	            	menuTable.fnUpdate(
	            				[rowData[0],
	            	             name], aPos);
	            	notify('updateMenuSuccessNotification', 'alert-info', data.message, 10000);
	            } else if (data.status < 1) {
	            	notify('updateMenuErrorNotification', 'alert-danger', data.message, 10000);
	            }
	        }
	    });
	}
}