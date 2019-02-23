<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<div class="navitagorDiv">
	<nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
		<img style="margin-left:10px;margin-right:0px" class="pull-left" src="img/site/tmallbuy.png" height="45px">
		<a class="navbar-brand" href="#nowhere">天猫后台</a>
		
		<a class="navbar-brand" href="admin_category_list">分类管理</a>
		<a class="navbar-brand" href="admin_user_list">用户管理</a>
		<a class="navbar-brand" href="admin_order_list">订单管理</a>

		<ul class="nav navbar-nav navbar-right">

			<li>
				<c:if test="${!empty user}">
				<a href="/admin_login">${user.name}</a>
				</c:if>
			</li>
			<li><a href="/admin_logout"><span class="glyphicon glyphicon-log-out"></span> 退出</a></li>

		</ul>
</nav>
</div>