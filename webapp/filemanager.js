
function beforValidata(){
	var fileTypeId = $('#fileTypeId').combobox('getValue'); 
	var fileTypeSubId = $('#fileTypeSubId').combobox('getValue');
	if(!fileTypeId || !fileTypeSubId){
		alert("请选择附件类型！");
		$('#fileSpan').html('<input id="file" name="file" type="file" onfocus="beforValidata();" onchange="saveAddFile()" />');
		return;
	}
}


function beforValidataForAudit(){
	var fileTypeId = $('#fileTypeId').combobox('getValue'); 
	var fileTypeSubId = $('#fileTypeSubId').combobox('getValue');
	if(!fileTypeId || !fileTypeSubId){
		alert("请选择附件类型！");
		$('#fileSpan').html('<input id="file" name="file" type="file" onfocus="beforValidataForAudit();" onchange="saveAddFileForAudit()" />');
		return;
	}
}


	

// 保存添加的文件
function saveAddFile(){
	var fileTypeId = $('#fileTypeId').combobox('getValue'); 
	var fileTypeSubId = $('#fileTypeSubId').combobox('getValue');
	if(!fileTypeId || !fileTypeSubId){
		alert("请选择附件类型！");
		$('#fileSpan').html('<input id="file" name="file" type="file" onfocus="beforValidata();" onchange="saveAddFile()" />');
		return;
	}
	
	jQuery.ajaxFileUpload({     
			       url : 'upload!saveAddFile.do',// 用于文件上传的服务器端请求地址
			      type : "post",  
			      data: { fileTypeId: fileTypeId, fileTypeSubId: fileTypeSubId }, 
		      dataType : "json",     
		       timeout : 300000,     
		     secureuri : false,    // 一般设置为false     
		 fileElementId : 'file', // 文件上传空间的id属性     
			   success : function(json) 
			   {    
				   if(json == "type"){
					   alert("该附件类型不在允许上传的范围内!");
				   }else if(json == "size"){
					   alert("附件大小超过10M不允许上传!");
				   }else{
					   var str = "<tr><td><label>&nbsp;<img src='images/point1.gif'>&nbsp;&nbsp;<a href='javascript:seeFileCentent(\"" + json.id + "\")'><font color='red'>" + json.fileDisplayname + "</font></a>&nbsp;&nbsp;"
					   		+ "&nbsp;&nbsp;<a class='a_removeCurrentFile' href='javascript:deleteFile(\"" + json.id + "\")'>删除</a>"
					   		+ "<input id='" + json.id +"' name='file_params' type='hidden' value='" + json.id + "' />"
					   		+ "</label></td></tr>";
					   $("#fileListTab").html($("#fileListTab").html()+str);
					   $('#fileSpan').html('<input id="file" name="file" type="file" onfocus="beforValidata();" onchange="saveAddFile()" />');
				   }
			    },
			    error : function(XMLHttpRequest, textStatus, errorThrown) 
			     {  
			    	alert("失败");     
			     }
	}); 
}
	
	
// 删除
function deleteFile(id){
	jQuery.ajax({
		   type: "POST",
		   url: "filemanager!deleteForPage.do",
		   data: "mainIds="+id,
		   dataType:"json",
		   success: function(json){}
		});
}


	
// 查看文件内容
function seeFileCentent(id){
	jQuery.ajax({
		   type: "POST",
		   url: "filemanager!examineFileContent.do",
		   data: "fileId="+id,
		   dataType:"json",
		   success: function(json){
			   
		   }
		});
}



//修改附件窗口
function updateWindow(id){
	
	document.getElementById("fileAddDiv").style.display="none";
	document.getElementById("fileUploadContentDiv").style.display="block";
	
	$("#uFileTypeId").combobox({width:170});
	$('#uFileTypeSubId').combobox({width:130});
	
	jQuery.ajax({
		   type: "POST",
		   url: "filemanager!updateWindow.do",
		   data: "fileId="+id,
		   dataType:"json",
		   success: function(json){
			   var optionStr1;
			   var filetypeId = json[2];
			   $("#uFileTypeId").find("option").remove(); 
			   
			    var data1,data2;
				data1 = [];
				data2 = [];
			   for(var i =0;i<json[0].length;i++){
				   var jsonobj1 = json[0][i];
				   data1.push({ "dictionaryName": jsonobj1.dictionaryName, "id": jsonobj1.id });
			   }
			   $("#uFileTypeId").combobox("loadData", data1);
			   $('#uFileTypeId').combobox('setValue', filetypeId);
			   var optionStr2;
			   var filetypeSubId = json[3];
			   $("#uFileTypeSubId").find("option").remove(); 
			   for(var i =0;i<json[1].length;i++){
				   var jsonobj2 = json[1][i];
				   data2.push({ "dictionaryName": jsonobj2.dictionaryName, "id": jsonobj2.id });
			   }
			   $("#uFileTypeSubId").combobox("loadData", data2);
			   $('#uFileTypeSubId').combobox('setValue', filetypeSubId);
			   
			   var hiddUpdateId = "<tr><td><input id='updateId' name='updateId' type='hidden' value=" + json[4] + "></input></td></tr>";
			   $("#fileListTab").html($("#fileListTab").html()+hiddUpdateId);
		   }
		});
}



//保存修改后的附件
function saveUpdateFile(){
	var fileTypeId = $('#uFileTypeId').combobox('getValue'); 
	var fileTypeSubId = $('#uFileTypeSubId').combobox('getValue');
	if(fileTypeId == null || fileTypeId == '' || fileTypeSubId == null || '' == fileTypeSubId){
		alert("请选择附件类型！");
		return;
	}
	var updateId = $("#updateId").val();
	jQuery.ajaxFileUpload({     
			       url : 'upload!saveAfterFileForUpdate.do',// 用于文件上传的服务器端请求地址
			      type : "post",  
			      data: { fileTypeId: fileTypeId, fileTypeSubId: fileTypeSubId, updateId: updateId }, 
		      dataType : "json",     
		       timeout : 300000,     
		     secureuri : false,    // 一般设置为false     
		 fileElementId : 'ufile', // 文件上传空间的id属性     
			     error : function(XMLHttpRequest, textStatus, errorThrown) 
			     {  
			    	alert("失败");     
			     },     
			   success : function(json) 
			   {    
				   if(json == "type"){
					   alert("该附件类型不在允许上传的范围内!");
				   }else if(json == "size"){
					   alert("附件大小超过10M不允许上传!");
				   }else{
					   var str = "<tr><td><label>&nbsp;<img src='images/point1.gif'>&nbsp;&nbsp;<a href='javascript:seeFileCentent(\"" + json.id + "\")'><font color='red'>" + json.fileNumber + json.fileDisplayname + "</font></a>&nbsp;&nbsp;"
				   		+ "&nbsp;&nbsp;<a class='a_removeCurrentFile' href='javascript:deleteFile(\"" + json.id + "\")'>删除</a>"
				   		+ "<input id='" + json.id +"' name='file_params' type='hidden' value='" + json.id + "' />"
				   		+ "</label></td></tr>";
					   $("#fileListTab").html($("#fileListTab").html()+str);
					   $('#auditFileSpan').html('<input type="file" id="ufile" name="ufile" /><font color="red">（附件大小应小于10MB）</font>');
					   //$('#updateDIV').window('close');
					   document.getElementById("fileUploadContentDiv").style.display="none";
					   document.getElementById("fileAddDiv").style.display="block";
				   }
			   }
	}); 
	
}


function deleteFile(id){
	jQuery.ajax({
		   type: "POST",
		   url: "filemanager!deleteForPage.do",
		   data: "mainIds="+id,
		   dataType:"json",
		   success: function(json){}
		});
}


//修改窗口取消按钮
function cancelForUpdate(){
	//$('#updateDIV').window('close');
	document.getElementById("fileUploadContentDiv").style.display="none";
	document.getElementById("fileAddDiv").style.display="block";
}



//修改附件窗口
function updateWindow(id){
	
	document.getElementById("fileAddDiv").style.display="none";
	document.getElementById("fileUploadContentDiv").style.display="block";
	
	$("#uFileTypeId").combobox({width:170});
	$('#uFileTypeSubId').combobox({width:130});
	
	jQuery.ajax({
		   type: "POST",
		   url: "filemanager!updateWindow.do",
		   data: "fileId="+id,
		   dataType:"json",
		   success: function(json){
			   var optionStr1;
			   var filetypeId = json[2];
			   $("#uFileTypeId").find("option").remove(); 
			   
			    var data1,data2;
				data1 = [];
				data2 = [];
			   for(var i =0;i<json[0].length;i++){
				   var jsonobj1 = json[0][i];
				   data1.push({ "dictionaryName": jsonobj1.dictionaryName, "id": jsonobj1.id });
			   }
			   $("#uFileTypeId").combobox("loadData", data1);
			   $('#uFileTypeId').combobox('setValue', filetypeId);
			   var optionStr2;
			   var filetypeSubId = json[3];
			   $("#uFileTypeSubId").find("option").remove(); 
			   for(var i =0;i<json[1].length;i++){
				   var jsonobj2 = json[1][i];
				   data2.push({ "dictionaryName": jsonobj2.dictionaryName, "id": jsonobj2.id });
			   }
			   $("#uFileTypeSubId").combobox("loadData", data2);
			   $('#uFileTypeSubId').combobox('setValue', filetypeSubId);
			   
			   var hiddUpdateId = "<tr><td><input id='updateId' name='updateId' type='hidden' value=" + json[4] + "></input></td></tr>";
			   $("#fileListTab").html($("#fileListTab").html()+hiddUpdateId);
		   }
		});
}






// 保存修改后的文件
function saveUpdateFileForAudit(){
	var fileTypeId = $('#uFileTypeId').combobox('getValue'); 
	var fileTypeSubId = $('#uFileTypeSubId').combobox('getValue');
	var updateId = $("#updateId").val();
	jQuery.ajaxFileUpload({     
			       url : 'upload!saveFileForUpdate.do',// 用于文件上传的服务器端请求地址
			      type : "post",  
			      data: { fileTypeId: fileTypeId, fileTypeSubId: fileTypeSubId, updateId: updateId }, 
		      dataType : "json",     
		       timeout : 300000,     
		     secureuri : false,    // 一般设置为false     
		 fileElementId : 'ufile', // 文件上传空间的id属性     
			     error : function(XMLHttpRequest, textStatus, errorThrown) 
			     {  
			    	alert("失败");     
			     },     
			   success : function(json) 
			   {    
				   var str = "<tr><td><label>&nbsp;<img src='images/point1.gif'>&nbsp;&nbsp;<a href='javascript:seeFileCententForAudit(\"" + json.id + "\")'><font color='red'>" + json.fileNumber + json.fileDisplayname + "</font></a>&nbsp;&nbsp;"
			   		+ "&nbsp;&nbsp;<a class='a_removeCurrentFile' href='javascript:deleteFileForAudit(\"" + json.id + "\")'>删除</a>"
			   		+ "<input id='" + json.id +"' name='file_params' type='hidden' value='" + json.id + "' />"
			   		+ "</label></td></tr>";
				   $("#fileListTab").html($("#fileListTab").html()+str);
			   }
	}); 
	$('#updateDIV').window('close');
}




// 保存添加的文件
function saveAddFileForAudit(){
	var srcGroupId = $("#executionIdAudit").val();
	alert(srcGroupId);
	var fileTypeId = $('#fileTypeId').combobox('getValue'); 
	var fileTypeSubId = $('#fileTypeSubId').combobox('getValue');
	if(!fileTypeId || !fileTypeSubId){
		alert("请选择附件类型！");
		$('#fileSpan').html('<input id="file" name="file" type="file" onfocus="beforValidataForAudit();" onchange="saveAddFileForAudit()" />');
		return;
	}
	
	groupId = srcGroupId;
	var custCode = $("input[name='xf_f_formRecordId']").val(); 
	jQuery.ajaxFileUpload({     
			       url : 'upload!saveFileForAudit.do',// 用于文件上传的服务器端请求地址
			      type : "post",  
			      data: { fileTypeId: fileTypeId, fileTypeSubId: fileTypeSubId, groupId: groupId, custCode: custCode }, 
		      dataType : "json",     
		       timeout : 300000,     
		     secureuri : false,    // 一般设置为false     
		 fileElementId : 'file', // 文件上传空间的id属性     
			     error : function(XMLHttpRequest, textStatus, errorThrown) 
			     {  
			    	alert("失败");     
			     },     
			   success : function(json) 
			   {    
				   if(json == "type"){
					   alert("该附件类型不在允许上传的范围内!");
				   }else if(json == "size"){
					   alert("附件大小超过10M不允许上传!");
				   }else{
					   var str = "<tr><td><label>&nbsp;<img src='images/point1.gif'>&nbsp;&nbsp;<a href='javascript:seeFileCententForAudit(\"" + json.id + "\")'><font color='red'>" + json.fileNumber + json.fileDisplayname + "</font></a>&nbsp;&nbsp;"
					   		+ "&nbsp;&nbsp;<a class='a_removeCurrentFile' href='javascript:deleteFileForAudit(\"" + json.id + "\")'>删除</a>"
					   		+ "<input id='" + json.id +"' name='file_params' type='hidden' value='" + json.id + "' />"
					   		+ "</label></td></tr>";
					   $("#fileListTab").html($("#fileListTab").html()+str);
					   $('#fileSpan').html('<input id="file" name="file" type="file" onfocus="beforValidataForAudit();" onchange="saveAddFileForAudit()" /><font color="red">（附件大小应小于10MB）</font>');
				   }
			    }
	}); 
}

















