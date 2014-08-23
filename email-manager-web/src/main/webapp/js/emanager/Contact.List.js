EManager.ListEmailAddresses = {
		
		numberOfRowsPerPage : 20,
		
		currentGroupId : null,
		
		init : function() {
			EManager.General.loadGroupsForUser("#emailAddressesGroupComboBox");
			EManager.General.loadGroupsForUser("#editEmailAddressGroupList");
			EManager.ListEmailAddresses.chooseGroup();
			EManager.ListEmailAddresses.editContact();
			EManager.ListEmailAddresses.deleteGroup();
		},
		
		chooseGroup : function() {
			$("#emailAddressesGroupComboBox").change(function() {
				var groupId = $("#emailAddressesGroupComboBox").val();
				if(groupId != -1) {
					EManager.ListEmailAddresses.currentGroupId = groupId;
					EManager.ListEmailAddresses.loadTable(groupId);
				}
			});
		},
		
		loadDeleteAddressBtn : function(cellvalue, options, rowObject) {
			return '<a class="btn btn-mini" href="javascript:EManager.ListEmailAddresses.deleteContact('+cellvalue+')">Apagar</a>';
		},
		
		deleteContact : function(contactId) {
			$.post("/email-manager/contacts/deleteEmailAddress?contactId=" + contactId, function( data ) {
				var msgType = "success";
				if(data.error) msgType = "error";
				notifyMessage(data.title, data.message, msgType,true);
				EManager.ListEmailAddresses.loadTable(EManager.ListEmailAddresses.currentGroupId);
			});
		},
		
		deleteGroup : function() {
			$('#deleteGroupBtn').click(function() {
				if(confirm('Tem certeza que deseja excluir esse grupo?')) {
					$.post("/email-manager/contacts/deleteGroup?groupId=" + EManager.ListEmailAddresses.currentGroupId, function( data ) {
						var msgType = "success";
						if(data.error) msgType = "error";
						notifyMessage(data.title, data.message, msgType,true);
						$("#emailAddressesGrid").jqGrid('GridUnload');
						$("#emailAddressesGroupComboBox").val(-1);
						EManager.General.loadGroupsForUser("#emailAddressesGroupComboBox");
					});
				}
				return false;
			});
		},

		loadTable : function(groupId) {
			$("#emailAddressesGrid").jqGrid('GridUnload');
			jQuery("#emailAddressesGrid").jqGrid({
				url:"/email-manager/contacts/listEmailAddresses?groupId=" + groupId + "&rowsPerPage=" + EManager.ListEmailAddresses.numberOfRowsPerPage,
				datatype: "json",
				mtype: 'GET',
				loadError: function(){},
				height: 'auto',
				colNames:['Nome', 'E-mail', '', '', ''],
				gridview:false,
				afterInsertRow : function(rowid, rowdata, rowelem){
				},
				colModel:
						[
				          {name:'name', index:'name', sortable: 'true', width: 100},
				          {name:'address', index:'address', sortable: 'true'},
				          {name:'id', index:'id', formatter:EManager.ListEmailAddresses.loadDeleteAddressBtn, width: 40},
				          {name:'addressGroup', index:'addressGroup', jsonmap: 'addressGroup.id', hidden: true},
				          {name:'id', index:'id', hidden: true}
				        ],
				          rownumbers: true,
				          //loadonce: true,
				          sortable: true,
				          sortname: 'name',
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
				          caption: "Contatos",
				          loadComplete: function (data){
				        	  var grid = $(this);
				        	  grid.setGridParam({rowNum:data.records});
				        	  bindResizeEventToGrid(this);
				        	  var parent = $(this).parent().parent().parent().parent().parent();
				        	  if(this.grid.cols!=null) //added because it was causing null exception
				        		  $(this).setGridWidth(parent.innerWidth() - 5, true);
				          },
				          onCellSelect : function(rowid, iRow, cellcontent, e) {
					        	var contactData = $("#emailAddressesGrid").getRowData(rowid);
					        	$("#editContactId").val(contactData.id);
					        	$("#editEmailAddressGroupList").val(contactData.addressGroup);
					        	$("#editAddressName").val(contactData.name);
					        	$("#editAddressEmail").val(contactData.address);
								$('#editContactModal').modal();
					      },
					      pager : "#contactListGridPager",
						  rowNum : EManager.ListEmailAddresses.numberOfRowsPerPage
			});	
			
		},
		
		editContact : function() {
			$("#addEmailAddressBtn").click(function() {
				var contactId = $("#editContactId").val();
				var group = $("#editEmailAddressGroupList").val();
				var name = $("#editAddressName").val();
				var email = $("#editAddressEmail").val();
				if(name == ''){
					notifyMessage("Alerta!", "Por favor informe um nome para o contato!","error",true);
					return false;
				}
				if(email == ''){
					notifyMessage("Alerta!", "Por favor informe um email para o contato!","error",true);
					return false;
				}
				var dataString = 'contactId='+ contactId + '&name='+ name + '&address=' + email + '&groupId=' + group;
				$.ajax({
					type: "POST",
					url: "/email-manager/contacts/edit",
					data: dataString,
					async:   false,
					beforeSend: function() {
					},
					success: function(result) {
						if(result == true){
							notifyMessage("Secesso!", "Contato alterado com sucesso!","success",true);
						}
						else {
							notifyMessage("Erro!", "Ocorreu um erro ao alterar este contato!","error",true);
						}
						$('#editContactModal').modal("hide");
						$("#emailAddressesGroupComboBox").val(group);
						EManager.ListEmailAddresses.loadTable(group);
					}
				});
			});
		}

}


EManager.ListEmailAddresses.init();