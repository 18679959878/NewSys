/**
 * 
 */
function createStudentData(className) {
	$.ajax({
		url : '/NewSys/student/class/select',
		type : 'POST',
		timeout : 5000, //超时时间
		data : "className=" + className,
		dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
		success : function(data) {
			if (data.success == true) {
				var data2 = data.data;
				var studentList = data2.students;
				var stuIdNodes = document.getElementsByName("stuId");
				var stuNameNodes = document.getElementsByName("stuName");
				var classNameNodes = document.getElementsByName("className");
				var emailNodes = document.getElementsByName("email");
				var passwordNodes = document.getElementsByName("password");
				var worksTeaNodes = document.getElementsByName("worksTea");
				var fieldworkTeaNodes = document.getElementsByName("fieldworkTea");
				var updUrlNodes = document.getElementsByName("updUrl");
				var delUrlNodes = document.getElementsByName("delUrl");
				
				for (var i = 0; i < stuIdNodes.length; i++) {
					if (i < studentList.length) {
						stuIdNodes[i].innerHTML = studentList[i].stuId;
						stuNameNodes[i].innerHTML = studentList[i].stuName;
						classNameNodes[i].innerHTML = studentList[i].className;
						emailNodes[i].innerHTML = studentList[i].email;
						passwordNodes[i].innerHTML = studentList[i].password;
						worksTeaNodes[i].innerHTML = studentList[i].worksTea;
						fieldworkTeaNodes[i].innerHTML = studentList[i].fieldworkTea;
						updUrlNodes[i].href = "admin_class_student_upd.html?stuId=" + studentList[i].stuId + "&stuName="
						+ studentList[i].stuName + "&className=" + studentList[i].className + "&email=" + studentList[i].email
						+ "&password=" + studentList[i].password + "&worksTea=" + studentList[i].worksTea + "&fieldworkTea="
						+ studentList[i].fieldworkTea;
						delUrlNodes[i].id = studentList[i].stuId + "(" + studentList[i].stuName + ")";
					} else {
						//						alert("测试");
						stuIdNodes[i].innerHTML = "";
						stuNameNodes[i].innerHTML = "";
						classNameNodes[i].innerHTML = "";
						emailNodes[i].innerHTML = "";
						passwordNodes[i].innerHTML = "";
						worksTeaNodes[i].innerHTML = "";
						fieldworkTeaNodes[i].innerHTML = "";
						updUrlNodes[i].href = "admin_class_student_upd.html";
					}

				}
				document.getElementById("upload").href = "upload.html?type=student_e&nowPage=" + nowPage;
			}
		}
	});
}

function deleteStudent(id) {
	var stuId = id.substr(0, 8);
	alert(stuId);
	if (confirm("是否删除学生--" + id + "?\u000d注意!!!删除学生后与该班级相关信息都将被删除，包括学生成绩等信息")) {
		$.ajax({
			url : '/NewSys/student/delete',
			type : 'POST',
			timeout : 5000, //超时时间
			data : "stuId=" + stuId,
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