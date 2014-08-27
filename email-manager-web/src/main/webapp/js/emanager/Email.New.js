EManager.NewEmail = {
		
		init : function() {
			EManager.NewEmail.addFile();
			EManager.NewEmail.removeAttachment();
			EManager.General.loadGroupsForUser("#newEmailGroupList");
			EManager.General.loadSendersForUser("#senderList");
			EManager.NewEmail.sendEmail();
			EManager.NewEmail.loadSignatures();
			EManager.NewEmail.insertSignature();
		},
		
		addFile : function() {
			$("#addFileBtn").click(function() {
				if($('#thefile').val() == ""){
					notifyMessage("Atencao!", "Escolha um arquivo para anexar ao email!","error",true);
					return;
				}
				$('#attachmentsForm').submit();
			});
			var options = { 
			    success:    function(result) { 
			        if(result == true) {
			        	$('#thefile').val("");
			        	$('#attachment').val("");
			        	EManager.NewEmail.loadAttachments();
			        }
			        else console.log("fail");
			    },
			    uploadProgress: function(event, position, total, percentComplete) {
					//console.log(event + ' ' + position + ' ' + total + ' ' + percentComplete);
				}
			};
			$('#attachmentsForm').ajaxForm(options);
		},
		
		loadAttachments : function() {
			$.get("/email-manager/email/getAttachments", function(data){
        		var attachments = data.rows;
        		if(attachments != null && attachments.length > 0){
        			var tableRows = ""; 
            		for(var i = 0; i < attachments.length; i++){
            			var fileName = attachments[i].originalFileName;
            			tableRows += "<tr><td>" + (i + 1) + "</td><td>" + fileName + "</td><td><i id='removeAttachment_" + i + "' class='icon-remove remove-attachment' /></td></tr>";
            		}
            		$("#attachmentList tbody").html(tableRows);
        		}
        		else {
        			$("#attachmentList tbody").html("<tr><td colspan='3'>Nenhum anexo adicionado ate o momento!</td></tr>");
        		}
        	});
		},
		
		loadPreviousEmail : function(emailId) {
			var dataString = "emailId=" + emailId;
			$.ajax({
				type: "GET",
				url: "/email-manager/email/loadPreviousEmailAttachments",
				data: dataString,
				async:   false,
				success: function(result) {
				}
			});
			EManager.NewEmail.loadAttachments();
			$.ajax({
				type: "GET",
				url: "/email-manager/email/getEmail",
				data: dataString,
				async:   false,
				success: function(email) {
					$("#subject").val(email.subject);
					CKEDITOR.instances.emailTextArea.setData(email.bodyString);
				}
			});
		},
		
		removeAttachment : function() {
			$(".remove-attachment").die('click').live("click", function() {
				var id = $(this).attr("id");
				var index = id.split("_")[1];
				var dataString = 'attachmentIndex='+ index;
				$.ajax({
					type: "POST",
					url: "/email-manager/email/removeAttachment",
					data: dataString,
					async:   false,
					success: function(result) {
						if(result == true){
							EManager.NewEmail.loadAttachments();
						}
					}
				});
				
			});
		},
		
		sendEmail : function() {
			$("#sendEmailBtn").click(function() {
			    var senderId = $("#senderList").val();
				var invalidEmails = EManager.NewEmail.getInvalidEmails();
				if(invalidEmails != null){
					notifyMessage("Erro!", "Os seguintes enderecos de email sao invalidos: " + invalidEmails + "","error",true);
					return false;
				}
				var subject = $("#subject").val();
				if(subject == ''){
					notifyMessage("Alerta!", "Por favor, escolha um assunto para seu email!","error",true);
					return false;
				}
				var emailBody = CKEDITOR.instances.emailTextArea.getData();
				if(emailBody == ''){
					notifyMessage("Alerta!", "O corpo do email esta vazio!","error",true);
					return false;
				}
				var groupId = $("#newEmailGroupList").val();
				var additionalEmails = $("#aditionalEmails").val();
				var dataString = 'senderId=' + senderId + '&groupId='+ groupId + '&additionalEmails=' + additionalEmails + '&emailSubject=' + subject + '&emailBody=' + encodeURIComponent(emailBody);
				$.ajax({
					type: "POST",
					url: "/email-manager/email/send",
					data: dataString,
					async:   false,
					beforeSend: function() {
						notifyMessage("Alerta!", "Seu email esta sendo enviado... Quando o envio estiver concluido, ele ficara branco na lista de e-mails enviados!","info",true);
					},
					success: function(result) {
						EManager.NewEmail.clearForm();
						if(result == true){
							EManager.NewEmail.clearForm();
							EManager.Main.listSentEmails();
						}
						else {
							notifyMessage("Erro!", "Ocorreu um erro ao enviar este email!","error",true);
						}
					}
				});
			});
		},
		
		getInvalidEmails : function() {
			var additionalEmails = $("#aditionalEmails").val();
			var additionalEmailsArray = additionalEmails.split(";");
			var invalidEmails = "";
			var containWrongEmail = false;
			for(i = 0; i < additionalEmailsArray.length; i++){
				if(!isValidEmailAddress(additionalEmailsArray[i]) && additionalEmailsArray[i] != ""){
					invalidEmails += additionalEmailsArray[i] + ";<br />";
					containWrongEmail = true;
				}
			}
			if(containWrongEmail) return invalidEmails;
			else return null;
		},
		
		loadSignatures : function() {
			$.get("/email-manager/users/listSignatures", function(data){
        		var signatures = data.rows;
        		if(signatures != null && signatures.length > 0){
        			$('#newEmailSignature').show();
        			for(i in signatures) {
        				var title = signatures[i].title;
        				var id = signatures[i].idSignature; 
        				$('#newEmailSigComboBox').append(new Option(title, id));
        			}
        		}
        	});
		},
		
		insertSignature : function() {
			$('#insertSignatureBtn').click(function(){
				var idSignature = $('#newEmailSigComboBox').val();
				$.get("/email-manager/users/loadSignature?idSignature=" + idSignature, function(signature){
					CKEDITOR.instances.emailTextArea.setData(signature.htmlSignature);
	        	});
			});
		},
		
		clearForm : function() {
			$("#aditionalEmails").val("");
			$("#subject").val("");
			CKEDITOR.instances.emailTextArea.setData("");
			$.post("/email-manager/email/clearTempAttachments");
			$("#attachmentList tbody").empty();
		}
		
}


EManager.NewEmail.init();