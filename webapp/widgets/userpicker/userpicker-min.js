var createUserPicker=function(a){if(!a){a={modalId:"userPicker",multiple:false,url:"/mossle-web-user/default/rs/user/search"}}if($("#"+a.modalId).length==0){$(document.body).append('<div id="'+a.modalId+'" class="modal hide fade">  <div class="modal-header">    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>    <h3>\u9009\u62e9\u7528\u6237</h3>  </div>  <div class="modal-body">      <!--	  <article class="m-blank">	    <div class="pull-left">		  <form name="userForm" method="post" action="javascript:void(0);return false;" class="form-inline m-form-bottom">    	    <label for="user_username">\u8d26\u53f7:</label>			<input type="text" id="user_username" name="filter_LIKES_username" value="">			<button class="btn btn-small" onclick="document.userForm.submit()">\u67e5\u8be2</button>		  </form>		</div>	    <div class="m-clear"></div>	  </article>      -->      <article class="m-widget">        <header class="header">		  <h4 class="title">\u7528\u6237\u5217\u8868</h4>		</header>		<div class="content"><form id="userPickerForm" name="userPickerForm" method="post" action="#" class="m-form-blank">  <table id="userPickerGrid" class="m-table table-hover">    <thead>      <tr>        <th width="10" class="m-table-check">&nbsp;</th>        <th>\u8d26\u53f7</th>      </tr>    </thead>    <tbody id="userPickerBody">      <tr>        <td><input id="selectedItem1" type="checkbox" class="selectedItem" name="selectedItem" value="1"></td>        <td>admin</td>      </tr>      <tr>        <td><input id="selectedItem2" type="checkbox" class="selectedItem" name="selectedItem" value="2"></td>        <td>user</td>      </tr>    </tbody>  </table></form>        </div>      </article>  </div>  <div class="modal-footer">    <span id="userPickerResult"></span>    <a id="userPickerBtnClose" href="#" class="btn" data-dismiss="modal">\u5173\u95ed</a>    <a id="userPickerBtnSelect" href="#" class="btn btn-primary">\u9009\u62e9</a>  </div></div>')}$(document).delegate(".userPicker .add-on","click",function(b){$("#"+a.modalId).data("userPicker",$(this).parent());$("#"+a.modalId).modal();$.ajax({url:a.url,data:{username:""},success:function(f){var d="";for(var c=0;c<f.length;c++){var e=f[c];d+='<tr><td><input id="selectedItem'+c+'" type="radio" class="selectedItem" name="selectedItem" value="'+e.id+'" title="'+e.displayName+'"></td><td><label for="selectedItem'+c+'">'+e.displayName+"</label></td></tr>"}$("#"+a.modalId+"Body").html(d)}})});$(document).delegate("#"+a.modalId+"BtnSelect","click",function(c){$("#"+a.modalId).modal("hide");var b=$("#"+a.modalId).data("userPicker");b.children("input[type=hidden]").val($(".selectedItem:checked").val());b.children("input[type=text]").val($(".selectedItem:checked").attr("title"))})};
