EManager.ListSignatures = {
		
		init : function() {
			EManager.ListSignatures.loadTable();
			EManager.ListSignatures.saveSignature();
			EManager.ListSignatures.newSignature();
		},

		loadTable : function(groupId) {
			$("#signaturesListGrid").jqGrid('GridUnload');
			jQuery("#signaturesListGrid").jqGrid({
				url:"/email-manager/users/listSignatures",
				datatype: "json",
				mtype: 'GET',
				loadError: function(){},
				height: 'auto',
				colNames:['Id', 'Titulo', ''],
				gridview:false,
				colModel:
						[
				          {name:'idSignature', index:'idSignature', sortable: 'true', width: 200},
				          {name:'title', index:'title', sortable: 'true'},
				          {name:'htmlSignature', index:'htmlSignature', hidden: true},
				        ],
				          rownumbers: true,
				          loadonce: true,
				          sortable: true,
				          sortname: 'signatureId',
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
				          caption: "Minhas assinaturas",
				          loadComplete: function (data){
				        	  var grid = $(this);
				        	  grid.setGridParam({rowNum:data.records});
				        	  bindResizeEventToGrid(this);
				        	  var parent = $(this).parent().parent().parent().parent().parent();
				        	  if(this.grid.cols!=null) //added because it was causing null exception
				        		  $(this).setGridWidth(parent.innerWidth() - 5, true);
				          },
				          onCellSelect : function(rowid, iRow, cellcontent, e) {
							var data = $("#signaturesListGrid").getLocalRow(rowid);
							$('#idSignature').val(data.idSignature);
							$("#editSignatureTitle").val(data.title);
							$('#editSignatureModal').modal();
							CKEDITOR.instances.signatureTextArea.setData(data.htmlSignature);
				          }
			});	
			
		},
		
		newSignature : function() {
			$('#newSignatureBtn').click(function(){
				$('#editSignatureModal').modal();
			});
		},
		
		saveSignature : function() {
			$("#saveSignatureBtn").click(function() {
				var idSignature = $("#idSignature").val();
				var title = $("#editSignatureTitle").val();
				var signature = CKEDITOR.instances.signatureTextArea.getData();
				if(title == ''){
					notifyMessage("Alerta!", "Por favor informe um titulo para sua assinatura!","error",true);
					return false;
				}
				if(signature == ''){
					notifyMessage("Alerta!", "Por favor informe uma assinatura!","error",true);
					return false;
				}
				var dataString = 'idSignature=' + idSignature + '&title='+ title + '&signature=' + signature;
				$.ajax({
					type: "POST",
					url: "/email-manager/users/saveSignature",
					data: dataString,
					async:   false,
					beforeSend: function() {
					},
					success: function(result) {
						if(result == true){
							notifyMessage("Secesso!", "Assinatura salva com sucesso!","success",true);
						}
						else {
							notifyMessage("Erro!", "Ocorreu um erro ao salvar esta assinatura!","error",true);
						}
						$('#editSignatureModal').modal("hide");
						EManager.ListSignatures.loadTable();
					}
				});
			});
		}

}


EManager.ListSignatures.init();