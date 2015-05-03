/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	//config.filebrowserUploadUrl = 'ckeditorUpload!ckeditorUploadForPic.do'; 
	//var pathName = window.document.location.pathname;
	//var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	//config.filebrowserImageUploadUrl = projectName+'/system/upload.do'; //固定路径
	
	config.language = 'zh-cn' ;
	
	config.toolbar = 'MyToolbar';
	 
	config.toolbar_MyToolbar =
	[
		{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
		{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
		{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','-','RemoveFormat' ] },
		{ name: 'colors', items : [ 'TextColor','BGColor' ] },
		'/',
		{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-',
		'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },
		{ name: 'insert', items : [ 'Table','HorizontalRule','SpecialChar','PageBreak' ] },
		{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] }
		
	];


};
