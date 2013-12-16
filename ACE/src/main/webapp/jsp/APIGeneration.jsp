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
					<ul>
						<li class="begin" element-name="Begin">Begin</li>
						<li class="end" element-name="End">End</li>
						<li class="if" element-name="If">If</li>
					</ul>
				</div>
				<h2>
					<a href="#">End Points</a>
				</h2>
				<div>
					<ul>
						<li class="web-service" element-name="WebService">Web Service</li>
						<li class="rest" element-name="Rest">Rest Service</li>
					</ul>
				</div>
				<h2>
					<a href="#">Java</a>
				</h2>
				<div>
					<ul>
						<li class="java-method" element-name="JavaMethod">Java API Method</li>
					</ul>
				</div>
			</div>
		</div>
	
		<div id="cart">
			<h1 class="ui-widget-header">View</h1>
			<div class="ui-widget-content view"></div>
		</div>
		
		<div class="componentFeature">
			<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/jquery-2.0.3.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/holder.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/UIGeneration.js" type="text/javascript"></script>

	<script type="text/javascript">
		UIGeneration();
	</script>
</body>
</html>