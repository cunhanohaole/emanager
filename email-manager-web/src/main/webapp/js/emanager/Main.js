EManager.Main = {

		init : function (){
			EManager.Main.showNewEmail();
		},
		
		showNewEmail : function(emailId) {
			$('#newEmailLink').live('click', function(event, emailId){
				$("#newEmailDiv").load("/email-manager/email/new", function() {
					if(emailId != undefined) {
						EManager.NewEmail.loadPreviousEmail(emailId);					
					}
					else {
						EManager.NewEmail.loadAttachments();
					}
				});
			});
		},
		
		listSentEmails : function() {
			$("#sentEmailsDiv").load("/email-manager/email/sentEmails", function() {
				$('#mainMenuTab a[href="#sentEmailsDiv"]').tab('show');
			});
		},
		
		listSignatures : function() {
			$("#signaturesDiv").load("/email-manager/users/signatures", function() {
			});
		},
		
		showAddGroup : function() {
			$("#addGroupDiv").load("/email-manager/contacts/addGroup", function() {
				
			});
		},
		
		showAddEmailAddress : function() {
			$("#addEmailAddressDiv").load("/email-manager/contacts/addEmailAddress", function() {
				
			});
		},
		
		showListEmailAddress : function() {
			$("#listEmailAddressDiv").load("/email-manager/contacts/loadListEmailAddresses");
		},
		
		showImportContacts : function() {
			$("#importContactsDiv").load("/email-manager/contacts/importContacts");
		},
		
		showNewUser : function() {
			$("#newUserDiv").load("/email-manager/users/new");
		},
		
		showUsersList : function() {
			$("#listUsersDiv").load("/email-manager/users/loadList");
		},
		
		showContactAccess : function() {
			$("#contactAccessDiv").load("/email-manager/users/loadContactAccess");
		}
		
}

EManager.Main.init();