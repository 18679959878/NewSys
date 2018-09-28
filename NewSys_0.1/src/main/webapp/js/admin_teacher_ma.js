/**
 * 
 */
var maxPage = 0;
var nowPage = 1;
function createData(nowPage) {
	nowPage = nowPage;
	$.ajax({
		url : '/NewSys/teacher/page',
		type : 'GET', //POST
		timeout : 5000, //超时时间
		data : "queryPage=" + nowPage,
		dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data) {
			if (data.success == true) {
				var data2 = data.data;
				maxPage = data2.maxPage;		
				var pageList = data2.pageList;
				var teaAccountNodes = document.getElementsByName("teaAccount");
				var teaNameNodes = document.getElementsByName("teaName");
				var emailNodes = document.getElementsByName("email");
				var passwordNodes = document.getElementsByName("password");
				var updUrlNodes = document.getElementsByName("updUrl");
				var delUrlNodes = document.getElementsByName("delUrl");
				for (var i = 0; i < 10; i++) {
					if (i < pageList.length) {
						teaAccountNodes[i].innerHTML = pageList[i].teaAccount;
						updUrlNodes[i].href = "admin_teacher_upd.html?teaAccount=" + pageList[i].teaAccount + "&teaName=" + pageList[i].teaName + "&email=" + pageList[i].email + "&password=" + pageList[i].password;
						teaNameNodes[i].innerHTML = pageList[i].teaName;
						emailNodes[i].innerHTML = pageList[i].email;
						passwordNodes[i].innerHTML = pageList[i].password;
						delUrlNodes[i].id = pageList[i].teaAccount;
					} else {
						teaAccountNodes[i].innerHTML = "";
						updUrlNodes[i].href = "admin_teacher_upd.html";
						teaNameNodes[i].innerHTML = "";
						emailNodes[i].innerHTML = "";
						passwordNodes[i].innerHTML = "";
						delUrlNodes[i].id = "";
					}
				}
				var html = "<a href='javascript:void(0);' onclick='lastPage()' class='prev'>«</a>";
				for (var i = 1; i <= maxPage; i++) {
					html += "<a href='javascript:void(0);' onclick='createData(" + i + ")' id='" + i + "'>" + i + "</a>";
				}
				html += "<a href='javascript:void(0);' onclick='nextPage()' class='next'>»</a>";
				document.getElementById("page").innerHTML = html;
				document.getElementById(nowPage).className = "current";
				document.getElementById("upload").href = "upload.html?type=teacher_e&nowPage=" + nowPage;
			}
		}
	})
}

function lastPage() {
	if (nowPage <= 1) {
		alert("没有上一页");
	} else {
		nowPage--;
		createData(nowPage);
	}
}
function nextPage() {
	if (nowPage == maxPage) {
		alert("没有下一页");
	} else {
		nowPage++;
		createData(nowPage);
	}
}
function deleteTeacher(id) {
	if (confirm("是否删除教师--" + id + "?")) {
		$.ajax({
			url : '/NewSys/teacher/delete',
			type : 'GET', //GET
			timeout : 5000, //超时时间
			data : "teaAccount=" + id,
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			success : function(data) {
				if (data.success == true) {
					alert("删除成功");
					createData(nowPage);
				} else {
					alert(data.error);
				}
			}
		});
	}
}