<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container menu-container dev-item-container">
	<div class="container dev-input-container">
		<label>Menu Name</label>
		<input id="menuName" class="ace-input" type="text" name="menuName" placeholder="Enter A Menu Name">
	</div>
	<div class="container dev-button-container">
		<button class="ace-button ladda-button" data-style="expand-right" onclick="createMenu(this)">
			<span id="createMenuButtonLabel" class="ladda-label">Add Menu</span>
		</button>
		<button id="deleteMenu" class="ace-button ladda-button" data-style="expand-left" 
			disabled="disabled" onclick="deleteMenu(this)">
			<span class="ladda-label">Delete Menu</span>
		</button>
	</div>
	<div class="container dev-table-container">
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="menuTable">
			<thead>
				<tr width="100%">
					<th width="0%">id</th>
					<th width="100%">Menu Name</th>
				</tr>
			</thead>
			<tbody></tbody>
			<tfoot>
				<tr>
					<th>id</th>
					<th>Menu Name</th>
				</tr>
			</tfoot>
		</table>
	</div>
	<div class='tandem-select-container'>
		<div class='tandem-select-src-div'>
			Available Menu Items 
			<select multiple='multiple' class='tandem-select-src-select'>
				<option id="src-template-option" value=""></option>
			</select>
		</div>
		<div class='tandem-select-controls-div'>
			<br /> <br /> <br /> 
			<input type="button" class="tandem-select-move-to-src" value="&nbsp;&lt;&nbsp;" /> 
				&nbsp; &nbsp; 
			<input type="button" class="tandem-select-move-to-dst" value="&nbsp;&gt;&nbsp;" /> 
			<br /> <br /> Search <br /> 
			<input type="text" class="tandem-select-search-src" size="15" /> 
			<br /> <br /> 
			<input id="saveMenuItems" type="button" value="Save">
		</div>
		<div class='tandem-select-dst-div'>
			Selected Menu Items 
			<select multiple='multiple' class='tandem-select-dst-select' 
					id='menuItemSelect' name='menuItemSelect'>
			</select>
		</div>
	</div>
</div>				

<script type="text/javascript">
</script>

<script src="${pageContext.request.contextPath}/js/app/menu.js"></script>
