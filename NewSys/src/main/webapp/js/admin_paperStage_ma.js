/**
 * 
 */
var maxPage = 0;
var nowPage = 1;
var t_type = 1;
function createData(nowPage, type) {
	if (type == null) {
		type = t_type;
	}
	t_type = type;
	nowPage = nowPage;
	$.ajax({
		url : '/NewSys/ds/page',
		type : 'GET', //
		timeout : 5000, //超时时间
		data : "queryPage=" + nowPage + "&type=" + type,
		dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data) {
			if (data.success == true) {
				var data2 = data.data;
				maxPage = data2.maxPage;
				var pageList = data2.pageList;
				var stageNameNodes = document.getElementsByName("stageName");
				var gradeNodes = document.getElementsByName("grade");
				var educationNodes = document.getElementsByName("education");
				var startDateNodes = document.getElementsByName("startDate");
				var endDateNodes = document.getElementsByName("endDate");
				var updUrlNodes = document.getElementsByName("updUrl");
				var delUrlNodes = document.getElementsByName("delUrl");
				for (var i = 0; i < 10; i++) {
					if (i < pageList.length) {
						stageNameNodes[i].innerHTML = pageList[i].stageName;
						updUrlNodes[i].href = "admin_stage_upd.html?stageName=" + pageList[i].stageName + "&grade=" + pageList[i].grade + "&education=" + pageList[i].education
							+ "&startDate=" + pageList[i].startDate.substr(0, pageList[i].startDate.indexOf(" ")) + "&endDate=" + pageList[i].endDate.substr(0, pageList[i].endDate.indexOf(" ")) + "&type=" + type;
						gradeNodes[i].innerHTML = pageList[i].grade;
						educationNodes[i].innerHTML = pageList[i].education;
						startDateNodes[i].innerHTML = pageList[i].startDate.substr(0, pageList[i].startDate.indexOf(" "));
						endDateNodes[i].innerHTML = pageList[i].endDate.substr(0, pageList[i].endDate.indexOf(" "));
						delUrlNodes[i].id = pageList[i].grade + " " + pageList[i].education + " " + pageList[i].stageName;
					//	seeUrlNodes[i].href = "admin_class_student.html?className=" + pageList[i].className+"&nowPage="+nowPage;
					} else {
						stageNameNodes[i].innerHTML = "";
						updUrlNodes[i].href = "";
						gradeNodes[i].innerHTML = "";
						educationNodes[i].innerHTML = "";
						startDateNodes[i].innerHTML = "";
						endDateNodes[i].innerHTML = "";
						delUrlNodes[i].id = "";
					//	seeUrlNodes[i].href = "admin_class_student.html?className=" + pageList[i].className+"&nowPage="+nowPage;
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
		createData(nowPage, t_type);
	}
}
function nextPage() {
	if (nowPage == maxPage) {
		alert("没有下一页");
	} else {
		nowPage++;
		createData(nowPage, t_type);
	}
}
function deletePaperStage(id, type) {
	alert("测试");
	alert(id);
	var nodes = id.split(/\s+/);
	if (confirm("是否删除阶段--" + id + "?\u000d注意!!!删除阶段后与该阶段相关信息都将被删除，包括毕业设计相关记录")) {
		$.ajax({
			url : '/NewSys/ds/delete',
			type : 'GET',
			timeout : 5000, //超时时间
			data : "stageName=" + nodes[2] + "&grade=" + nodes[0] + "&education=" + nodes[1] + "&type=" + type,
			dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
			success : function(data) {
				if (data.success == true) {
					alert("删除成功");
					createData(nowPage, type);
				} else {
					alert(data.error);
				}
			}
		});
	}
}