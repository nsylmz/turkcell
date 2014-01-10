<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="header-container container">
	<div class="navbar-wrapper">
		<div class="container">
			<div class="navbar navbar-inverse navbar-static-top">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="${pageContext.request.contextPath}/UIGeneration">
							Application Creation Environment
						</a>
					</div>
					<div class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
							<li id="UIGeneration">
								<a href="${pageContext.request.contextPath}/UIGeneration">UI Generation</a>
							</li>
							<li id="APIGeneration">
								<a href="${pageContext.request.contextPath}/APIGeneration">API Generation</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		if (document.URL.indexOf("UIGeneration") != -1) {
			$('#UIGeneration').addClass("active");
			$('#APIGeneration').removeClass("active");
		} else if (document.URL.indexOf("APIGeneration") != -1) {
			$('#UIGeneration').removeClass("active");
			$('#APIGeneration').addClass("active");
		}
	});
</script>