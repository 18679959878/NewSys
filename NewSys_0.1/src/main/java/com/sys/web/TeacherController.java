package com.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Teacher;
import com.sys.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	// ҳ���С
	private final int pageSize = 10;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<Teacher> add(String teaAccount, String teaName, String email, String password) {
		System.out.println("����");
		if (teaAccount != null && !teaAccount.equals("") & teaName != null && !teaName.equals("") && password != null
				&& !password.equals("")) {
			return teacherService.save(new Teacher(teaAccount, teaName, email, password));
		}
		return new Result<Teacher>("���ʧ�ܣ��˺�/����/����Ϊ��");
	}

	/**
	 * ��ѯ��ǰҳ����Ϣ
	 * 
	 * @param queryPage
	 *            ��ѯ��ҳ��
	 * @return ���ص�ҳ����Ϣ
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> classPageData(int queryPage) {
		return teacherService.pageTeacher(pageSize, queryPage);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<ArrayList<Teacher>> allTeacher() {
		return teacherService.allTeacher();

	}

	/**
	 * ɾ����ʦ��Ϣ
	 * 
	 * @param teaAccount
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> deleteTeacher(String teaAccount) {
		return teacherService.deleteTeacher(teaAccount);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> updTeacher(String teaAccount, String teaName, String email, String password) {
		return teacherService.update(new Teacher(teaAccount, teaName, email, password));
	}

	@RequestMapping(value = "/ft/add", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> addFiledStudents(String stuId) {
		return teacherService.addFiledStudents(stuId);
	}

	@RequestMapping(value = "/ft/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<Map<String, Object>> fieldStuPage(int queryPage) {
		return teacherService.selectFiledStuPage(queryPage, pageSize);

	}

	@RequestMapping(value = "/ft/stage/page", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public Result<Map<String, Object>> fieldStuPage(int queryPage, String stageName) {
		return teacherService.selectFiledStuPage(queryPage, pageSize, stageName);
	}
	
	@RequestMapping(value = "/ft/wr/stage/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<Map<String, Object>> weekRecordStuPage(String stageName) {
	return	teacherService.selectWeekRecordStuPage(stageName);

	}

	@RequestMapping(value = "/ft/delete", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> deleteFieldStu(String stuId) {
		return teacherService.deleteFieldStu(stuId);
	}
}
