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
import com.sys.entity.Student;
import com.sys.service.DesignProcessService;

@Controller
@RequestMapping("/dp")
public class DesignProcessController {
	@Autowired
	private HttpSession session;
	@Autowired
	private DesignProcessService designProcessService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission()
	public Result<NullData> saveOrUpdate(String stuId, String stageName, String sketch, String amendOpinion,
			String guidance) {
		Student student = (Student) session.getAttribute("student");
		stuId = student.getStuId();
		DesignProcess designProcess = designProcessService.select(stuId, stageName).getData();
		if (designProcess == null) {
			designProcess = new DesignProcess();
			designProcess.setStuId(stuId);
			designProcess.setStageName(stageName);
		}
		designProcess.setSketch(sketch);
		designProcess.setAmendOpinion("нч");
		designProcess.setGuidance(guidance);
		return designProcessService.saveOrUpdate(designProcess);
	}

	@RequestMapping(value = "t/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission()
	public Result<NullData> saveOrUpdate(String stuId, String stageName, String amendOpinion, String guidance,
			int score, boolean isLock) {
		return designProcessService.saveOrUpdate(stuId, stageName, "нч", guidance, score, isLock);
	}

	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission()
	public Result<DesignProcess> select(String stuId, String stageName) {
		return designProcessService.select(stuId, stageName);
	}
}
