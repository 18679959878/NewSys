
package com.sys.web;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.service.AdminService;
import com.sys.service.StudentService;
import com.sys.service.TeacherService;

@Controller
@RequestMapping("/login")
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AdminService adminService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;

	/**
	 * 处理前端管理员登陆信息
	 * 
	 * @author 金小瑶
	 * @param account
	 *            前端传来的账号
	 * @param password
	 *            前端传来的密码
	 * @return 返回给前端的json数据
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<Map<Object, Object>> adminLogin(String account, String password) {
		return adminService.successLogin(account, password);
	}

	/**
	 * 处理前端学生登陆信息
	 * 
	 * @author 金小瑶
	 * @param account
	 *            前端传来的账号
	 * @param password
	 *            前端传来的密码
	 * @return 返回给前端的json数据
	 */
	@RequestMapping(value = "/student", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> studenLogin(String stuId, String password) {
			return studentService.studentLogin(stuId, password);

	}

	@RequestMapping(value = "/teacher", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> teaLogin(String teaAccount, String password) {
		return teacherService.login(teaAccount, password);
	}
}
