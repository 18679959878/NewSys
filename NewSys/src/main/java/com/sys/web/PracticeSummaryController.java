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
import com.sys.entity.PracticeSituation;
import com.sys.entity.PracticeSummary;
import com.sys.entity.Student;
import com.sys.service.PracticeSituationService;
import com.sys.service.PracticeSummaryService;

@Controller
@RequestMapping(("/summary"))
public class PracticeSummaryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private PracticeSummaryService practiceSummaryService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<NullData> saveOrUpdate(String stuId, String summary) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		PracticeSummary practiceSummary = new PracticeSummary(stuId, summary);
		return practiceSummaryService.saveOrUpdate(practiceSummary);
	}
	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<PracticeSummary> select(String stuId) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		return practiceSummaryService.select(stuId);
	}
}
