CKEDITOR.editorConfig = function( config ) {
	config.language = 'zh-cn';
    config.resize_enabled = false;
    config.toolbarCanCollapse = false;
    config.toolbarGroups = [
		//{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		//{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		//{ name: 'links' },
		//{ name: 'insert' },
		//{ name: 'forms' },
		//{ name: 'tools' },
		//{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
		//{ name: 'others' },
		//'/',
//		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
//		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
//		{ name: 'styles' },
//		{ name: 'colors' }
		  { name: 'styles'},
		  { name: 'insert' },
		  '/',
		 { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
		 '/',
	    { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
	    { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
	    { name: 'forms' },
	    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	    { name: 'colors' },
	    '/',
	    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
	    { name: 'links' },
	    
	    { name: 'tools' },
	    { name: 'others' },
	    { name: 'about' }

		//{ name: 'about' }
	];
    config.removeButtons = 'Source,Save,NewPage,Preview,Print,Templates,Replace,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Superscript,Subscript,CopyFormatting,CreateDiv,Blockquote,BidiLtr,BidiRtl,Language,Flash,PageBreak,Iframe,ShowBlocks,Find,Maximize,Smiley,Anchor,SelectAll';
	config.enterMode = CKEDITOR.ENTER_BR;
	config.shiftEnterMode = CKEDITOR.ENTER_BR;
	
	
	 //other plugins
	config.font_names='宋体/SimSun;新宋体/NSimSun;仿宋_GB2312/FangSong_GB2312;楷体_GB2312/KaiTi_GB2312;黑体/SimHei;微软雅黑/Microsoft YaHei;'+ config.font_names;
};
