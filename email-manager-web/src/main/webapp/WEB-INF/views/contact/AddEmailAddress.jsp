<script src="/email-manager/js/jquery.form/jquery.form.js"></script>
<script src="/email-manager/js/emanager/Contact.Add.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
						
	});
</script>

	
<legend>Criar novo Contato</legend>

<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Grupo:</label>
			<div class="controls">
				<select id="addEmailAddressGroupList" class="span12">
				</select>
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Nome:</label>
			<div class="controls">
				<input id="addAddressName" type="text" class="span12" placeholder="Informe o nome do contato...">
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">E-mail:</label>
			<div class="controls">
				<input id="addAddressEmail" type="text" class="span12" placeholder="Informe e-mail do contato...">
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<button id="addEmailAddressBtn" class="btn btn-primary" type="button">Criar contato</button>
	</div>
</div>