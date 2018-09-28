package com.sys.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AdminService adminService;

	/**
	 * 给前端返回管理员姓名
	 * 
	 * @author 金小瑶
	 * @return 返回给前端的文本信息，即管理员姓名
	 */
	@RequestMapping(value="/name",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String adminName() {
		return adminService.getAdminName();
	}
}
