EManager.ListUsers = {
		
		init : function() {
			EManager.ListUsers.loadTable();
			EManager.ListUsers.editUser();
			EManager.General.loadRoles("#editUserRoleComboBox");
		},

		loadTable : function(groupId) {
			$("#usersListGrid").jqGrid('GridUnload');
			jQuery("#usersListGrid").jqGrid({
				url:"/email-manager/users/list",
				datatype: "json",
				mtype: 'GET',
				loadError: function(){},
				height: 'auto',
				colNames:['Usuario', 'Perfil', 'Ativo', '', '', ''],
				gridview:false,
				colModel:
						[
				          {name:'username', index:'username', sortable: 'true', width: 200},
				          {name:'roleDesc', index:'roleDesc', sortable: 'true', jsonmap: "roles.0.description"},
				          {name:'active', index:'active', sortable: 'true', width: 20, formatter: formatYesOrNo},
				          {name:'role', index:'role', jsonmap: "roles.0.role", hidden: true},
				          {name:'password', index:'password', hidden: true},
				          {name:'emailAccount', index:'emailAccount', jsonmap: "senderConfig.from", hidden: true},
				        ],
				          rownumbers: true,
				          loadonce: true,
				          sortable: true,
				          sortname: 'username',
				          sortorder: 'desc',
				          jsonReader : {
				        	  root: "rows",
				        	  page: "page",
				        	  total: "total",
				        	  records: "records",
				        	  repeatitems: false,
				        	  cell: "cell",
				        	  id: "id"
				          },
				          multiselect: false,
				          loadui:'disabel',
				          caption: "Usuarios",
				          loadComplete: function (data){
				        	  var grid = $(this);
				        	  grid.setGridParam({rowNum:data.records});
				        	  bindResizeEventToGrid(this);
				        	  var parent = $(this).parent().parent().parent().parent().parent();
				        	  if(this.grid.cols!=null) //added because it was causing null exception
				        		  $(this).setGridWidth(parent.innerWidth() - 5, true);
				          },
				          onCellSelect : function(rowid, iRow, cellcontent, e) {
				        	var userData = $("#usersListGrid").getLocalRow(rowid);
				        	$("#editUserRoleComboBox").val(userData.role);
				        	$("#editUserLogin").val(userData.username);
				        	$("#editUserPassword").val(userData.password);
				        	$("#editUserEmailAccount").val(userData.emailAccount);
				        	$('#editUserActive').prop('checked', userData.active);
							$('#editUserModal').modal();
				          }
			});	
			
		},
		
		editUser : function() {
			$("#editUserBtn").click(function() {
				var userRole = $("#editUserRoleComboBox").val();
				var username = $("#editUserLogin").val();
				var password = $("#editUserPassword").val();
				var emailAccount = $("#editUserEmailAccount").val();
				var active = $("#editUserActive").is(':checked');
				var isFormValid = EManager.NewUser.validateUser(username, password, emailAccount, null, null);
				if(!isFormValid) return false;
				
				var dataString = 'username='+ username + '&password=' + password + '&active=' + active + '&userRole=' + userRole + '&emailAccount=' + emailAccount;
				$.ajax({
					type: "POST",
					url: "/email-manager/users/editUser",
					data: dataString,
					async:   false,
					beforeSend: function() {
					},
					success: function(result) {
						if(result == true){
							notifyMessage("Secesso!", "Usuario alterado com sucesso!","success",true);
						}
						else {
							notifyMessage("Erro!", "Ocorreu um erro ao alterar este usuario!","error",true);
						}
						$('#editUserModal').modal("hide");
						EManager.ListUsers.loadTable();
					}
				});
			});
		}

}


EManager.ListUsers.init();