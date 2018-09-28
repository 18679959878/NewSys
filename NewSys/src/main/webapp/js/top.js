/**
 * 
 */
function getUserName() {
	$.ajax({
		url : '/NewSys/admin/name',
		type : 'GET', //GET
		timeout : 5000, //超时时间
		dataType : 'text', //返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data) {
			$("#name").html(data);
		}
	})
}