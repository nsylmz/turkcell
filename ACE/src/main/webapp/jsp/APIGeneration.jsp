<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="docs-assets/ico/favicon.png">
	
	<title>Turkcell ACE</title>
	<link rel="shortcut icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
	<link rel="icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
	<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/UIGeneration.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/Components.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/flowchart.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/featureBar.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/ladda.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/ladda-themeless.min.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="notification-container">
	 	<div class="alert appStore-notification">
			<button type="button" class="close notification-close" aria-hidden="true" onclick="closeNotification()">&times;</button>
	    </div>
	</div>
	<div id="htmlPallet-container" class="container">
		<div class="navbar-wrapper">
			<div class="container">

				<div class="navbar navbar-inverse navbar-static-top">
					<div class="container">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".navbar-collapse">
								<span class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath}/UIGeneration">Application Creation Enviroment</a>
						</div>
						<div class="navbar-collapse collapse">
							<ul class="nav navbar-nav">
								<li><a href="${pageContext.request.contextPath}/UIGeneration">UI Generation</a></li>
								<li class="active"><a href="${pageContext.request.contextPath}/APIGeneration">API Generation</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="products">
			<h1 class="ui-widget-header">API Elements</h1>
			<div id="catalog">
				<h2>
					<a href="#">Process</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<li class="list-group-item" element-name="Begin">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/start.png">
								<div class="list-item-label">Begin</div>
							</div>
						</li>
						<li class="list-group-item" element-name="End">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/end.png">
								<div class="list-item-label">End</div>
							</div>
						</li>
						<li class="list-group-item" element-name="If">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/if.png">
								<div class="list-item-label">If</div>
							</div>
						</li>
					</ul>
				</div>
				<h2>
					<a href="#">End Points</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<li class="list-group-item" element-name="WebService">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/web-service.png">
								<div class="list-item-label">Web Service</div>
							</div>
						</li>
						<li class="list-group-item" element-name="RestService">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/rest-service.png">
								<div class="list-item-label">Rest Service</div>
							</div>
						</li>
					</ul>
				</div>
				<h2>
					<a href="#">Java</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<li class="list-group-item" element-name="JavaMethod">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/java-big.png">
								<div class="list-item-label">Java API Method</div>
							</div>
						</li>
					</ul>
				</div>	
			</div>
		</div>
	
		<div id="cart">
			<h1 class="ui-widget-header">View</h1>
			<div id="flowchart-view" class="ui-widget-content view flowchart-view"></div>
		</div>
		
		<div id="WebService" class="feature-bar">
			<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
			<div id="tabs" class="feature-tabs">
				<ul>
					<li><a href="#feature-info">Component Info</a></li>
					<li><a href="#WebService-feature">WebService</a></li>
				</ul>
				<div id="feature-info">
					<div class="component-info">
						<div class="component-name">
							<p>Component Name</p>
							<input class="feature-input" type="text" name="componentName">
						</div>
					</div>
				</div>
				<div id="WebService-feature">
					<div class="component-feature-container">
						<div class="component-feature">
							<p>Wsdl URL</p>
							<input id="wsd-input" class="feature-input" type="text" name="wsdlUrl">
							<button id="read-wsdl-button" class="ladda-button" data-style="expand-right"
	                                onclick="readWsdlAndGetOperationName(this, '${pageContext.request.contextPath}')">
	                            <span class="ladda-label">Read WSDL</span>
	                        </button>
						</div>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery-2.0.3.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/holder.js" type="text/javascript"></script>
	
	<script src="${pageContext.request.contextPath}/js/flowchart/jquery.ui.touch-punch.min.js"></script>        
    <script src="${pageContext.request.contextPath}/js/flowchart/jsBezier-0.6.js"></script>     
    <script src="${pageContext.request.contextPath}/js/flowchart/jsplumb-geom-0.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/util.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/dom-adapter.js"></script>        
    <script src="${pageContext.request.contextPath}/js/flowchart/jsPlumb.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/endpoint.js"></script>                
    <script src="${pageContext.request.contextPath}/js/flowchart/connection.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/anchors.js"></script>        
    <script src="${pageContext.request.contextPath}/js/flowchart/defaults.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connectors-bezier.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connectors-statemachine.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connectors-flowchart.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/connector-editors.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/renderers-svg.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/renderers-canvas.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/renderers-vml.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/jquery.jsPlumb.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart/flowchart.js"></script>
    
    <script src="${pageContext.request.contextPath}/js/ladda/spin.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ladda/ladda.min.js"></script>
    
	<script src="${pageContext.request.contextPath}/js/APIGeneration.js" type="text/javascript"></script>
	

	<script type="text/javascript">
		APIGeneration();
		$("#tabs").tabs();
		
		function readWsdlAndGetOperationName(button, basePath){
			var wsdlUrl = $(button).parent().find('.feature-input').val();
		    var l = Ladda.create(button);
		    l.start();
		    $.ajax({
		        type: "POST",
		        url: basePath + "/ws/readWsdl",
		        data: "wsdlUrl=" + wsdlUrl,
		        success: function (data) {
		            l.stop();
		            if (data.status == 1) {
		            	notify('appApproveSuccessNotification', 'alert-info', 'WSDL Başarılı Bir Şekilde Okundu.', 10000);
		            } else if (data.status < 1) {
		            	notify('appApproveErrorNotification', 'alert-danger', 'WSDL Okuma Sırasında Hata Alındı!!!', 10000);
		            }
		        }
		    });
		}
	</script>
</body>
</html>