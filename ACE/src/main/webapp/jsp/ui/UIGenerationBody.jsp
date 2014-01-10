<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="htmlPallet-container" class="container">
	<div class="view-bar bar-transition">
		<div class="view-bar-item item-first" onclick="openSaveBar()">
			<img class="view-item-img" title="Save View" alt="Save View" src="${pageContext.request.contextPath}/img/save-icon.png">
			<label>Save View</label>
		</div>
        <div class="view-bar-item item-last" onclick="openViewModal('${pageContext.request.contextPath}')">
			<img class="view-item-img" title="Open View" alt="Open View" src="${pageContext.request.contextPath}/img/open-icon.png">
			<label>Open View</label>
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
						<button type="button" class="ace-button go-to-view-button" onclick="goToUIView(this, '${pageContext.request.contextPath}')">go to ui view</button>
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
		<label>View Name</label>
		<input id="view-input" class="view-name-input" type="text" name="viewInput">
		<button id="save-view-button" class="ace-button btn btn-primary ladda-button" data-style="expand-right"
	        onclick="saveView(this, '${pageContext.request.contextPath}')">
	        <span class="ladda-label">Save View</span>
        </button>
    </div>
	<div id="products">
		<h1 class="ui-widget-header">Components</h1>
		<div id="catalog">
			<c:forEach var="category" items="${categories}">
				<h2>
					<a href="#">${category.key}</a>
				</h2>
				<div>
					<ul class="list-group component-list">
						<c:forEach var="element" items="${category.value}">
							<li class="list-group-item" element-name="${element.elementLabel}">
								<div class="list-item-container">
									<img class="component-img" src="${pageContext.request.contextPath}/img/${element.iconName}" >
									<div class="list-item-label">${element.elementLabel}</div>
									<c:out value="${element.elementHtml}" escapeXml="false"/>
									<!-- button class="temp-component ace-button btn btn-primary ladda-button" element-name="button" data-style="expand-right">
					                	<span class="ladda-label">button</span>
					                </button -->
								</div>
							</li>
							<!-- 
							<li class="list-group-item" element-name="input" element-type="checkbox">
								<div class="list-item-container">
									<div class="list-item-label">Check Box</div>
								</div>
							</li>
							<li class="list-group-item" element-name="input" element-type="radio">
								<div class="list-item-container">
									<div class="list-item-label">Radio Button</div>
								</div>
							</li>
							<li class="list-group-item" element-name="input" element-type="text">
								<div class="list-item-container">
									<div class="list-item-label">Text Field</div>
								</div>
							</li>
							<li class="list-group-item" element-name="input" element-type="textarea">
								<div class="list-item-container">
									<div class="list-item-label">Text Area</div>
								</div>
							</li>
							 -->
						</c:forEach>
					</ul>
				</div>
			</c:forEach>
			<!-- 
			<h2>
				<a href="#">List Components</a>
			</h2>
			<div>
				<ul class="list-group component-list">
					<li class="list-group-item">Select</li>
					<li class="list-group-item">Combo Box</li>
				</ul>
			</div>
			<h2>
				<a href="#">Forms</a>
			</h2>
			<div>
				<ul class="list-group component-list">
					<li class="list-group-item">Form</li>
				</ul>
			</div>
			 -->
		</div>
	</div>

	<div id="cart">
		<h1 class="ui-widget-header">View</h1>
		<div class="ui-widget-content view"></div>
	</div>
	
	<div class="feature-container">
		<div id="Button-feature-bar" class="feature-bar display-none">
			<button type="button" class="close featureBar-close" aria-hidden="true" onclick="closeFeatureBar()">&times;</button>
			<div id="tabs" class="feature-tabs">
				<ul>
					<li><a href="#feature-info">Button Features</a></li>
				</ul>
				<div id="feature-info">
					<div class="component-info">
						<div class="component-feature-container">
							<div class="component-feature">
								<div class="component-name-container">
									<label>Component Name</label>
									<input class="component-name feature-input" type="text" name="componentName">
								</div>
								<div class="component-label-container">
									<label>Component Label</label>
									<input class="component-label feature-input" type="text" name="componentLabel" 
										onkeypress="changeButtonLabel(this)" onkeyup="changeButtonLabel(this)" onchange="changeButtonLabel(this)">
								</div>
								<div class="component-process-container">
									<div class="ui-widget">
										<label>Process </label>
										<select class="processes">
											<option value="">Select Process...</option>
											<c:if test="${not empty processNames}">
	     										<c:forEach var="processName" items="${processNames}">
													<option value="${processName}">${processName}</option>
												</c:forEach>
											</c:if>
										</select>
										<!-- button class="test-process-button ace-button btn btn-primary ladda-button" data-style="expand-down"
			                                	onclick="runProcess(this, '${pageContext.request.contextPath}')">
				                            <span class="ladda-label">Test Process</span>
				                        </button -->
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/js/UIGeneration.js" type="text/javascript"></script>

<script type="text/javascript">
	UIGeneration();
	$(".feature-tabs").tabs();
	$( "#selectable" ).selectable();
</script>

<c:if test="${not empty uiView}">
	<script type="text/javascript">
		$(document).ready(function() {
			onload(${uiView});
		});
	</script>
</c:if>