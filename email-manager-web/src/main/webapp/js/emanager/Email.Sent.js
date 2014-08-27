EManager.SentEmails = {
		
		numberOfRowsPerPage : 20,
		
		init : function() {
			EManager.SentEmails.loadTable();
			EManager.SentEmails.forwardEmail();
			EManager.General.loadSendersForUser("#sentEmailsSenders");
			EManager.SentEmails.setupFromFilterOnClick();
		},

		loadTable : function() {
		    $("#sentEmailsGrid").jqGrid('GridUnload');
		    var senderId = $("#sentEmailsSenders").val();
			jQuery("#sentEmailsGrid").jqGrid({
				url:"/email-manager/email/listSentEmails?senderConfigId=" + senderId + "&rowsPerPage=" + EManager.SentEmails.numberOfRowsPerPage,
				datatype: "json",
				mtype: 'GET',
				loadError: function(){},
				height: 'auto',
				gridview:false,
				afterInsertRow : function(rowid, rowdata, rowelem){
					if(rowdata.statusFailure) {
						$("#sentEmailsGrid #" + rowid).find("td").addClass("alert-error");
					}
					else if(rowdata.statusSuccess) {
						$("#sentEmailsGrid #" + rowid).find("td").addClass("alert-success");
					}
				},
				colNames:['Assunto', 'De', 'Para', 'Data', 'Contem anexo?', '', '', ''],
				colModel: [
					 {name:'subject', index:'subject', sortable: 'true'},
					 {name:'senderConfig.from', index:'senderConfig.from', sortable: 'true', width: 70},
				     {name:'addressGroup.title', index:'addressGroup.title', sortable: 'true', width: 70},
				     {name:'createdTimeStamp', index:'createdTimeStamp', sortable: 'true', width: 40, formatter: formatToJavaToStringDate},
				     {name:'containAttachments', index:'containAttachments', sortable: 'false', width: 20, formatter: formatYesOrNo},
				     {name:'success', index:'success', hidden: true},
				     {name:'statusFailure', index:'statusFailure', hidden: true},
				     {name:'statusSuccess', index:'statusSuccess', hidden: true}
				],
				rownumbers: true,
				//loadonce: true,
				sortable: true,
				sortname: 'createdTimeStamp',
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
					var dataString = "emailId=" + rowId;
					$.ajax({
						type: "GET",
						url: "/email-manager/email/getEmail",
						data: dataString,
						async:   false,
						success: function(email) {
							$("#emailDetailSubject").html(email.subject);
							$("#emailDetailBody").html(email.bodyString);
						}
					});
					$('#emailId').val(rowId);
					$('#sentEmailDetails').modal();
				},
				pager : "#sentEmailsGridPager",
				rowNum : EManager.SentEmails.numberOfRowsPerPage
			});	
			
		},
		
		forwardEmail : function () {
			$("#forwardEmail").live('click', function(){
				$("#sentEmailDetails").modal('hide');
				var emailId = $('#emailId').val();
				$("#newEmailLink").trigger("click", emailId);
			});
		},

		setupFromFilterOnClick : function () {
		    $("#sentEmailsSenders").live("change", function (){
                EManager.SentEmails.loadTable();
		    });
		}

}


EManager.SentEmails.init();