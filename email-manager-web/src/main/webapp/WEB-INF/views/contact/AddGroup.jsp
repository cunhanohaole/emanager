<script src="/email-manager/js/jquery.form/jquery.form.js"></script>
<script src="/email-manager/js/emanager/Contact.AddGroup.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
						
	});
</script>

	
<legend>Criar novo Grupo</legend>

<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">Nome do grupo:</label>
			<div class="controls">
				<input id="addGroupTitle" type="text" class="span12" placeholder="Informe o nome do grupo...">
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="control-group">
			<label class="control-label">O grupo esta ativo?</label>
			<div class="controls">
				<input type="checkbox" id="addActiveGroup" />
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<button id="addGroupBtn" class="btn btn-primary" type="button">Criar grupo</button>
	</div>
</div>