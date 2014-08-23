EManager.UsersContactAccess = {
		
		init : function() {
			EManager.UsersContactAccess.loadUsers();
			EManager.UsersContactAccess.loadAllContactGroups();
			EManager.UsersContactAccess.onSelectUser();
			EManager.UsersContactAccess.saveContactAccess();
			EManager.UsersContactAccess.populateCotactAccess();
		},

		loadUsers : function() {
			$.ajax({
	            type: "GET",
	            url: "/email-manager/users/list",
	            dataType: "json",
	            async: false,
	            success: function(result)
	            {
	            	var users = result.rows;
	                for(i in users) {
	                	var username = users[i].username;
	                	$("#contactAccessUsers").append(new Option(username, username));
	                }
	            }
	        });
		},
		
		listUserGroups : function(username) {
			var userGroups = null;
			$.ajax({
	            type: "GET",
	            url: "/email-manager/users/listUserGroups?username=" + username,
	            dataType: "json",
	            async: false,
	            success: function(result)
	            {
	            	userGroups = result.rows;
	            }
	        });
			return userGroups;
		},
		
		loadAllContactGroups : function() {
			$.ajax({
	            type: "GET",
	            url: "/email-manager/contacts/listGroups",
	            dataType: "json",
	            async: false,
	            success: function(result)
	            {
	            	var groups = result.rows;
	            	for(i in groups) {
	            		var title = groups[i].title;
	            		var id = groups[i].id;
	            		$("#accessContactGroups").append(new Option(title, id));
	            	}
	            }
	        });
		},
		
		onSelectUser : function() {
			$("#contactAccessUsers").live('change', function(){
				EManager.UsersContactAccess.populateCotactAccess();
			});
		},
		
		populateCotactAccess : function() {
			$("#accessContactGroups option:selected").prop("selected", false);
			var username = $("#contactAccessUsers").val();
			var userGroups = EManager.UsersContactAccess.listUserGroups(username);
			for(i in userGroups) {
				$('#accessContactGroups option[value=' + userGroups[i].id + ']').attr('selected', true);
			}
		},
		
		saveContactAccess : function () {
			$("#btnSaveContactAccess").live('click', function(){
				var selectedOptions = $('#accessContactGroups').val();
				var jsonData = JSON.stringify(selectedOptions);
				var username = $("#contactAccessUsers").val();
				$.ajax({
		            type : "POST",
		            url : "/email-manager/users/saveContactAccess?username=" + username,
		            data : jsonData,
		            contentType : "application/json; charset=utf-8",
		            dataType : "json",
		            async : false,
		            success : function(result) {
		            	if(result == true) {
		            		notifyMessage("Sucesso!", "Definicao de acesso salvo com sucesso!","success",true);
		            	}
		            },
		            error : function() {
		            	notifyMessage("Erro!", "Erro ao salva definicao de acesso!","error",true);
		            }
		        });
			});			
		}

}

EManager.UsersContactAccess.init();