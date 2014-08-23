<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>Login</title>
<%@ include file="commonscripts.jsp"%>
<%@ include file="commonstylesheets.jsp"%>
</head>
<body style="padding: 10px; background-color: #F5F5F5;">
	<h4>E-mail Manager</h4>
	<div class="container-fluid mainContainer navbar-inner tab-content">
		<div>
			<h4>Autenticacao</h4>
			<div class="span5">
				<div>
					<form id="loginForm" name="loginForm" method="post"
						action="j_spring_security_check" onsubmit="clearAllCookies()">
						<div id="loginControlGroup" class="control-group">
						<div class="row-fluid qd">
							<div class="span9">
							
								<label class="control-label" for="j_username"><i
									class="mandatory"></i>Usuario</label> <input type="text"
									class="span12" id="j_username" name="j_username"
									placeholder="Username">
								
							</div>
						</div>
						<div class="row-fluid qd">
							<div class="span9">
							
								<label class="control-label" for="j_password"><i
									class="mandatory"></i>Senha</label> <input type="password"
									class="span12" name="j_password" id="j_password"
									placeholder="Password">
									</div>
							</div>
						</div>
						<div class="row-fluid qd">

							<button type="submit" name="submit" class="btn labelSpace">Login</button>
							<button type="reset" name="reset" class="btn labelSpace" onclick="javascript:$('#loginControlGroup').removeClass('error');">Reset</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<footer class="container pagination-centered footer">
		<div class="row-fluid" align="center">
			<b> Build: 2.0.4</b>
		</div>
		<div class="row-fluid" align="center">
			© F-Alves Information Technology.
		</div>
	</footer>
</body>
</html>
