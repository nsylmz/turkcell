<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div id="htmlPallet-container" class="container">
	<div class="dev-bar-title bar-transition" onclick="openDevBar()">
		<h2></h2>
	</div>
	<div class="dev-bar bar-transition">
		<div class="dev-menu-container">
			<div id="dev-tabs" class="dev-menu">
				<ul>
					<li><a href="#menus">Manage Menus</a></li>
					<li><a href="#menuItems">Manage Menu Items</a></li>
					<li><a href="#pages">Manage Pages</a></li>
					<li><a href="#entities">Manage Entities</a></li>
					<li><a href="#fields">Manage Fields</a></li>
				</ul>
				<div id="menus" class="container">
					<tiles:insertAttribute name="menuTab"/>
				</div>
				<div id="menuItems">
					<tiles:insertAttribute name="menuItemTab"/>
				</div>
				<div id="pages">
					<tiles:insertAttribute name="pageTab"/>
				</div>
				<div id="entities">
					<tiles:insertAttribute name="entityTab"/>
				</div>
				<div id="fields">
					<tiles:insertAttribute name="fieldTab"/>
				</div>
			</div>
		</div>
    </div>
</div>

<script type="text/javascript">
var appId = "${appId}";
var basePath = "${pageContext.request.contextPath}";
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
