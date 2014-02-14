<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="htmlPallet-container" class="container">
	<div class="dev-bar-title bar-transition" onclick="openDevBar()">
		<h2></h2>
	</div>
	<div class="dev-bar bar-transition">
		<div class="dev-menu-container">
			<div id="dev-tabs" class="dev-menu">
				<ul>
					<li><a href="#menus">Manage Menus</a></li>
					<li><a href="#pages">Manage Pages</a></li>
					<li><a href="#entities">Manage Entities</a></li>
					<li><a href="#fields">Manage Fields</a></li>
				</ul>
				<div id="menus" class="container">
					<div class="container menu-container dev-item-container">
						<div class="container dev-input-container">
							<label>Menu Name</label>
							<input id="menuName" class="ace-input" type="text" name="menuName" placeholder="Enter A Menu Name">
						</div>
						<div class="container dev-button-container">
							<button class="ace-button ladda-button" data-style="expand-right" onclick="createMenu(this)">
								<span id="craeteMenuButtonLabel" class="ladda-label">Add Menu</span>
							</button>
							<button id="deleteMenu" class="ace-button ladda-button" data-style="expand-left" 
								disabled="disabled" onclick="deleteMenu(this)">
								<span class="ladda-label">Delete Menu</span>
							</button>
						</div>
						<div class="container dev-table-container">
							<table cellpadding="0" cellspacing="0" border="0" class="display" id="menus">
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
				</div>
				<div id="pages">
					<div class="page-container dev-item-container">
					</div>
				</div>
				<div id="entities">
					<div class="entity-container dev-item-container">
					</div>
				</div>
				<div id="fields">
					<div class="field-container dev-item-container">
					</div>
				</div>
			</div>
		</div>
    </div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$(".dev-bar-title h2").text("O\np\ne\nn\n \nD\ne\nv\ne\nl\no\np\nm\ne\nn\nt\n \nB\na\nr");
	$("#dev-tabs").tabs();
});

function openDevBar() {
	$(".dev-bar-title").css("left", "1000px");
	$(".dev-bar-title").attr("onclick", "closeDevBar()");
	$(".dev-bar").css("left", "0px");
	$(".dev-bar-title h2").text("C\nl\no\ns\ne\n \nD\ne\nv\ne\nl\no\np\nm\ne\nn\nt\n \nB\na\nr");
}

function closeDevBar() {
	$(".dev-bar").css("left", "-1000px");
	$(".dev-bar-title").css("left", "0px");
	$(".dev-bar-title").attr("onclick", "openDevBar()");
	$(".dev-bar-title h2").text("O\np\ne\nn\n \nD\ne\nv\ne\nl\no\np\nm\ne\nn\nt\n \nB\na\nr");
}
</script>