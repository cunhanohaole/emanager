EManager.AddGroup = {

		init : function (){
			EManager.AddGroup.addGroup();
		},
		
		addGroup : function() {
			$("#addGroupBtn").click(function() {
				var title = $("#addGroupTitle").val();
				var active = $("#addActiveGroup").is(':checked');
				var dataString = 'title='+ title + '&active=' + active;
				$.ajax({
					type: "POST",
					url: "/email-manager/contacts/addGroup",
					data: dataString,
					async:   false,
					beforeSend: function() {
						notifyMessage("Alerta!", "Seu grupo estaa sendo criado, aguarde...","info",true);
					},
					success: function(result) {
						EManager.AddGroup.clearForm();
						if(result == true){
							notifyMessage("Secesso!", "Grupo criado com sucesso!","success",true);
						}
						else {
							notifyMessage("Erro!", "Ocorreu um erro ao criar este grupo!","error",true);
						}
					}
				});
			});
		},
		
		clearForm : function() {
			$("#addGroupTitle").val("");
			$('#addActiveGroup').prop('checked', false);
		}
		
}

EManager.AddGroup.init();