function bindHandleForProselection(){
	$('.xf-proselection').combobox({
		onSelect: function(param){
			var projectId = param.value;
			jQuery.ajax({
	            type: "POST",
	            dataType: "json",
	            async:false,
	            url: "form!showDetails.do?rId="+projectId,
	            success: function(data) {
	    			//alert(data.msg);
	    			//xform.setValue(eval('(' + data.msg + ')'));
	    			populatPropsFunc(eval('(' + data.msg + ')'));
	            },
	            error: function(data) {
	            }
	        });
		}
	});
}
