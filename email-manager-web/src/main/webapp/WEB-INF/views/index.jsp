<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@ include file="commonscripts.jsp"%>
		<%@ include file="commonstylesheets.jsp"%>	
		<script src="/email-manager/js/emanager/Main.js"></script>	
		<title>E-mail Manager</title>
	</head>
	<body>
		
		<div class="nav navbar-inner" style="padding: 10px;">
			<div id="main" class="container-fluid navbar navbar-inner">
			
				<a class="brand" href="#" style="padding: 15px;">E-mail Manager</a>	
				<ul class="nav" id="companyBar">
					<li class="divider-vertical"></li>
				</ul>
				<ul class="nav pull-right">
					<li>
						<div id="controls">
							<div id="account">
								<p class="navbar-text">
									Oi,&nbsp;
									<span id="username">
										<sec:authentication property="principal.username" />
									</span>
									&nbsp;<a href="javascript:logutFromApplication()">Logout</a>
								</p>
							</div>
						</div>
					</li>
				</ul>	
			
			</div>
			
			<div class="row-fluid">
				<div class="span2">
					<ul class="nav navbar-inner nav-list" id="mainMenuTab">
						<li class="nav-header">E-mail</li>
						<li class="active"><a href="#homeDiv" data-toggle="tab">Home</a></li>
						<li><a id="newEmailLink" href="#newEmailDiv" data-toggle="tab">Criar novo email</a></li>
						<li><a href="#sentEmailsDiv" data-toggle="tab" onclick="EManager.Main.listSentEmails()">E-mails enviados</a></li>
						<li><a href="#signaturesDiv" data-toggle="tab" onclick="EManager.Main.listSignatures()">Minhas assinaturas</a></li>
						<li class="divider"></li>
						<li class="nav-header">Contatos</li>
						<li><a href="#addGroupDiv" data-toggle="tab" onclick="EManager.Main.showAddGroup()">Criar novo Grupo</a></li>
						<li><a href="#addEmailAddressDiv" data-toggle="tab" onclick="EManager.Main.showAddEmailAddress()">Criar novo Contato</a></li>
						<li><a href="#listEmailAddressDiv" data-toggle="tab" onclick="EManager.Main.showListEmailAddress()">Lista de Contatos</a></li>
						<li><a href="#importContactsDiv" data-toggle="tab" onclick="EManager.Main.showImportContacts()">Importar Contatos</a></li>
						<li class="divider"></li>
						<sec:authorize access="hasRole('ROLE_ADM')">
							<li class="nav-header">Usuarios</li>
							<li><a href="#newUserDiv" data-toggle="tab" onclick="EManager.Main.showNewUser()">Criar usuario</a></li>
							<li><a href="#listUsersDiv" data-toggle="tab" onclick="EManager.Main.showUsersList()">Lista de usuarios</a></li>
							<li><a href="#contactAccessDiv" data-toggle="tab" onclick="EManager.Main.showContactAccess()">Acesso a Contatos</a></li>
							<li><a href="#sendersManagementDiv" data-toggle="tab" onclick="EManager.Main.showSendersManagement()">Cadastro de enviadores</a></li>
							<li class="divider"></li>
						</sec:authorize>
					</ul>
				</div>
				<div class="tab-content span10">
					<div class="tab-pane active" id="homeDiv">
					
						<h1>Bem vindo ao E-Mail Manager</h1>
						Selecione oque deseja fazer atraves do menu a esquerda.<br />
						Lembre-se de que todas as suas acoes serao registradas em nome do seu usuario.
					
					</div>
					<div class="tab-pane" id="newEmailDiv">
					</div>
					<div class="tab-pane" id="sentEmailsDiv">
					</div>
					<div class="tab-pane" id="signaturesDiv">
					</div>
					<div class="tab-pane" id="addGroupDiv">
					</div>
					<div class="tab-pane" id="addEmailAddressDiv">
					</div>
					<div class="tab-pane" id="listEmailAddressDiv">
					</div>
					<div class="tab-pane" id="importContactsDiv">
					</div>
					<div class="tab-pane" id="newUserDiv">
					</div>
					<div class="tab-pane" id="listUsersDiv">
					</div>
					<div class="tab-pane" id="contactAccessDiv">
					</div>
					<div class="tab-pane" id="sendersManagementDiv">
                    </div>
				</div>
			</div>
		</div>
		<footer class="container pagination-centered footer">
			<div class="row-fluid" align="center">
				<b> Build: 2.0.4</b>
			</div>
			<div class="row-fluid" align="center">
				F-Alves Information Technology.
			</div>
		</footer>
	</body>
</html>