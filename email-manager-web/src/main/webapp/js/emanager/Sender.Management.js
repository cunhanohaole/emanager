EManager.SenderManagement = {
		
		init : function() {
			EManager.General.loadUsers("#senderManagementUser");
			EManager.SenderManagement.loadTable();
			EManager.SenderManagement.setupSenderManagementUserOnClick();
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
					console.log('onCellSelect')
				}
			});
		},

		setupSenderManagementUserOnClick : function() {
		    $("#senderManagementUser").change(function() {
		        EManager.SenderManagement.loadTable();
		    });
		}

}


EManager.SenderManagement.init();