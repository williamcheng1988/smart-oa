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
		//mode = eval(resources);
		mode = calcArith(resources);
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
//计算值
function calcArith(strV) {
	//if (("+-*/").indexOf(strV.substring(0, 1)) >= 0) {strV = strV.substring(1, strV.length);}
	//计算加法
	return exA(strV);
}
function exA(strV) {
	var dJe = 0.0;
	if (strV == "") {return dJe;}
	if (strV.indexOf("+") > 0) {
		var arryTemp = strV.split("+");
		dJe = exP(arryTemp[0]);
		for (var i = 1; i < arryTemp.length; i++) {
			//dJe += exP(arryTemp[i]);
			dJe=accAdd(dJe,exP(arryTemp[i]));
		}
	} else {
		dJe = exP(strV);
	}
	return dJe;
}
//计算减法
function exP(strV) {
	var dJe = 0.0;
	if (strV == "") {
		return dJe;
	}
	if (strV.indexOf("-") > 0) {
		var arryTemp = strV.split("-");
		dJe = exX(arryTemp[0]);
		for (var i = 1; i < arryTemp.length; i++) {
			//dJe -= exX(arryTemp[i]);
			dJe=accSub(dJe,exX(arryTemp[i]));
		}
	} else {
		dJe = exX(strV);
	}
	return dJe;
}
//计算乘法
function exX(strV) {
	var dJe = 0.0;
	if (strV == "") {
		return dJe;
	}
	if (strV.indexOf("*") > 0) {
		var arryTemp = strV.split("*");
		dJe = exX(arryTemp[0]);
		for (var i = 1; i < arryTemp.length; i++) {
			//dJe *= exC(arryTemp[i]);
			dJe=accMul(dJe,exC(arryTemp[i]));
		}
	} else {
		dJe = exC(strV);
	}
	return dJe;
}
//计算除法
function exC(strV) {
	var dJe = 1.0;
	if (strV == "") {
		return dJe;
	}
	if (strV.indexOf("/") > 0) {
		var arryTemp = strV.split("/");
		dJe = exX(arryTemp[0]);
		for (var i = 1; i < arryTemp.length; i++) {
			//dJe /= parseFloat(arryTemp[i]);
			dJe=accDiv(dJe,parseFloat(arryTemp[i]));
		}
	} else {
		dJe = parseFloat(strV);
	}
	return dJe;
}

function accAdd(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
	m=Math.pow(10,Math.max(r1,r2));
	return (parseInt(arg1*m)+parseInt(arg2*m))/m;
}

function accSub(arg1,arg2){
	return accAdd(arg1,-arg2);
}

function accMul(arg1,arg2)
{
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length;}catch(e){}
	try{m+=s2.split(".")[1].length;}catch(e){}
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}

function accDiv(arg1,arg2){
	var t1=0,t2=0,r1,r2;
	try{t1=arg1.toString().split(".")[1].length;}catch(e){}
	try{t2=arg2.toString().split(".")[1].length;}catch(e){}
	with(Math){
		r1=Number(arg1.toString().replace(".",""));
		r2=Number(arg2.toString().replace(".",""));
		return (r1/r2)*pow(10,t2-t1);
	}
}
