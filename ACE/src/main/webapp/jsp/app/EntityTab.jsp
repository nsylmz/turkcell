<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="entity-container dev-item-container">
	<div class="container dev-input-container">
		<label>Entity Name</label>
		<input id="entityName" class="ace-input" type="text" name="entityName" placeholder="Enter An Entity Name">
	</div>
	<div class="container dev-button-container">
		<button class="ace-button ladda-button" data-style="expand-right" onclick="createEntity(this)">
			<span id="createEntityButtonLabel" class="ladda-label">Add Entity</span>
		</button>
		<button id="deleteEntity" class="ace-button ladda-button" data-style="expand-left" 
			disabled="disabled" onclick="deleteEntity(this)">
			<span class="ladda-label">Delete Entity</span>
		</button>
	</div>
	<div class="container dev-table-container">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="entityTable">
			<thead>
				<tr width="100%">
					<th width="0%">id</th>
					<th width="100%">Entity Name</th>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot>
				<tr>
					<th>id</th>
					<th>Entity Name</th>
				</tr>
			</tfoot>
		</table>
	</div>
</div>

<script type="text/javascript">
</script>

<script src="${pageContext.request.contextPath}/js/app/entity.js"></script>
