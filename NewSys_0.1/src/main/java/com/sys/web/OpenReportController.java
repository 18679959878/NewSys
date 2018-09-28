package com.sys.web;

import javax.faces.flow.FlowScoped;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.annotation.FPValidatePermission;
import com.sys.dao.OpenReportMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.FinalProjectTitel;
import com.sys.entity.OpenReport;
import com.sys.entity.Student;
import com.sys.service.OpenReportService;

@Controller
@RequestMapping("/or")
public class OpenReportController {
	@Autowired
	private HttpSession session;
	@Autowired
	private OpenReportService openReportService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission(stageName = FPValidatePermission.OPEN_REPORT_STAGE)
	public Result<NullData> saveOrUpdate(String stuId, String basisAndSignificance, String situation,
			String researchContent, String proposal, String literature, String otherInstructions, String agree) {
		Student student = (Student) session.getAttribute("student");
		stuId = student.getStuId();
		OpenReport openReport = openReportService.select(stuId).getData();
		if (openReport == null) {
			openReport = new OpenReport();
			openReport.setStuId(stuId);
		}
		openReport.setBasisAndSignificance(basisAndSignificance);
		openReport.setSituation(situation);
		openReport.setResearchContent(researchContent);
		openReport.setProposal(proposal);
		openReport.setLiterature(literature);
		openReport.setOtherInstructions(otherInstructions);
		openReport.setAgree(agree);
		return openReportService.saveOrUpdate(openReport);
	}

	@RequestMapping(value = "/t/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission(stageName = FPValidatePermission.OPEN_REPORT_STAGE)
	public Result<NullData> saveOrUpdate(String stuId, String agree, boolean isLock, int score) {
		OpenReport openReport = openReportService.select(stuId).getData();
		openReport.setAgree(agree);
		openReport.setIsLock(isLock);
		openReport.setScore(score);
		return openReportService.saveOrUpdate(openReport);
	}

	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission(stageName = FPValidatePermission.OPEN_REPORT_STAGE)
	public Result<OpenReport> select(String stuId) {
		return openReportService.select(stuId);
	}
}
