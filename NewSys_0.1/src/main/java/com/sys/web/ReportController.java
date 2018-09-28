package com.sys.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.annotation.FPValidatePermission;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DesignProcess;
import com.sys.entity.Report;
import com.sys.entity.Student;
import com.sys.service.DesignProcessService;
import com.sys.service.ReportService;
@Controller
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private HttpSession session;
	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission
	public Result<NullData> saveOrUpdate(String stuId,String stageName, String reportText, String guidance) {
		Student student = (Student) session.getAttribute("student");
		stuId = student.getStuId();
		Report report = reportService.select(stuId, stageName).getData();
		if(report==null){
			report = new Report();
			report.setStuId(stuId);
			report.setStageName(stageName);
		}
		report.setReportText(reportText);
		report.setGuidance(guidance);
		return reportService.saveOrUpdate(report);
	}

	@RequestMapping(value = "t/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission
	public Result<NullData> saveOrUpdate(String stuId, String stageName, String reportText, String guidance,
			int score, boolean isLock) {
		return reportService.saveOrUpdate(stuId, stageName,reportText, guidance, score, isLock);
	}

	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission
	public Result<Report> select(String stuId, String stageName) {
		return reportService.select(stuId, stageName);
	}
}
