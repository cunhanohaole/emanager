<script src="/email-manager/js/emanager/Sender.Management.js"></script>
<script type="text/javascript">
</script>
<style>
</style>

<legend>Cadastro de Enviadores</legend>

<div class="row-fluid">
    <div class="span4">
        <div class="control-group">
            <label class="control-label">De:</label>
            <div class="controls">
                <select id="senderManagementUser" class="span12">
                </select>
            </div>
        </div>
    </div>
</div>

<div class="row-fluid">
	<table id="sendersGrid">
	</table>
</div>

<div class="row-fluid">
&nbsp;
</div>

<div class="row-fluid">
    <div class="span12">
        <button id="newSenderConfigBtn" class="btn pull-right" type="button">Criar novo</button>
    </div>
</div>

<div id="editSenderDetail" class="modal hide fade">

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>Editar Enviador</h3>
	</div>
	<div class="modal-body">
	    <div class="row-fluid">
            <div class="span4">
                <div class="control-group">
                    <label class="control-label">Id</label>
                    <div class="controls">
                        <input type="text" id="editSenderConfigId" readonly class="span4" />
                    </div>
                </div>
            </div>
        </div>
	    <div class="row-fluid">
            <div class="span8">
                <div class="control-group">
                    <label class="control-label">E-mail</label>
                    <div class="controls">
                        <input type="text" id="editSenderConfigFrom" class="span12" />
                    </div>
                </div>
            </div>
        </div>
	</div>
	<div class="modal-footer">
	    <button id="deleteSenderConfigBtn" class="btn btn-danger" type="button">Apagar</button>
		<button id="editSenderConfigBtn" class="btn btn-primary" type="button">Salvar</button>
		<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
	</div>
</div>

<div id="newSenderModal" class="modal hide fade">

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>Criar novo Enviador</h3>
	</div>
	<div class="modal-body">
        <div class="row-fluid">
            <div class="span4">
                <div class="control-group">
                    <label class="control-label">Usuario:</label>
                    <div class="controls">
                        <select id="newSenderUser" class="span12">
                        </select>
                    </div>
                </div>
            </div>
        </div>
	    <div class="row-fluid">
            <div class="span8">
                <div class="control-group">
                    <label class="control-label">Informe o endereco de e-mail:</label>
                    <div class="controls">
                        <input type="text" id="newSenderConfigFrom" class="span12" />
                    </div>
                </div>
            </div>
        </div>
	</div>
	<div class="modal-footer">
		<button id="saveNewSenderConfigBtn" class="btn btn-primary" type="button">Salvar</button>
		<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
	</div>
</div>