<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-item-container dev-item-container">
	<div class="container dev-input-container">
		<label>Menu Item Name</label>
		<input id="menuItemName" class="ace-input" type="text" name="menuItemName" placeholder="Enter A Menu Item Name">
	</div>
	<div class="container dev-input-container">
		<c:choose>
			<c:when test="${not empty appMenus}">
				<label>Select Menu</label>
				<select id="menuSelect">
					<c:forEach var="menu" items="${appMenus}">
						<option value="${menu[0]}">${menu[1]}</option>
					</c:forEach>
				</select>
			</c:when>
			<c:otherwise>
				<label>No Menu Defined. Before A Menu Item Define, you have to define A Menu</label>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="container dev-input-container">
		<c:choose>
			<c:when test="${not empty appPages}">
				<label>Select Page</label>
				<select id="pageSelect">
					<c:forEach var="page" items="${appPages}">
						<option value="${page[0]}">${page[1]}</option>
					</c:forEach>
				</select>
			</c:when>
			<c:otherwise>
				<label>No Page Defined</label>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="container dev-button-container">
		<button class="ace-button ladda-button" data-style="expand-right" onclick="createMenuItem(this)">
			<span id="createMenuItemButtonLabel" class="ladda-label">Add Menu Item</span>
		</button>
		<button id="deleteMenuItem" class="ace-button ladda-button" data-style="expand-left" 
			disabled="disabled" onclick="deleteMenuItem(this)">
			<span class="ladda-label">Delete Menu Item</span>
		</button>
	</div>
	<div class="container dev-table-container">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="menuItemTable">
			<thead>
				<tr width="100%">
					<th width="0%">id</th>
					<th width="33.3%">Menu Item Name</th>
					<th width="0%">Menu Id</th>
					<th width="33.3%">Menu Name</th>
					<th width="0%">Page Id</th>
					<th width="33.3%">Page Name</th>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot>
				<tr>
					<th>id</th>
					<th>Menu Item Name</th>
					<th>Menu Id</th>
					<th>Menu Name</th>
					<th>Page Id</th>
					<th>Page Name</th>
				</tr>
			</tfoot>
		</table>
	</div>
</div>

<script type="text/javascript">
</script>

<script src="${pageContext.request.contextPath}/js/app/menuItem.js"></script>