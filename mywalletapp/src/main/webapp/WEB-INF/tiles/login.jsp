<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:url var="authUrl" value="/j_spring_security_check" />

<form id="loginForm" method="post" action="${authUrl}">
	<input id="loginId" type="hidden" value="1" name="id" />
	<div class="form-row">
		<div class="form-field-name"></div>
		<div id="loginpasswordValidate" class="form-field-input error-box">
			<div class="loginpassword" data-condition="">Username/password
				combination is incorrect</div>
		</div>
	</div>
	<div class="form-row">
		<div class="form-field-name">Username</div>
		<div class="form-field-input">
			<input id="username" type="text" maxlength="50" value=""
				name="j_username" />
		</div>
	</div>
	<div class="form-row">
		<div class="form-field-name">Password</div>
		<div class="form-field-input">
			<input id="userpassword" type="password" value="" name="j_password" />
		</div>
	</div>
	<div class="form-row">
		<div class="form-field-name"></div>
		<div class="form-field-input">
			<div class="checkbox-label">
				<label class="checkbox checkbox_style" for="rememberMe"> <input
					id="rememberMe" type="checkbox" value="true" name="rememberMe" />
					<input type="hidden" value="on" name="_spring_security_remember_me" />
				</label>
			</div>
			<div class="checkbox-input">Remember me on this computer</div>
		</div>
	</div>
	<div class="form-row">
		<div class="form-field-name"></div>
		<div class="form-field-input">
			<input id="loginSubmit" class="loginsubmit" type="submit"
				value="Login" name="loginSubmit" />
		</div>
	</div>
</form>