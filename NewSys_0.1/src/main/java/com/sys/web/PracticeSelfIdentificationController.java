package com.sys.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSelfIdentification;
import com.sys.entity.PracticeSummary;
import com.sys.entity.Student;
import com.sys.service.PracticeSelfIdentificationService;
import com.sys.service.PracticeSummaryService;

@Controller
@RequestMapping("identification")
public class PracticeSelfIdentificationController {
	@Autowired
	private HttpSession session;
	@Autowired
	private PracticeSelfIdentificationService practiceSelfIdentificationService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<NullData> saveOrUpdate(String stuId, String identification) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		PracticeSelfIdentification practiceSelfIdentification = new PracticeSelfIdentification(stuId, identification);
		return practiceSelfIdentificationService.saveOrUpdate(practiceSelfIdentification);
	}

	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<PracticeSelfIdentification> select(String stuId) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		return practiceSelfIdentificationService.select(stuId);
	}
}
