<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script src="/email-manager/js/emanager/Contact.List.js"></script>
<script type="text/javascript">
	
</script>
	
<legend>Contatos</legend>

<div class="row-fluid">
	<div class="span8">
		<div class="control-group">
			<label class="control-label">Grupo:</label>
			<div class="controls">
				<div class="span4">
					<select id="emailAddressesGroupComboBox">
					</select>
				</div>
				<div class="span4">
					<sec:authorize access="hasRole('ROLE_ADM')">
						<button id="deleteGroupBtn" class="btn btn-small btn-danger">Excluir grupo</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</div>
</div>

<br />
<div class="row-fluid">
	<table id="emailAddressesGrid">
	</table>
	<div id="contactListGridPager"></div> 
</div>

<div id="editContactModal" class="modal hide fade">
	
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>Editar contato</h3>
	</div>
	<div class="modal-body">
		
		<div class="row-fluid">
			<div class="span8">
				<div class="control-group">
					<label class="control-label">Grupo:</label>
					<div class="controls">
						<select id="editEmailAddressGroupList" class="span12">
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<div class="control-group">
					<label class="control-label">Nome:</label>
					<div class="controls">
						<input id="editContactId" type="hidden" />
						<input id="editAddressName" type="text" class="span12" placeholder="Informe o nome do contato...">
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<div class="control-group">
					<label class="control-label">E-mail:</label>
					<div class="controls">
						<input id="editAddressEmail" type="text" class="span12" placeholder="Informe e-mail do contato...">
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<button id="addEmailAddressBtn" class="btn btn-primary" type="button">Confirmar</button>
			</div>
		</div>
		
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Fechar</button>
	</div>
</div>