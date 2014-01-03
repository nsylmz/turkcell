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
	<link href="${pageContext.request.contextPath}/css/ladda.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/ladda-themeless.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/UIGeneration.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/flowchart.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/featureBar.css" rel="stylesheet" type="text/css" />
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
							<a class="navbar-brand" href="${pageContext.request.contextPath}/UIGeneration">Application Creation Environment</a>
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
		<div class="view-bar bar-transition">
			<div class="view-bar-item item-first" onclick="openSaveBar()">
				<img class="view-item-img" title="Save View" alt="Save View" src="${pageContext.request.contextPath}/img/save-icon.png">
				<label>Save Process</label>
			</div>
	        <div class="view-bar-item item-last" onclick="openViewModal('${pageContext.request.contextPath}')">
	        	<img class="view-item-img" title="Open View" alt="Open View" src="${pageContext.request.contextPath}/img/open-icon.png">
		        <label>Open Process</label>
	        </div>
	    </div>
	    <div class="modal fade" id="openViewModal" tabindex="-1" role="dialog" aria-labelledby="openViewModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">Application Creation Environment</h3>
					</div>
					<div class="modal-body" id="openViewModalBody">
						<c:if test="${not empty viewNames}">
							<ol id="selectable">
		    					<c:forEach var="viewName" items="${viewNames}">
									<li class="ui-widget-content">${viewName}</li>
								</c:forEach>
							</ol>
							<button type="button" class="ace-button go-to-view-button" onclick="goToAPIView(this, '${pageContext.request.contextPath}')">go to view</button>
						</c:if>
					</div>
                    <div class="modal-footer">
                      	<button type="button" class="ace-button modal-close" data-dismiss="modal">close</button>
					</div>
				</div>
			</div>
		</div>
	    <div class="save-bar bar-transition">
	    	<button type="button" class="close save-bar-close" aria-hidden="true" onclick="closeSaveBar()">&times;</button>
			<label>Process Name</label>
			<input id="view-input" class="view-name-input" type="text" name="viewInput">
			<button id="save-view-button" class="ace-button btn btn-primary ladda-button" data-style="expand-right"
		        onclick="saveProcess(this, '${pageContext.request.contextPath}')">
		        <span class="ladda-label">Save Process</span>
	        </button>
	    </div>
		<div id="products">
			<h1 class="ui-widget-header">API Elements</h1>
			<div id="catalog">
				<h2>
					<a href="#">Process</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<li class="list-group-item" element-name="begin">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/start.png">
								<div class="list-item-label">Begin</div>
							</div>
						</li>
						<li class="list-group-item" element-name="end">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/end.png">
								<div class="list-item-label">End</div>
							</div>
						</li>
						<li class="list-group-item" element-name="if">
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
						<li class="list-group-item" element-name="webservice">
							<div class="list-item-container">
								<img class="component-img" src="${pageContext.request.contextPath}/img/web-service.png">
								<div class="list-item-label">Web Service</div>
							</div>
						</li>
						<li class="list-group-item" element-name="restservice">
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
						<li class="list-group-item" element-name="javamethod">
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
			<h1 class="ui-widget-header">API Process</h1>
			<div id="flowchart-view" class="ui-widget-content view flowchart-view"></div>
		</div>

		<div class="feature-container">
			<div id="webservice-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
						<li><a href="#WebService-feature">WebService</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
						<div class="delete-button-container">
							<button class="ace-button btn btn-primary ladda-button" data-style="expand-left">
				            	<span class="ladda-label">Delete Component</span>
				            </button>
			            </div>
					</div>
					<div id="WebService-feature">
						<div class="component-feature-container">
							<div class="component-feature">
								<label>Wsdl URL</label>
								<input class="wsdl-input feature-input" type="text" name="wsdlUrl">
								<button class="read-wsdl-button ace-button btn btn-primary ladda-button" data-style="expand-down"
		                                onclick="readWsdlAndGetOperationName(this, '${pageContext.request.contextPath}')">
		                            <span class="ladda-label">Read WSDL</span>
		                        </button>
								<div class="ws-select ui-widget">
									<label>Operations </label>
									<select class="ws-opreations">
										<option value="">Select Operation...</option>
									</select>
									<button class="get-operation-button ace-button btn btn-primary ladda-button" data-style="expand-down"
		                                onclick="getOpDetail(this, '${pageContext.request.contextPath}')">
			                            <span class="ladda-label">Get Operation Detail</span>
			                        </button>
								</div>
								<button class="ws-run-button ace-button btn btn-primary ladda-button" data-style="expand-left"
		                                onclick="runWS(this)">
			                            <span class="ladda-label">Test Ws Operation</span>
			                    </button>
							</div>
							<div class="component-feature">
								<div id="param-name-container" class="param-container">
									<label class="param-name">Parameter Name</label>
									<input id="param-name-input" class="param-input feature-input" type="text" name="param-name">
									<label class="param-type">Parameter Type</label>
								</div>
								<div class="params-container"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div id="begin-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
						<div class="delete-button-container">
							<button class="ace-button btn btn-primary ladda-button" data-style="expand-left">
				            	<span class="ladda-label">Delete Component</span>
				            </button>
			            </div>
					</div>
				</div>
			</div>
			
			<div id="end-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
						<div class="delete-button-container">
							<button class="ace-button btn btn-primary ladda-button" data-style="expand-left">
				            	<span class="ladda-label">Delete Component</span>
				            </button>
			            </div>
					</div>
				</div>
			</div>
			
			<div id="if-feature-bar" class="feature-bar display-none">
				<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
				<div id="tabs" class="feature-tabs">
					<ul>
						<li><a href="#feature-info">Component Info</a></li>
					</ul>
					<div id="feature-info">
						<div class="component-info">
							<div class="component-name">
								<label>Component Name</label>
								<input class="feature-input" type="text" name="componentName">
							</div>
						</div>
						<div class="delete-button-container">
							<button class="ace-button btn btn-primary ladda-button" data-style="expand-left">
				            	<span class="ladda-label">Delete Component</span>
				            </button>
			            </div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="wsResponseModal" tabindex="-1" role="dialog" aria-labelledby="wsResponseModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">Application Creation Environment</h3>
					</div>
					<div class="modal-body" id="wsResponseModalBody">
						<label class="class-name">ClassName</label>
						<ul class="list-group ws-response-content">
							<li class="list-group-item ws-response-item">
								<div class="ws-item-field-name">fieldName</div>
								<div class="ws-item-field-value">fieldValue</div>
							</li>
						</ul>
					</div>
                    <div class="modal-footer">
                      	<button type="button" class="ace-button modal-close" data-dismiss="modal">close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="${pageContext.request.contextPath}/js/jquery-2.0.3.js" type="text/javascript"></script>
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
    
	<script src="${pageContext.request.contextPath}/js/jquery-ui.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/ComboBox.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/APIGeneration.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		APIGeneration();
		$( "#selectable" ).selectable();
		
		function openViewModal(basePath) {
			$('#openViewModal').modal('toggle');
		}
		
		function goToAPIView(button, basePath) {
			var viewName = $(button).parent().find('.ui-selected').text();
			if (viewName) {
				window.location.href = basePath + "/APIGeneration/" + viewName;
			} else {
				notify('noViewSelectedErrorNotification', 'alert-info', 'No APIView Selected!!! Please Select API View.', 5000);
			}
		}
		
		
		function deleteComponent(featureBarId) {
		    var featureBar = $("#" + featureBarId);
		    var componentId = featureBar.attr("id").replace("-feature-bar", "");
		    var endPoints = flowchart.getEndpoints(componentId);
		    for (var i in endPoints) {
		    	flowchart.deleteEndpoint(endPoints[i]);
			}
		    $('#' + componentId).remove();
		    featureBar.remove();
		}
	</script>
	
	<c:if test="${not empty processView}">
		<script type="text/javascript">
			$(document).ready(function() {
				onload(${processView}, '${pageContext.request.contextPath}');
			});
		</script>
	</c:if>
</body>
</html>