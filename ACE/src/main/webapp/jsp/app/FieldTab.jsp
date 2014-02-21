<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="field-container dev-item-container">
	<div class="container dev-input-container">
		<label>Field Name</label>
		<input id="fieldName" class="ace-input" type="text" name="fieldName" placeholder="Enter An Field Name">
	</div>
	<div class="container dev-input-container">
		<c:choose>
			<c:when test="${not empty appEntities}">
				<label>Select An Entity</label>
				<select id="fieldEntitySelect">
					<option value=""></option>
					<c:forEach var="entity" items="${appEntities}">
						<option value="${entity[0]}">${entity[1]}</option>
					</c:forEach>
				</select>
			</c:when>
			<c:otherwise>
				<label>No Entity Defined For Defining A Field</label>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="container dev-input-container">
		<label>Indexed</label>
		<input id="indexedField" type="checkbox" name="Indexed" value="Indexed">
	</div>
	<div class="container dev-input-container">
		<label>Select A Field Type</label>
		<select id="fieldTypeSelect">
			<option value=""></option>
			<option value="String">String</option>
			<option value="Char">Char</option>
			<option value="Integer">Integer</option>
			<option value="Long">Long</option>
			<option value="Double">Double</option>
			<option value="Float">Float</option>
			<option value="Boolean">Boolean</option>
			<option value="Date">Date</option>
			<option value="DateTime">DateTime</option>
		</select>
	</div>
	<c:if test="${not empty appEntities}">
		<div class="container dev-input-container">
			<label>Or</label>
		</div>
	</c:if>
	<div class="container dev-input-container">
		<c:choose>
			<c:when test="${not empty appEntities}">
				<label>Select An Entity As Field Type</label>
				<select id="fieldEntityTypeSelect">
					<option value=""></option>
					<c:forEach var="entity" items="${appEntities}">
						<option value="${entity[0]}">${entity[1]}</option>
					</c:forEach>
				</select>
			</c:when>
			<c:otherwise>
				<label>No Entity Defined For Selecting As A Field Type</label>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="container dev-button-container">
		<button class="ace-button ladda-button" data-style="expand-right" onclick="createField(this)">
			<span id="createFieldButtonLabel" class="ladda-label">Add Field</span>
		</button>
		<button id="deleteField" class="ace-button ladda-button" data-style="expand-left" 
			disabled="disabled" onclick="deleteField(this)">
			<span class="ladda-label">Delete Field</span>
		</button>
	</div>
	<div class="container dev-table-container">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="fieldTable">
			<thead>
				<tr width="100%">
					<th width="0%">id</th>
					<th width="25%">Field Name</th>
					<th width="25%">Field Type</th>
					<th width="25%">Indexed</th>
					<th width="0%">Entity Id</th>
					<th width="25%">Entity Name</th>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot>
				<tr>
					<th>id</th>
					<th>Field Name</th>
					<th>Field Type</th>
					<th>Indexed</th>
					<th>Entity id</th>
					<th>Entity Name</th>
				</tr>
			</tfoot>
		</table>
	</div>
	
	<div class="field-feature-contianer">
		<div class="field-feature-header">
			Field Annotation Features
		</div>
	</div>
	
	<div class="field-feature-contianer">
		<div class="field-feature-header">
			Field Annotation Member Features
		</div>
	</div>
</div>

<script type="text/javascript">
</script>

<script src="${pageContext.request.contextPath}/js/app/field.js"></script>
