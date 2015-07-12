function cookie(key, value, options) {/* cookie 设置 */
    // key and value given, set cookie...
    if (arguments.length > 1 && (value === null || typeof value !== "object")) {
        options = jQuery.extend({}, options);
        if (value === null) {
            options.expires = -1;
        }

        if (typeof options.expires === 'number') {
            var days = options.expires, t = options.expires = new Date();
            t.setDate(t.getDate() + days);
        }
        return (document.cookie = [
            encodeURIComponent(key), '=',
            options.raw ? String(value) : encodeURIComponent(String(value)),
            options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
            options.path ? '; path=' + options.path : '',
            options.domain ? '; domain=' + options.domain : '',
            options.secure ? '; secure' : ''
        ].join(''));
    }
    // key and possibly options given, get cookie...
    options = value || {};
    var result, decode = options.raw ? function (s) { return s; } : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

function chooseTheme(){
	var themeName = "default";
	if(cookie('easyuiThemeName')){
		themeName = cookie('easyuiThemeName');
	}
	//切换主题
	if(themeName=="default"){
		themeName = "gray";
	}else if(themeName=="gray"){
		themeName = "bootstrap";
	}else if(themeName=="bootstrap"){
		themeName = "black";
	}else if(themeName=="black"){
		themeName = "metro";
	}else if(themeName=="metro"){
		themeName = "metro-blue";
	}else if(themeName=="metro-blue"){
		themeName = "metro-gray";
	}else if(themeName=="metro-gray"){
		themeName = "metro-green";
	}else if(themeName=="metro-green"){
		themeName = "metro-orange";
	}else if(themeName=="metro-orange"){
		themeName = "metro-red";
	}else if(themeName=="metro-red"){
		themeName = "ui-cupertino";
	}else if(themeName=="ui-cupertino"){
		themeName = "ui-dark-hive";
	}else if(themeName=="ui-dark-hive"){
		themeName = "ui-pepper-grinder";
	}else if(themeName=="ui-pepper-grinder"){
		themeName = "ui-sunny";
	}else if(themeName=="ui-sunny"){
		themeName = "default";
	}
	changeThemeFun(themeName);
}

function getThemeName(){
	var themeName = "default";
	if(cookie('easyuiThemeName')){
		themeName = cookie('easyuiThemeName');
	}
	return themeName;
}

function changeThemeFun(themeName) {/* 更换主题 */
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href ='/smartoa/themes/' + themeName + '/easyui.css';
    $easyuiTheme.attr('href', href);

    var $iframe = $('iframe');
    if ($iframe.length > 0) {
        for ( var i = 0; i < $iframe.length; i++) {
            var ifr = $iframe[i];
            $(ifr).contents().find('#easyuiTheme').attr('href', href);
        }
    }
    cookie('easyuiThemeName', themeName,{
    	path:"/smartoa",
        expires : 365
    });
};
if (cookie('easyuiThemeName')) {
    changeThemeFun(cookie('easyuiThemeName'));
}

// 切换首页
function switchTopic(action){
	if(action){
		location.href=action+".do";
		//存入 cookie
	    cookie('homePage',(action+".do"),{
	    	path:"/smartoa",
	        expires : 365
	    });
	}
}

function GetQueryParam(name){
	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null){
	  return unescape(r[2]);
	}
	return null;
}
