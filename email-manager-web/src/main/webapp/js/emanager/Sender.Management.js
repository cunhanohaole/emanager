EManager.SenderManagement = {
		
		init : function() {
			EManager.General.loadUsers("#senderManagementUser");
			EManager.SenderManagement.loadTable();
			EManager.SenderManagement.setupSenderManagementUserOnClick();
			EManager.SenderManagement.editSenderConfig();
			EManager.SenderManagement.deleteSenderConfig();
			EManager.SenderManagement.setupShowNewSenderModal();
			EManager.SenderManagement.setupSaveNewSenderConfig();
		},

		loadTable : function() {
		    $("#sendersGrid").jqGrid('GridUnload');
		    var username = $("#senderManagementUser").val();
			jQuery("#sendersGrid").jqGrid({
				url:"/email-manager/senderConfig/listSendersForUser?username=" + username,
				datatype: "json",
				mtype: 'GET',
				loadError: function(){},
				height: 'auto',
				gridview:false,
				colNames:['Id', 'E-mail'],
				colModel: [
					 {name:'id', index:'id', sortable: 'true', width: 70},
					 {name:'from', index:'from', sortable: 'true'}
				],
				rownumbers: true,
				//loadonce: true,
				sortable: true,
				sortname: 'id',
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
				caption: "E-mails enviados",
				loadComplete: function (data){
					var grid = $(this);
					grid.setGridParam({rowNum:data.records});
					bindResizeEventToGrid(this);
					var parent = $(this).parent().parent().parent().parent().parent();
					if(this.grid.cols!=null) //added because it was causing null exception
						$(this).setGridWidth(parent.innerWidth() - 5, true);
				},
				onCellSelect : function(rowId) {
                    var senderConfigData = $("#sendersGrid").getRowData(rowId);
                    $("#editSenderConfigId").val(senderConfigData.id);
                    $("#editSenderConfigFrom").val(senderConfigData.from);
					$("#editSenderDetail").modal();
				}
			});
		},

		setupSenderManagementUserOnClick : function() {
		    $("#senderManagementUser").change(function() {
		        EManager.SenderManagement.loadTable();
		    });
		},

		editSenderConfig : function() {
            $("#editSenderConfigBtn").click(function() {
                var id = $("#editSenderConfigId").val();
                var from = $("#editSenderConfigFrom").val();

                if(!isValidEmailAddress(from)) {
                    notifyMessage("Erro!", "Por favor informe um edereço de email válido!","error",true);
                    return;
                }

                var dataString = 'senderConfigId='+ id + '&senderConfigFrom=' + from;
                $.ajax({
                    type: "POST",
                    url: "/email-manager/senderConfig/editSenderConfig",
                    data: dataString,
                    async:   false,
                    beforeSend: function() {
                    },
                    success: function(result) {
                        notifyMessage(result.title, result.message, result.notifyType, true);
                        $('#editSenderDetail').modal("hide");
                        EManager.SenderManagement.loadTable();
                    }
                });
            });
        },

        setupShowNewSenderModal : function() {
            $("#newSenderConfigBtn").click(function() {
                EManager.General.loadUsers("#newSenderUser");
                $("#newSenderModal").modal();
            });
        },

        setupSaveNewSenderConfig : function() {
            $("#saveNewSenderConfigBtn").click(function() {
                var userName = $("#newSenderUser").val();
                var from = $("#newSenderConfigFrom").val();

                if(!isValidEmailAddress(from)) {
                    notifyMessage("Erro!", "Por favor informe um edereço de email válido!","error",true);
                    return;
                }

                var dataString = 'userName' + userName + '&emailAddress=' + from;
                $.ajax({
                    type: "POST",
                    url: "/email-manager/senderConfig/createNewSenderConfig",
                    data: dataString,
                    async:   false,
                    success: function(result) {
                        notifyMessage(result.title, result.message, result.notifyType, true);
                        $('#newSenderModal').modal("hide");
                        EManager.SenderManagement.loadTable();
                    }
                });
            });
        },

        deleteSenderConfig : function() {
            $("#deleteSenderConfigBtn").click(function() {
                var id = $("#editSenderConfigId").val();
                var dataString = 'senderConfigId='+ id;
                $.ajax({
                    type: "DELETE",
                    url: "/email-manager/senderConfig/deleteSenderConfig?senderConfigId=" + id,
                    data: dataString,
                    async:   false,
                    beforeSend: function() {
                    },
                    success: function(result) {
                        notifyMessage(result.title, result.message, result.notifyType, true);
                        $('#editSenderDetail').modal("hide");
                        EManager.SenderManagement.loadTable();
                    }
                });
            });
        }

}


EManager.SenderManagement.init();