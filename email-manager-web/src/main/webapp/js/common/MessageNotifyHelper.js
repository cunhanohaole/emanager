function notifyMessage(messageTitle, messageText, messageType,
		hideMessageAfterDelay) {
	var messageDelay = 2000;

	// we don't want to show anything if passed title AND text were bad or empty
	if (messageTitle == null && messageText == null
			&& $.trim(messageTitle) === "" && $.trim(messageText) === "") {
		return;
	}

	// default messageType to info if message type was bad or not specified
	if (messageType != "notice" && messageType != "error"
			&& messageType != "info" && messageType != "success") {
		messageType = "info";
	}

	// if you don't want the history pull-down menu in the top corner, include
	// this line somewhere before your first notice:
	$.pnotify.defaults.history = false;  

	// Set the hide option to true by default
	if (hideMessageAfterDelay == null) {
		hideMessageAfterDelay = true;
	}

	// POgliani :: 26/06 :: according Wikipedia, the average adult reading rate
	// is 250 words per minute or 4 words per second
	// count the number the spaces + 1 (number of words) and multiply by 250
	// miliseconds (4 words per second) + 2 sec for the slower readers +
	// reaction time :)
	var cleanStr = messageText.match(/ /g);
	if (cleanStr != null && !$.trim(cleanStr) === "") {
		messageDelay = Number(((cleanStr.length + 1) * 250) + 2000);
	}

	//
	$.pnotify({
		title : messageTitle,
		text : messageText,
		type : messageType,
		delay : messageDelay,
		hide : hideMessageAfterDelay
	});
};



function notifyDataLoadingError() {
	notifyMessage("Grid Data Error", "Error occurred in grid data loading",
			"error", true)
}