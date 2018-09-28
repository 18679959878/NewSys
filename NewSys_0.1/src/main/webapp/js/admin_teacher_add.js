/**
 * 
 */
$(".addTea").Validform({
	tiptype : function(msg, o, cssctl) {
		var objtip = $(".sec_error_box");
		cssctl(objtip, o.type);
		objtip.text(msg);
	},
	ajaxPost : true,
	beforeSubmit : function(curform) {
		return true;
	},
	callback : function(data) {
		if (data.success == true) {
			var objtip = $(".sec_error_box");
			objtip.text("添加成功");
		//			window.location.href = "/GraduateSystem/admin.html"
		} else {
			var objtip = $(".sec_error_box");
			objtip.text(data.error);
		}
	}
});