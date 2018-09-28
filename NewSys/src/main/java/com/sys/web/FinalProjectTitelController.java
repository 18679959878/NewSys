package com.sys.web;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.annotation.FPValidatePermission;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.FinalProjectTitel;
import com.sys.entity.Student;
import com.sys.service.FinalProjectTitelService;

@Controller
@RequestMapping("/fpt")
public class FinalProjectTitelController {
	@Autowired
	private FinalProjectTitelService finalProjectTitelService;
	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission(stageName = FPValidatePermission.TITEL_STAGE)
	public Result<NullData> saveOrUpdate(String stuId, String titel_cn, String titel_en, String academy,
			String teaNameAndTitel) {
		FinalProjectTitel finalProjectTitel = new FinalProjectTitel();
		finalProjectTitel.setTitelCn(titel_cn);
		finalProjectTitel.setTitelEn(titel_en);
		finalProjectTitel.setAcademy(academy);
		finalProjectTitel.setTeaNameAndTitel(teaNameAndTitel);
		Student student = (Student) session.getAttribute("student");
		finalProjectTitel.setStuId(student.getStuId());
		return finalProjectTitelService.saveOrUpdate(finalProjectTitel);
	}

	@RequestMapping(value = "/t/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission(stageName = FPValidatePermission.TITEL_STAGE)
	public Result<NullData> saveOrUpdate(String stuId, boolean isSumbit) {
		FinalProjectTitel finalProjectTitel = finalProjectTitelService.select(stuId).getData();
		finalProjectTitel.setIsSumbit(isSumbit);
		return finalProjectTitelService.saveOrUpdate(finalProjectTitel);
	}

	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission(stageName = FPValidatePermission.TITEL_STAGE)
	public Result<FinalProjectTitel> select(String stuId) {
		return finalProjectTitelService.select(stuId);
	}
}
