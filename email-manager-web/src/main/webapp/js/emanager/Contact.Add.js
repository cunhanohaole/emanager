EManager.AddEmailAddress = {

		init : function (){
			EManager.AddEmailAddress.addEmailAddress();
			EManager.General.loadGroups("#addEmailAddressGroupList");
		},
		
		addEmailAddress : function() {
			$("#addEmailAddressBtn").click(function() {
				var groupId = $("#addEmailAddressGroupList").val();
				var name = $("#addAddressName").val();
				var email = $("#addAddressEmail").val();
				if(!isValidEmailAddress(email)){
					notifyMessage("Erro!", email + " nao e um endereco de email valido!","error",true);
					return false;
				}
				var dataString = 'name='+ name + '&address=' + email + '&groupId=' + groupId;
				$.ajax({
					type: "POST",
					url: "/email-manager/contacts/addEmailAddress",
					data: dataString,
					async:   false,
					beforeSend: function() {
						notifyMessage("Alerta!", "Seu contato estaa sendo criado, aguarde...","info",true);
					},
					success: function(result) {
						EManager.AddEmailAddress.clearForm();
						if(result == true){
							notifyMessage("Secesso!", "Contato criado com sucesso!","success",true);
						}
						else {
							notifyMessage("Erro!", "Ocorreu um erro ao criar este contato!","error",true);
						}
					}
				});
			});
		},
		
		clearForm : function() {
			$("#addAddressName").val("");
			$("#addAddressEmail").val("");
			EManager.General.loadGroups("#addEmailAddressGroupList");
		}
		
}

EManager.AddEmailAddress.init();