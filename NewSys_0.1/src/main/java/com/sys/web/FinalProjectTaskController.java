package com.sys.web;

import javax.faces.annotation.RequestCookieMap;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.FinalProjectTask;
import com.sys.entity.FinalProjectTitel;
import com.sys.entity.Student;
import com.sys.service.FinalProjectTaskService;
import com.sys.service.FinalProjectTitelService;

@Controller
@RequestMapping("/fptask")
public class FinalProjectTaskController {
	@Autowired
	private FinalProjectTaskService finalProjectTaskService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<NullData> saveOrUpdate(String stuId, String contentAndRequired, String liberature) {
		return finalProjectTaskService.saveOrUpdate(new FinalProjectTask(stuId, contentAndRequired, liberature));
	}

	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<FinalProjectTask> select(String stuId) {
		return finalProjectTaskService.select(stuId);
	}
}
