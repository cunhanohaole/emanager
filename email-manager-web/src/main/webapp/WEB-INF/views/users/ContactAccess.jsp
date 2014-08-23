<script src="/email-manager/js/emanager/Users.Contact.Access.js"></script>
<script type="text/javascript">
	
</script>
	
<legend>Definicao de acesso aos contatos</legend>

<div class="row-fluid">
	<div class="control-group">
		<label>Usuario:</label>
		<div class="controls">
			<select id="contactAccessUsers" class="span4"></select>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="control-group">
		<label>Grupos de contatos: <i>(Selecione os grupos que este usuario pode acessar. Use a tecla Ctrl para selecionar mais de um)</i></label>
		<div class="controls">
			<select id="accessContactGroups" multiple="multiple" class="span4" size=20></select>	
		</div>
	</div>
</div>
<div class="row-fluid">
	<button class="btn labelSpace" type="button" id="btnSaveContactAccess">Salvar</button>
</div>