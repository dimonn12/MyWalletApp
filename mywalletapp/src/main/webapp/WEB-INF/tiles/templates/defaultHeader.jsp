<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<div class="header">
	<div
		class="home-menu pure-menu pure-menu-open pure-menu-horizontal pure-menu-fixed">
		<a class="pure-menu-heading" href="">My Wallet Application</a>

		<ul>
			<li class="pure-menu-selected"><a href="#">Home</a></li>
			<li><a href="#">Tour</a></li>
			<security:authorize url="/user/**">
				<s:url value="/user/${username}" var="user_url" />
				<li><a href="#"><security:authentication property="principal.username"/></a></li>
			</security:authorize>
			<security:authorize access="isAnonymous()">
				<li><a href="#">Sign In</a></li>
				<li><a href="#">Sign Up</a></li>
			</security:authorize>
			<security:authorize access="isAuthenticated()">
				<li><a href="#">Sign Out</a></li>
			</security:authorize>
		</ul>
	</div>
</div>