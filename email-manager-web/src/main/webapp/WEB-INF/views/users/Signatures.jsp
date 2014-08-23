<script src="/email-manager/js/emanager/Signatures.List.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		CKEDITOR.config.entities = false;
		
		CKEDITOR.replace( 'signatureTextArea', {
			height: 350
		});
		
	});
</script>
<style>
body #editSignatureModal {
    width: 900px;
    margin-left: -450px;
}
</style>
	
<legend>Minhas assinaturas</legend>

<br />
<div class="row-fluid" style="margin-bottom: 10px;">
	<button id="newSignatureBtn" class="btn btn-primary pull-right" type="button">Nova Assinatura</button>
</div>
<div class="row-fluid">
	<table id="signaturesListGrid">
	</table>
</div>

<div id="editSignatureModal" class="modal hide fade">
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>Editar Assinatura</h3>
	</div>
	<div class="modal-body">
		<input type="hidden" id="idSignature" />
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group">
					<label class="control-label">Titulo:</label>
					<div class="controls">
						<input id="editSignatureTitle" type="text" class="span12" placeholder="Informe o titulo desta assinatura...">
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="control-group">
					<label class="control-label">Assinatura: <i>(Insira e formate sua assinatura aqui)</i></label>
					<div class="controls">
						<textarea id="signatureTextArea" name="signatureTextArea" rows="10"></textarea>	
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button id="saveSignatureBtn" class="btn btn-primary" type="button">Salvar</button>
		<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
	</div>
</div>