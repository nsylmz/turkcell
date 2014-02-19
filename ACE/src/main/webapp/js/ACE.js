function fnGetSelected(table) {
    return table.$('tr.row_selected')[0];
}

function notify(notificationId, alertType, notificationText, showTime) {
    if ($('#' + notificationId).length == 0) {
    	var tempNotifyElement = $('#temp-notify');
        var newNotification = tempNotifyElement.clone();
        newNotification.attr("id", notificationId);
        newNotification.addClass(alertType);
        newNotification.append(notificationText);
        newNotification.find("button").attr("onclick", "closeNotification('" + notificationId + "')");
        newNotification.appendTo("#notificationContainer");
        newNotification.show();
        setTimeout(function () {
            closeNotification(notificationId);
        }, showTime);
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
		newNotification.show();
		newNotification.addClass("alert-info");
		newNotification.find(".confirm-text-container").append(notificationText);
		newNotification.find(".confirm-button").attr("id", notificationId + "ConfirmButton");
		newNotification.find(".confirm-button span").text(confirmText);
		newNotification.find(".cancel-button span").text(cancelText);
		newNotification.find(".cancel-button").attr("onclick", "closeConfirmNotification('" + notificationId + "')");
		var confirmOnclik = confirmMethodName + "(";
		for (var i in confirmMethodParams) {
			if (i == 0 && i == confirmMethodParams.length-1 && typeof confirmMethodParams[i] == "string") {
				confirmOnclik = confirmOnclik + "'" + confirmMethodParams[i] + "'";
			} else if (i == 0 && i == confirmMethodParams.length-1) {
				confirmOnclik = confirmOnclik + confirmMethodParams[i];
			} else if (i == 0 && typeof confirmMethodParams[i] == "string") {
				confirmOnclik = confirmOnclik + "'" + confirmMethodParams[i] + "', ";
			} else if (i == 0) {
				confirmOnclik = confirmOnclik + confirmMethodParams[i] + ", ";
			} else if (i != confirmMethodParams.length-1 && typeof confirmMethodParams[i] == "string") {
				confirmOnclik = confirmOnclik + "'" + confirmMethodParams[i] + "', ";
			} else if (i != confirmMethodParams.length-1) {
				confirmOnclik = confirmOnclik + confirmMethodParams[i] + ", ";
			} else if (typeof confirmMethodParams[i] == "string") {
				confirmOnclik = confirmOnclik + "'" + confirmMethodParams[i] + "'";
			} else {
				confirmOnclik = confirmOnclik + confirmMethodParams[i];
			}
		}
		confirmOnclik = confirmOnclik + ");" + "closeConfirmNotification('" + notificationId + "')";
		newNotification.find(".confirm-button").attr("onclick", confirmOnclik);
		newNotification.appendTo("#notificationContainer");

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