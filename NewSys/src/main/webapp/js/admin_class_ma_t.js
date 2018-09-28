/**
 * 
 */

function createGrade() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth();
	if (month >= 8) {
		year--;
	}
	for (var i = 1; i < 5; i++) {
		document.getElementById("grade_t" + i).innerHTML = year + "级";
		document.getElementById("grade" + i).value = year + "级";
		year--;
	}
}
$(".addClass").Validform({
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
$(".updClass").Validform({
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
			objtip.text("修改成功");
		//			window.location.href = "/GraduateSystem/admin.html"
		} else {
			var objtip = $(".sec_error_box");
			objtip.text(data.error);
		}
	}
});