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

<div id="senderDetail" class="modal hide fade">

	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	</div>
	<div class="modal-body">
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
	</div>
</div>