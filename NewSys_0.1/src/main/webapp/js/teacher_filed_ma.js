/**
 * 
 */
var maxPage = 0;
var nowPage = 1;
function createData(nowPage) {
	nowPage = nowPage;
	$.ajax({
		url : '/NewSys/teacher/ft/page',
		type : 'GET', //GET
		timeout : 5000, //超时时间
		data : "queryPage=" + nowPage,
		dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data) {
			if (data.success == true) {
				var data2 = data.data;
				maxPage = data2.maxPage;
				var pageList = data2.pageList;
				var stuNameNodes = document.getElementsByName("stuName");
				var stuIdNodes = document.getElementsByName("stuId");
				var delUrlNodes = document.getElementsByName("delUrl")
				//var nowStageNodes = document.getElementsByName("nowStage");
				//var compStageNodes = document.getElementsByName("compStage");
				for (var i = 0; i < 10; i++) {
					if (i < pageList.length) {
						stuNameNodes[i].innerHTML = pageList[i].stuName;
						stuIdNodes[i].innerHTML = pageList[i].stuId;
						//nowStageNodes[i].innerHTML = pageList[i].nowStage;
						//compStageNodes[i].innerHTML = pageList[i].compStage;
						delUrlNodes[i].id = pageList[i].stuId;
					} else {
						stuNameNodes[i].innerHTML = "";
						stuIdNodes[i].innerHTML = "";
						//nowStageNodes[i].innerHTML = "";
						//compStageNodes[i].innerHTML = "";
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
function deleteFieldStu(id) {
	if (confirm("是否删除指导学生--" + id + "?")) {
		$.ajax({
			url : '/NewSys/teacher/ft/delete',
			type : 'POST', //GET
			timeout : 5000, //超时时间
			data : "stuId=" + id,
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