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
	
	<title>Beta Turkcell ACE</title>
	<link rel="shortcut icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
	<link rel="icon" href="http://s.turkcell.com.tr/static_lib/assets/images/common/favicon.ico" type="image/vnd.microsoft.icon">
	<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/UIGeneration.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/Components.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/flowchart.css" rel="stylesheet" type="text/css" />

</head>

<body>
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
		
		<div class="componentFeature">
			<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery-2.0.3.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/holder.js" type="text/javascript"></script>
	
	<script src="${pageContext.request.contextPath}/js/jquery.ui.touch-punch.min.js"></script>        
    <script src="${pageContext.request.contextPath}/js/jsBezier-0.6.js"></script>     
    <script src="${pageContext.request.contextPath}/js/jsplumb-geom-0.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/util.js"></script>
    <script src="${pageContext.request.contextPath}/js/dom-adapter.js"></script>        
    <script src="${pageContext.request.contextPath}/js/jsPlumb.js"></script>
    <script src="${pageContext.request.contextPath}/js/endpoint.js"></script>                
    <script src="${pageContext.request.contextPath}/js/connection.js"></script>
    <script src="${pageContext.request.contextPath}/js/anchors.js"></script>        
    <script src="${pageContext.request.contextPath}/js/defaults.js"></script>
    <script src="${pageContext.request.contextPath}/js/connectors-bezier.js"></script>
    <script src="${pageContext.request.contextPath}/js/connectors-statemachine.js"></script>
    <script src="${pageContext.request.contextPath}/js/connectors-flowchart.js"></script>
    <script src="${pageContext.request.contextPath}/js/connector-editors.js"></script>
    <script src="${pageContext.request.contextPath}/js/renderers-svg.js"></script>
    <script src="${pageContext.request.contextPath}/js/renderers-canvas.js"></script>
    <script src="${pageContext.request.contextPath}/js/renderers-vml.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.jsPlumb.js"></script>
    <script src="${pageContext.request.contextPath}/js/flowchart.js"></script>
    
	<script src="${pageContext.request.contextPath}/js/APIGeneration.js" type="text/javascript"></script>

	<script type="text/javascript">
		APIGeneration();
	</script>
</body>
</html>