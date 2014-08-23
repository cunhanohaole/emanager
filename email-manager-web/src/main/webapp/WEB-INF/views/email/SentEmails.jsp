<script src="/email-manager/js/emanager/Email.Sent.js"></script>
<script type="text/javascript">
	
</script>
<style>
	#sentEmailDetails .modal-body {
		
	}
</style>
	
<legend>E-mails enviados</legend>

<div class="row-fluid">
	<table id="sentEmailsGrid">
	</table>
	<div id="sentEmailsGridPager"></div> 
</div>

<div id="sentEmailDetails" class="modal hide fade">
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3 id="emailDetailSubject"></h3>
		<input type="hidden" id="emailId" />
	</div>
	<div class="modal-body">
		<div id="emailDetailBody">
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn" id="forwardEmail">Encaminhar email</button>
		<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
	</div>
</div>