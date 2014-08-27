<script src="/email-manager/js/jquery.form/jquery.form.js"></script>
<script src="/email-manager/js/emanager/Email.New.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		CKEDITOR.config.entities = false;
		
		CKEDITOR.replace( 'emailTextArea', {
			height: 500
		});

		EManager.NewEmail.clearForm();
		
		$("input[id=thefile]").change(function(){
			$("#attachment").val($(this).val());
		});
		
	});
</script>

	
<legend>Criar novo email</legend>

<div class="row-fluid">
	<div class="span12">
	    <div class="row-fluid">
	        <div class="span4">
	            <div class="control-group">
                    <label class="control-label">De:</label>
                    <div class="controls">
                        <select id="senderList" class="span12">
                        </select>
                    </div>
                </div>
	        </div>
	    </div>
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group">
					<label class="control-label">Para:</label>
					<div class="controls">
						<select id="newEmailGroupList" class="span12">
						</select>
					</div>
				</div>
			</div>
			<div class="span8">
				<div class="control-group">
					<label class="control-label">Destinos adicionais:</label>
					<div class="controls">
						<input id="aditionalEmails" type="text" class="span12" placeholder="Deseja adicionar outros emails? Separados por ; (Ex: teste@teste.com.br;teste2@teste.com.br)">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="control-group">
		<label class="control-label">Assunto:</label>
		<div class="controls">
			<input id="subject" type="text" class="span8" placeholder="Digite o assunto do email...">
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<button id="sendEmailBtn" class="btn btn-primary" type="button">Enviar</button>
		<span id="newEmailSignature" class="hide">
			<button id="insertSignatureBtn" class="btn" type="button">Inserir assinatura</button>
			<select id="newEmailSigComboBox"></select>
		</span>
	</div>
</div>
<br/>
<div class="row-fluid">
	<div class="span8">
		<textarea id="emailTextArea" name="emailTextArea" rows="15"></textarea>
	</div>
	<div class="span4">
		<form id="attachmentsForm" action="/email-manager/email/addAtachment" enctype="multipart/form-data" method="post">
			<div class="row-fluid">
				<label>Anexos:</label>
			</div>
			<div class="row-fluid">
				<input name="thefile" id="thefile" type="file" style="display: none;">
				<div class="input-append">
					<input id="attachment" type="text" class="span8" onclick="$('input[id=thefile]').click();" />
					<a class="btn" onclick="$('input[id=thefile]').click();">Procurar</a>
				</div>
			</div>
			<div class="row-fluid">
				<button id="addFileBtn" class="btn" type="button">Adicionar</button>
			</div>
		</form>
		<div class="row-fluid">
			<div class="span12">
				<table id="attachmentList" class="table table-striped">
					<thead>
						<tr>
							<td class="span1"><strong>#</strong></td>
							<td class="span9"><strong>Arquivo</strong></td>
							<td class="span2"></td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>