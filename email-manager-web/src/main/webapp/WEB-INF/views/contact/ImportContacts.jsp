<script src="/email-manager/js/jquery.form/jquery.form.js"></script>
<script src="/email-manager/js/emanager/Contact.Import.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input[id=importContactsFile]").change(function(){
			$("#importContatctsInput").val($(this).val());
		});			
	});
</script>

	
<legend>Importar contatos de arquivo</legend>

<div class="row-fluid">
	<div class="span4">
		<form id="importContactsForm" action="/email-manager/contacts/importContacts" enctype="multipart/form-data" method="post">
			<div class="row-fluid">
				<label>Selecione o arquivo para importar os contatos:</label>
			</div>
			<div class="row-fluid">
				<input name="importContactsFile" id="importContactsFile" type="file" style="display: none;">
				<div class="input-append">
					<input id="importContatctsInput" type="text" class="span8" onclick="$('input[id=importContactsFile]').click();" />
					<a class="btn" onclick="$('input[id=importContactsFile]').click();">Procurar</a>
				</div>
			</div>
			<div class="row-fluid">
				<button id="importContactsBtn" class="btn" type="button">Importar</button>
			</div>
		</form>		
	</div>
</div>