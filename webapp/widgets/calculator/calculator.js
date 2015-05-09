$(function(){
	// 大写控件 处理函数
	$('.upperDigit').each(function (){
		var upperDigit = $(this);
		var resources = $(this).attr('resources');
		var regObj = new RegExp('#\\w+#','g');
		
		upperDigit.textbox('setValue','零元整');
		
		var res_array = resources.match(regObj);
		
		if(res_array!=null && res_array!='null'&&res_array.length>0){
			var res = res_array[0];
			var unique = res.substring(1,res.length-1);
			
			$('#xf_f_'+unique).textbox({
				onChange:function(){
					var upperValue = upDigit($(this).val());
					
					upperDigit.textbox('setValue',upperValue);
				}
			});
		}
	})

	//合计控件 处理函数
	$('.calculator').each(function (){
		//#number1#+#number2#
		var id = this.id;
		var totalObj = $(this);
		var resources = $(this).attr('resources');
		if(!resources){return false;}
		
		//初始化
		totalObj.textbox('setValue',replacePattern(totalObj,resources));
		
		var regObj = new RegExp('#\\w+#','g');
		
		//#number1#+#number2#
		var res_array = resources.match(regObj);
		
		for(var key in res_array){
			var res = res_array[key];
			var regTmp = new RegExp(res);
			var unique = res.substring(1,res.length-1);
			$('#xf_f_'+unique).textbox({
				onChange:function(){
					var keyAndvalue={};
					keyAndvalue[this.id] = $(this).val();
					
					totalObj.textbox('setValue',replacePattern(totalObj,resources,keyAndvalue));
				}
			});
			
			//设置 “合成控件” 精度 :如果存在精度值，则设置,且以最后一个 “值源”精度为准
			if(totalObj.hasClass('easyui-numberbox')){
				//debugger;
				//var precisionVal = $('#xf_f_'+unique).textbox('options').precision;
				//precisionVal = precisionVal?precisionVal:0;
				
				//$('#'+id).numberbox({precision:precisionVal,groupSeparator:','});
			}
		}
	})
})
//正则替换元素
function replacePattern(target,resources,keyAndvalue){
	if(!resources){return false;}
	var regObj = new RegExp('#\\w+#','g');
	var res_array = resources.match(regObj);
	//keyAndvalue 有值，证明为动态更新值，而不是初始化数据
	for(var key in res_array){
		var res = res_array[key];
		var regTmp = new RegExp(res);
		var name = res.substring(1,res.length-1);
		if(keyAndvalue && keyAndvalue['xf_f_'+name]){
			resources = resources.replace(regTmp,keyAndvalue['xf_f_'+name]);
		}else{
			var value = $('#xf_f_'+name).val();
			var defaultVal = target.hasClass('easyui-numberbox')?0:'';
			
			resources = resources.replace(regTmp,value?value:defaultVal);
		}
	}
	var mode;
	if(target.hasClass('easyui-numberbox')){
		mode = eval(resources);
	}else{
		mode = resources.split('+').join('');
		//mode = resources;
	}
	
	return mode;
}
//转换金额为大写
function upDigit(n){ 
	var fraction = ['角', '分']; 
	var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖']; 
	var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ]; 
	var head = n < 0? '欠': ''; 
	n = Math.abs(n); 
   
	var s = ''; 
   
	for (var i = 0; i < fraction.length; i++)  
	{ 
		s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, ''); 
	} 
	s = s || '整'; 
	n = Math.floor(n); 
   
	for (var i = 0; i < unit[0].length && n > 0; i++)  
	{ 
		var p = ''; 
		for (var j = 0; j < unit[1].length && n > 0; j++)  
		{ 
			p = digit[n % 10] + unit[1][j] + p; 
			n = Math.floor(n / 10); 
		} 
		s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s; 
	} 
	return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整'); 
}
