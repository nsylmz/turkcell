<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="shortcut icon" href="docs-assets/ico/favicon.png">
		
		<title>Create App | Turkcell ACE</title>
		<link rel="shortcut icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
		<link rel="icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
		<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/ladda.min.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/ladda-themeless.min.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/UIGeneration.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/flowchart.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/featureBar.css" rel="stylesheet" type="text/css" />
	<head>
	
	<body>
		<div class="container ace-title-container">
			<h1>Application Creation Enviroment</h1>
		</div>
		<div class="container app-ace-container">
			<div class="container">
				<h2 class="create-app-title">Create An App</h2>
				<h2 class="run-app-title">Run An App</h2>
			</div>
			<div class="container create-app-container">
				<input id="appName" class="ace-input" type="text" name="appName" placeholder="Enter An App Name">
				<button class="create-app-button ace-button btn btn-primary ladda-button" data-style="expand-down"
		                onclick="createApp(this, '${pageContext.request.contextPath}')">
		        	<span class="ladda-label">Create App</span>
		        </button>
			</div>
			<div class="container get-app-container">
				<c:choose>
					<c:when test="${not empty appNames}">
						<div class="ui-widget">
							<label>Apps </label>
							<select id="appNameSelect">
								<option value="">Select App...</option>
								<c:forEach var="appName" items="${appNames}">
									<option value="${appName}">${appName}</option>
								</c:forEach>
							</select>
						</div>
						<button class="run-app-button ace-button btn btn-primary ladda-button" data-style="expand-down"
				                onclick="runApp('${pageContext.request.contextPath}')">
				        	<span class="ladda-label">Run App</span>
				        </button>
					</c:when>
					<c:otherwise>
						<div class="no-app">
							No App is defined in ACE
						</div>
						<button class="run-app-button ace-button btn btn-primary ladda-button" data-style="expand-down"
				                onclick="runApp('${pageContext.request.contextPath}')" disabled="disabled">
				        	<span class="ladda-label">Run App</span>
				        </button>
				    </c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<script src="${pageContext.request.contextPath}/js/jquery-2.0.3.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/holder.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/ladda/spin.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ladda/ladda.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-ui.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/ComboBox.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			function createApp(button, basePath) {
				var l = Ladda.create(button);
			    l.start();
				var appName = $("#appName").val();
				$.ajax({
			        type: 			"POST",
			        dataType: 		"json",
			        contentType: 	"application/json",
			        url: 			basePath + "/app/createApp",
			        data: 			appName,
			        success: function (data) {
			            l.stop();
			            if (data.status == 1) {
			            	window.location.href = basePath + "/app/" + appName;
			            } else if (data.status < 1) {
			            	notify('createAppErrorNotification', 'alert-danger', data.message, 5000);
			            }
			        }
			    });
			} 
			
			function runApp(basePath) {
				var appName = $('#appNameSelect').find('option:selected').val();
				if (appName) {
					window.location.href = basePath + "/app/" + appName;
				}
			}
		</script>
		<c:if test="${not empty appNames}">
			<script type="text/javascript">
				$("#appNameSelect").combobox();
			</script>
		</c:if>
	</body>
</html>
