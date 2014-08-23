var EManager = {};
EManager.General = {

		init : function (){
		},

        loadUsers : function(comboBoxSelector) {
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
                        $(comboBoxSelector).append(new Option(username, username));
                    }
                }
            });
        },
		
		loadGroups : function(comboBoxSelector) {
			$.get("/email-manager/contacts/listGroups", function(data){
				EManager.General.populateGroupComboBox(data, comboBoxSelector);
        	});
		},
		
		loadGroupsForUser : function(comboBoxSelector) {
			$.get("/email-manager/contacts/listGroupsForUser", function(data){
				EManager.General.populateGroupComboBox(data, comboBoxSelector);
        	});
		},

		loadSendersForUser : function(comboBoxSelector){
		    $.get("/email-manager/senderConfig/listSendersForUser", function(data){
                var senders = data.rows;
                if(senders != null && senders.length > 0){
                    for(var i = 0; i < senders.length; i++){
                        var o = new Option(senders[i].from, senders[i].id);
                        $(o).html(senders[i].from);
                        $(comboBoxSelector).append(o);
                    }
                }
            });
		},
		
		populateGroupComboBox : function(data, comboBoxSelector) {
			$(comboBoxSelector).html('');
			var groups = data.rows;
			var defaultOption = new Option("Escolha um grupo...", -1);
			$(defaultOption).html("Escolha um grupo...");
			$(comboBoxSelector).append(defaultOption);
    		if(groups != null && groups.length > 0){
        		for(var i = 0; i < groups.length; i++){
        			var o = new Option(groups[i].title, groups[i].id);
        			$(o).html(groups[i].title);
        			$(comboBoxSelector).append(o);
        		}
    		}
		},
		
		loadRoles : function(comboBoxSelector) {
			$.get("/email-manager/users/listRoles", function(roles){
        		if(roles != null && roles.length > 0){
            		for(var i = 0; i < roles.length; i++){
            			var o = new Option(roles[i].role, roles[i].role);
            			$(o).html(roles[i].description);
            			$(comboBoxSelector).append(o);
            		}
        		}
        	});
		}
		
}

EManager.General.init();