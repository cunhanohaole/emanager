/* 
POgliani :: 10/08/2012
Global settings form all grids 
*/
jQuery.extend(
	jQuery.jgrid.defaults,
	{
	    altRows: true, //Set a zebra-striped grid (alternate rows have different styles)
	    altclass: "table-striped", //The class that is used for applying different styles to alternate (zebra) rows in the grid. "table-striped" is a Bootstrap class for alternate colors
	    autowidth: true, //When set to true, the grid width is recalculated automatically to the width of the parent element. 
	    caption: null, //Defines the caption for the grid. 
	    cmTemplate: { // default values for columns. Those don't need to be specified in the colModel anymore
	        datefmt: "dd/mm/yyyy",
	        resizable: true,
	        sortable: true,
	        sorttype: "text",
	        title: true,
	        width: 150
	    },
	    direction: "ltr", //Determines the direction of text in the grid
	    gridview: true, //insert all the data at once
	    height: "auto",
	    loadonce: false, //If this flag is set to true, the grid loads the data from the server only once. The functions of the pager (if present) are disabled.
	    page: 1, //Set the initial page number when we make the request.This parameter is passed to the url for use by the server routine retrieving the data.
	    rowNum: 15,
	    rowList: [15, 30, 50, 100],
	    shrinkToFit: true,
	    sortable: true,
	    viewrecords: true,
	    error: function () {
	        // todo add error handling
	        alert('Error in constructing documentSelectionGrid.');
	    },
	    loadError: function () {
	        // todo add error handling
	        //alert('Error in documentSelectionGrid on load. LoadError.');
	    },
	    loadComplete: function () {
	        var _this = this;
	        var parent = $(_this).parent().parent().parent().parent().parent();
	        $(window).bind('resize', function() {
	            	if(_this.grid.cols!=null) //added because it was causing null exception
	            		$(_this).setGridWidth(parent.innerWidth(), true);
	        });
	    }
	},
/*Global Formats for fields*/
	jQuery.jgrid.formatter, { date: { newformat: 'd/m/Y'} }
);
/*Format for columns of type date*/
var dateFormat = {
	sorttype:'date', 
	formatter:'date',
    formatoptions: {newformat:'d/m/Y'},
	datefmt: 'd/m/Y',
	align:'center',
	width:80
}
