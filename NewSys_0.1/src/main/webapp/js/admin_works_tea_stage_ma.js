/**
 * 
 */
/**
 * 
 */
var maxPage = 0;
var nowPage = 1;
function createData(nowPage) {
	nowPage = nowPage;
	$.ajax({
		url : '/NewSys/class/page',
		type : 'GET', //GET
		timeout : 5000, //超时时间
		data : "queryPage=" + nowPage,
		dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data) {
			if (data.success == true) {
				var data2 = data.data;
				maxPage = data2.maxPage;
				var pageList = data2.pageList;
				var classNameNodes = document.getElementsByName("className");
				var gradeNodes = document.getElementsByName("grade");
				var educationNodes = document.getElementsByName("education");
				var filedTeaNodes = document.getElementsByName("filedTea");
				for (var i = 0; i < 10; i++) {
					if (i < pageList.length) {
						classNameNodes[i].innerHTML = pageList[i].className;
						gradeNodes[i].innerHTML = pageList[i].grade;
						educationNodes[i].innerHTML = pageList[i].education;
						filedTeaNodes[i].innerHTML = pageList[i].filedWorkTea;
					} else {
						classNameNodes[i].innerHTML = pageList[i].className;
						gradeNodes[i].innerHTML = pageList[i].grade;
						educationNodes[i].innerHTML = "";
						filedTeaNodes[i].innerHTML = "";
					}
				}
				var html = "<a href='javascript:void(0);' onclick='lastPage()' class='prev'>«</a>";
				for (var i = 1; i <= maxPage; i++) {
					html += "<a href='javascript:void(0);' onclick='createData(" + i + ")' id='" + i + "'>" + i + "</a>";
				}
				html += "<a href='javascript:void(0);' onclick='nextPage()' class='next'>»</a>";
				document.getElementById("page").innerHTML = html;
				document.getElementById(nowPage).className = "current";
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
function deleteClass(id) {
	if (confirm("是否删除班级--" + id + "?\u000d注意!!!删除班级后与该班级相关信息都将被删除，包括学生以及该班级学生成绩等信息")) {
		$.ajax({
			url : '/NewSys/class/delete',
			type : 'POST', //GET
			timeout : 5000, //超时时间
			data : "className=" + id,
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