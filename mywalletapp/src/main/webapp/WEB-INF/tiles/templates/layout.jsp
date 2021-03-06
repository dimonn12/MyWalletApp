<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="s"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:set var="root" value="" scope="request" />
<c:set var="img" value="${root}/resources/images" scope="request" />
<c:set var="css" value="${root}/resources/css" scope="request" />
<c:set var="js" value="${root}/resources/jscript" scope="request" />
<!DOCTYPE html>

<html>

<head>
<link rel="stylesheet" href="<c:url value="${css}/pure-css.css" />" />
<link rel="stylesheet" href="<c:url value="${css}/pure-layout.css" />" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><s:insertAttribute name="title" ignore="true" /></title>
</head>
<body>



	<div class="splash-container">
		<div class="splash">
			<h1 class="splash-head">New application for wallet control</h1>
			<p class="splash-subhead">{submessage}</p>
			<p>
				<a href="#" class="pure-button pure-button-primary">Get Started</a>
			</p>
		</div>
	</div>

	<div class="content-wrapper">
		<div class="content">
			<h2 class="content-head is-center">Excepteur sint occaecat
				cupidatat.</h2>

			<div class="pure-g">
				<div class="l-box pure-u-1 pure-u-md-1-2 pure-u-lg-1-4">

					<h3 class="content-subhead">
						<i class="fa fa-rocket"></i> Get Started Quickly
					</h3>
					<p>{getstartedmessage}.</p>
				</div>
				<div class="l-box pure-u-1 pure-u-md-1-2 pure-u-lg-1-4">
					<h3 class="content-subhead">
						<i class="fa fa-mobile"></i> Easy to use
					</h3>
					<p>{easytouse}.</p>
				</div>
				<div class="l-box pure-u-1 pure-u-md-1-2 pure-u-lg-1-4">
					<h3 class="content-subhead">
						<i class="fa fa-th-large"></i> You can use it for free
					</h3>
					<p>{freemodel}</p>
				</div>
				<div class="l-box pure-u-1 pure-u-md-1-2 pure-u-lg-1-4">
					<h3 class="content-subhead">
						<i class="fa fa-check-square-o"></i> Nice views
					</h3>
					<p>{views}.</p>
				</div>
			</div>
		</div>

<!-- 		<div class="ribbon l-box-lrg pure-g">
			<div class="l-box-lrg is-center pure-u-1 pure-u-md-1-2 pure-u-lg-2-5">
				<img class="pure-img-responsive" alt="File Icons" width="300"
					src="img/common/file-icons.png">
			</div>
			<div class="pure-u-1 pure-u-md-1-2 pure-u-lg-3-5">

				<h2 class="content-head content-head-ribbon">Laboris nisi ut
					aliquip.</h2>

				<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
					do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
					enim ad minim veniam, quis nostrud exercitation ullamco laboris
					nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor.</p>
			</div>
		</div> -->

		<div class="content">
			<h2 class="content-head is-center">You can start quickly after simple registration.</h2>

			<div class="pure-g">
				<div class="l-box-lrg pure-u-1 pure-u-md-2-5">
					<form class="pure-form pure-form-stacked">
						<fieldset>

							<label for="name">Your Name</label> <input id="name" type="text"
								placeholder="Your Name"> <label for="email">Your
								Email</label> <input id="email" type="email" placeholder="Your Email">

							<label for="password">Your Password</label> <input id="password"
								type="password" placeholder="Your Password">

							<button type="submit" class="pure-button">Sign Up</button>
						</fieldset>
					</form>
				</div>

				<div class="l-box-lrg pure-u-1 pure-u-md-3-5">
					<h4>Contact Us</h4>
					<p>{contactinfo}</p>

					<h4>More Information</h4>
					<p>{some additional information}</p>
				</div>
			</div>

		</div>

		<div class="footer l-box is-center">
			Developerd by Dmitry Shanko <br />Minsk, Belarus. 2014
		</div>

	</div>

</body>
</html>