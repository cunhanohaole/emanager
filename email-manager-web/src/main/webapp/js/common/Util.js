var isIE = (navigator.appName.toLowerCase().indexOf("microsoft") != -1);

function acceptNumbersOnly(evt){
	var key = isIE ? evt.keyCode : evt.which;
	if(key >= 48 && key <= 57)
		return true;
	else
		return false;
}

function acceptAlphabetOnly(evt) {
	var key = isIE ? evt.keyCode : evt.which;
	if (key == 0 || key == 13 || key == 8 || key == 31 || key == 44
			|| (key > 47 && key < 58) || (key >= 65 && key <= 90)
			|| (key >= 97 && key <= 122))
		return true;
	else
		return false;
}

function toUpperCase(control) {
	if (control != null) {
		control.value = control.value.toUpperCase();
	}
}

$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if(results!=null)
    	return results[1] || 0;
    else
    	return 0;
}

function isEnterKeyPressed(event)
{
	var key = isIE ? event.keyCode : event.which;
	return (key == 13);
}
function clearAllCookies()
{
	var cookies = document.cookie.split(";");
	for(var i=0; i < cookies.length; i++) {
	    var equals = cookies[i].indexOf("="),
	        name = equals > -1 ? cookies[i].substr(0, equals) : cookies[i];
	    document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
	}	
}

function logutFromApplication()
{
	//clearAllCookies()
	window.location.href = '/email-manager/j_spring_security_logout'
		
}

function isValidEmailAddress(emailAddress) {
    var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
    return pattern.test(emailAddress);
};


function bindResizeEventToGrid(grid)
{
	var _this = grid;
      var parent = $(_this).parent().parent().parent().parent().parent();
      $(window).bind('resize', function() {
          	if(_this.grid.cols!=null) //added because it was causing null exception
          		$(_this).setGridWidth(parent.innerWidth(), true);
      });
}

function formatToJavaToStringDate(cellValue, options) {
    if(cellValue) {
        return $.fmatter.util.DateFormat(
            '', 
            new Date(+cellValue), 
            'D d M Y - H:i:s', 
            $.extend({}, $.jgrid.formatter.date, options)
        );
    } else {
        return '';
    }
}

function formatYesOrNo(value){
	if(value == true) return "Sim";
	else return "Nao";
}


function linkFormat( cellvalue, options, rowObject ){
	return '<a class="btn" href="'+cellvalue+'">Download</a>';
}
function linkUnFormat( cellvalue, options, cell){
	return $('a', cell).attr('href');
}

function triggerRefreshGrid(grid, sortName, sortOrder)
{
	$(grid).jqGrid().setGridParam({ datatype: 'json', sortname: sortName, sortorder: sortOrder }).trigger("reloadGrid");  
}



function animateRefreshIcon(icon)
{
   $(icon).animate({textIndent:0},
    		{
		    	step: function(now, fx)
		    	{
		    		
		    		 $(icon).css({
				        "-webkit-transform": "rotate(-"+now+"deg)",
				        "-moz-transform": "rotate(-"+now+"deg)",
				        "filter": "progid:DXImageTransform.Microsoft.BasicImage(rotation=-"+now+")"
				    });      
		    	},
		    	duration: 'slow',
		    	complete:function() {
			    	$(icon).css({"text-indent": "360px"});
			    }
		    },
		    'linear');	
}


/*Hoffgate.AutoCompleteItemRenderer = function( ul, item ) {
	var li = $( "<li></li>" );
	li.data( "item.autocomplete", item )
	li.append( "<a>" + item.value+" - " + item.label+ "</a>" ).appendTo( ul )
	return 
};*/