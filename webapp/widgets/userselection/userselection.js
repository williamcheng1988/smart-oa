$(function() {
	
	countOfUserSelection = 1;
	
	var userSelectionFlag = true;
	var roleFieldName;
	var userFieldName;
	
	$(document).delegate('.userselectionAdd', 'click', function(e) {
		if(userSelectionFlag){
			roleFieldName = $(this).parent().find("select").eq(0).attr("textboxname");
			userFieldName = $(this).parent().find("select").eq(1).attr("textboxname");
			
			userSelectionFlag = false;
			
			//此步骤为以后编辑页面做准备
			var tempArray = roleFieldName.split("~");
			if(tempArray.length>3){
				roleFieldName = roleFieldName.substr(0,roleFieldName.lastIndexOf("~"));
				userFieldName = userFieldName.substr(0,userFieldName.lastIndexOf("~"));
			}
		}
		
		var currentDiv = this.parentNode;
		var parentDiv = currentDiv.parentNode;
		var targetDiv = document.createElement("div");
		
		var milis = (new Date()).getTime();
		var id = "xf-handler"+milis;
		var roleName = roleFieldName+"~"+milis;
		var userName = userFieldName+"~"+milis;
		
		targetDiv.className= "xf-handler UserSelection";
		targetDiv.setAttribute("id",id);
		targetDiv.setAttribute("style","margin:10px 0px");
		targetDiv.innerHTML = getAttr();
		targetDiv.childNodes[0].name=roleName;
		targetDiv.childNodes[0].setAttribute("id",roleName);
		targetDiv.childNodes[1].name=userName;
		targetDiv.childNodes[1].setAttribute("id",userName);
		
		parentDiv.insertBefore(targetDiv,currentDiv);
		countOfUserSelection++;
		
		jQuery.parser.parse('#'+id);
	});
	
	
	function getAttr(){
		//alert(UserSelection_new_inner_html);
		if(UserSelection_new_inner_html!='') return UserSelection_new_inner_html;
		var html = '<select class="easyui-combobox userSelectionCom" style="width:135px" name="'+this.name+'-'+xf_f_name_prefix+'us_r-@0'+'" '
		 + 'data-options="url:\'formTemplate!fetchRoles.do\',method:\'get\',valueField:\'value\',textField:\'name\',panelHeight:\'auto\'"'
		 + (this.required?'required ':'')
		 + (this.readOnly ? 'disabled' : '') + '>';
		 html += '</select>';

		 html += '<input class="easyui-textbox userPicker" data-options="icons:[{iconCls:\'icon-man\',handler:showUserPickTab}]" type="text" name="'+this.name+'-'+xf_f_name_prefix+'us_u-@1'+'" alias="' + this.alias +'" '+ (this.readOnly ? 'readOnly="" ' : '') 
		 		+ (this.required?'required ':'')
		 		+' reportFlag='+this.reportFlag+' ' +'>';
		 
		 html += '<span class="add-on" style="padding-top: 2px; padding-bottom: 2px;border-radius: 0 4px 4px 0;"><i style="cursor:pointer;" class="icon-user '+(this.readOnly ? '':' userPickerBtn')+'"></i></span>';
		 html += '<span '+(this.readOnly ? '':'class="userselectionAdd"')+' style="cursor: pointer;padding: 2px;font-size: 12px;">+</span>';
		 html += '<span '+(this.readOnly ? '':'class="userselectionRemove"')+' style="cursor: pointer;padding: 2px;font-size: 12px;">-</span>';
		 
		 return html;
	}
	
	$(document).delegate('.userselectionRemove', 'click', function(e) {
		//alert();
		if(countOfUserSelection==1){
			//showMsg('至少保留一个控件！');
			$(this).focus();
			return false;
		}
		
		var currentDiv = this.parentNode;
		var parentDiv = currentDiv.parentNode;
		parentDiv.removeChild(currentDiv);
		countOfUserSelection--;
	});
})

//项目选择器，处理事件
function populatPropsFunc(props){
	if(!props){return false;}
	
	$('#Form1').form('load',props);
	for(var key in props){
		//alert(key+':'+(typeof(props[key])));
		//UserSelection
		if('object'==typeof(props[key]) && !(props[key] instanceof Array)){
			//alert(key+':'+(typeof(props[key])));
			var roleFieldName_compare;
			var parentNode;
			var currentDiv;
			//如果不存在，则不处理后续代码
			if(!UserSelection_new_inner_html || !$('.UserSelection')){
				return false;
			}else{
				currentDiv = $('.UserSelection');
				parentNode = currentDiv.parent();
				
				roleFieldName_compare = $('.UserSelection').find(".easyui-combobox").attr("comboname");
				$('.UserSelection').html('');
			}
			//alert(countOfUserSelection);
			//将控件数重置为0
			countOfUserSelection = 0;
			var userSelVal = props[key];
			var userselectionArray = new Array();
			for(var key in userSelVal){
				var userSelValGroup = userSelVal[key];
				
				var role0_name;
				var role0_value;
				var user1_name;
				var user1_value;
				if(userSelValGroup[0].innerName.indexOf('@0')>0){
					role0_name  = userSelValGroup[0].innerName;
					role0_value = userSelValGroup[0].value;
				}else{
					role0_name  = userSelValGroup[1].innerName;
					role0_value = userSelValGroup[1].value;
				}
				if(userSelValGroup[1].innerName.indexOf('@1')>0){
					user1_name  = userSelValGroup[1].innerName;
					user1_value = userSelValGroup[1].value;
				}else{
					user1_name  = userSelValGroup[0].innerName;
					user1_value = userSelValGroup[0].value;
				}
				//if the name is not equal the specific name,then break
				if(role0_name.indexOf(roleFieldName_compare)!=0){
					$('.UserSelection').html(UserSelection_new_inner_html);
					break;
				}
				
				userselectionArray[role0_name] = role0_value;
				userselectionArray[user1_name] = user1_value;
				
				var targetDiv = document.createElement("div");
				targetDiv.className= "xf-handler UserSelection";
				targetDiv.setAttribute("style","margin:10px 0px");
				targetDiv.innerHTML = UserSelection_new_inner_html;
				targetDiv.childNodes[0].name=role0_name;
				targetDiv.childNodes[1].name=user1_name;
				targetDiv.childNodes[0].setAttribute("id",role0_name);
				targetDiv.childNodes[1].setAttribute("id",user1_name);
				
				$('.UserSelection').parent().append(targetDiv);
				
				countOfUserSelection++;
			}
			
			jQuery.parser.parse($('.UserSelection').parent());
			//alert(countOfUserSelection);
			$('#Form1').form('load',userselectionArray);
		}
	}
}
