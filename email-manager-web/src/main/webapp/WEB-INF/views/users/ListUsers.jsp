<script src="/email-manager/js/emanager/Users.List.js"></script>
<script src="/email-manager/js/emanager/Users.New.js"></script>
<script type="text/javascript">
	
</script>
	
<legend>Usuarios</legend>

<br />
<div class="row-fluid">
	<table id="usersListGrid">
	</table>
</div>

<div id="editUserModal" class="modal hide fade">
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>Editar usuario</h3>
	</div>
	<div class="modal-body">
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group">
					<label class="control-label">Perfil do usuario</label>
					<div class="controls">
						<select id="editUserRoleComboBox"></select>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<div class="control-group">
					<label class="control-label">Login:</label>
					<div class="controls">
						<input id="editUserLogin" type="text" class="span12" placeholder="Informe o login do usuario..." disabled="disabled">
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<div class="control-group">
					<label class="control-label">Senha:</label>
					<div class="controls">
						<input id="editUserPassword" type="password" class="span12" placeholder="Informe a senha do usuario...">
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<div class="control-group">
					<label class="control-label">E-mail:</label>
					<div class="controls">
						<input id="editUserEmailAccount" type="text" class="span12" placeholder="Informe a conta de e-mail...">
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group">
					<label class="control-label">O usario esta ativo?</label>
					<div class="controls">
						<input type="checkbox" id="editUserActive" />
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<button id="editUserBtn" class="btn btn-primary" type="button">Confirmar</button>
			</div>
		</div>
		
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
	</div>
</div>