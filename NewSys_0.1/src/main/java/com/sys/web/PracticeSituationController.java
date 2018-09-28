package com.sys.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSituation;
import com.sys.entity.Student;
import com.sys.service.PracticeSituationService;

@Controller
@RequestMapping("/situation")
public class PracticeSituationController {
	@Autowired
	private HttpSession session;
	@Autowired
	private PracticeSituationService practiceSituationService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<NullData> saveOrUpdate(String stuId, int fullWork, int absenceLeave, int sickLeave, int late,
			int leaveEarly, int absent, String workSituation) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		PracticeSituation practiceSituation = new PracticeSituation(stuId, fullWork, absenceLeave, sickLeave, late,
				leaveEarly, absent, workSituation);
		practiceSituation.setIsLock(false);
		practiceSituation.setScore(0);
		return practiceSituationService.saveOrUpdate(practiceSituation);
	}
	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Result<PracticeSituation> select(String stuId) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		return practiceSituationService.select(stuId);
	}
}
