EManager.ImportContacts = {
		
		init : function() {
			EManager.ImportContacts.importContacts();
		},
		
		importContacts : function() {
			$("#importContactsBtn").click(function() {
				if($('#importContactsFile').val() == ""){
					notifyMessage("Atencao!", "Escolha um arquivo para importar seus contatos!","error",true);
					return;
				}
				$('#importContactsForm').submit();
			});
			var options = { 
				dataType: "json",
			    success:    function(screenResponse) { 
			        if(screenResponse.error == false) {
			        	$('#importContactsFile').val("");
			        	$('#importContatctsInput').val("");
			        	notifyMessage(screenResponse.title, screenResponse.message,"success",true);
			        }
			        else if(screenResponse.friendlyError == true){
			        	notifyMessage(screenResponse.title, screenResponse.message,"error",true);
			        }
			        else {
			        	notifyMessage(screenResponse.title, screenResponse.message,"error",true);
			        }
			    }
			};
			$('#importContactsForm').ajaxForm(options);
		},
		
		clearForm : function() {
			$("#aditionalEmails").val("");
			$("#subject").val("");
			CKEDITOR.instances.emailTextArea.setData("");
			$.post("/email-manager/email/clearTempAttachments");
			$("#attachmentList tbody").empty();
		}
		
}


EManager.ImportContacts.init();