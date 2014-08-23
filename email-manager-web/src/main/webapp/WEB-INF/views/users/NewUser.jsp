<script src="/email-manager/js/jquery.form/jquery.form.js"></script>
<script src="/email-manager/js/emanager/Users.New.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
						
	});
</script>

	
<legend>Criar novo Usuario</legend>

<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Perfil do usuario</label>
			<div class="controls">
				<select id="newUserRoleComboBox"></select>
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Login:</label>
			<div class="controls">
				<input id="newUserLogin" type="text" class="span12" placeholder="Informe o login do usuario...">
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Senha:</label>
			<div class="controls">
				<input id="newUserPassword" type="password" class="span12" placeholder="Informe a senha do usuario...">
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">E-mail:</label>
			<div class="controls">
				<input id="newUserEmailAccount" type="text" class="span12" placeholder="Informe a conta de e-mail...">
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">O usario esta ativo?</label>
			<div class="controls">
				<input type="checkbox" id="newUserActive" />
			</div>
		</div>
	</div>
</div>

<!-- <legend>Informacoes sobre a conta de E-mail</legend>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Senha:</label>
			<div class="controls">
				<input id="newUserEmailPassword" type="password" class="span12" placeholder="Informe a senha do e-mail...">
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Hostname:</label>
			<div class="controls">
				<input id="newUserHostname" type="text" class="span12" value="mail.jcvcomercial.com.br" placeholder="Informe o hostname (Ex: mail.jcvcomercial.com.br)...">
			</div>
		</div>
	</div>
</div> -->
<div class="row-fluid">
	<div class="span12">
		<button id="newUserBtn" class="btn btn-primary" type="button">Criar Usuario</button>
	</div>
</div>