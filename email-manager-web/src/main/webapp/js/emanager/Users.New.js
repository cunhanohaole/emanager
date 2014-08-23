EManager.NewUser = {

		init : function (){
			EManager.NewUser.newUser();
			EManager.General.loadRoles("#newUserRoleComboBox");
		},
		
		newUser : function() {
			$("#newUserBtn").click(function() {
				var userRole = $("#newUserRoleComboBox").val();
				var username = $("#newUserLogin").val();
				var password = $("#newUserPassword").val();
				var emailAccount = $("#newUserEmailAccount").val();
				//var emailPassword = $("#newUserEmailPassword").val();
				//var hostname = $("#newUserHostname").val();
				var active = $("#newUserActive").is(':checked');
				var isFormValid = EManager.NewUser.validateUser(username, password, emailAccount, null, null);
				if(!isFormValid) return false;
				//var dataString = 'username='+ username + '&password=' + password + '&active=' + active + '&userRole=' + userRole + '&emailAccount=' + emailAccount + '&emailPassword=' + emailPassword + '&hostname=' + hostname;
				var dataString = 'username='+ username + '&password=' + password + '&active=' + active + '&userRole=' + userRole + '&emailAccount=' + emailAccount;
				$.ajax({
					type: "POST",
					url: "/email-manager/users/persist",
					data: dataString,
					async:   false,
					beforeSend: function() {
					},
					success: function(result) {
						EManager.NewUser.clearForm();
						if(result == true){
							notifyMessage("Secesso!", "Usuario criado com sucesso!","success",true);
						}
						else {
							notifyMessage("Erro!", "Ocorreu um erro ao criar este usuario!","error",true);
						}
					}
				});
			});
		},
		
		validateUser : function(username, password, emailAccount, emailPassword, hostname) {
			if(username == ''){
				notifyMessage("Alerta!", "Por favor informe um nome de usuario!","error",true);
				return false;
			}
			if(password == ''){
				notifyMessage("Alerta!", "Por favor informe uma senha!","error",true);
				return false;
			}
			if(emailAccount == ''){
				notifyMessage("Alerta!", "Por favor informe uma conta de e-mail!","error",true);
				return false;
			}
			if(!isValidEmailAddress(emailAccount)){
				notifyMessage("Erro!", emailAccount + " nao e um endereco de email valido!","error",true);
				return false;
			}
//			if(emailPassword == ''){
//				notifyMessage("Alerta!", "Por favor informe a senha do e-mail!","error",true);
//				return false;
//			}
//			if(hostname == ''){
//				notifyMessage("Alerta!", "Por favor informe o hostname do provedor de e-mail!","error",true);
//				return false;
//			}
			return true;
		},
		
		clearForm : function() {
			$("#newUserLogin").val("");
			$("#newUserPassword").val("");
			$('#newUserActive').prop('checked', false);
			$("#newUserEmailAccount").val("");
			$("#newUserEmailPassword").val("");
			$("#newUserHostname").val("");
		}
		
}

EManager.NewUser.init();