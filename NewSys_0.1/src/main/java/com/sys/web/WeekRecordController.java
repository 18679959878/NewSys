package com.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.annotation.FPValidatePermission;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSummary;
import com.sys.entity.Student;
import com.sys.entity.WeekRecord;
import com.sys.service.PracticeSummaryService;
import com.sys.service.StudentService;
import com.sys.service.WeekRecordService;

@Controller
@RequestMapping("weekRecord")
public class WeekRecordController {
	@Autowired
	private HttpSession session;
	@Autowired
	private WeekRecordService weekRecordService;
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission
	public Result<NullData> saveOrUpdate(String stuId, String stageName, String department, String place,
			String activity) {
		WeekRecord weekRecord;
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		weekRecord = weekRecordService.select(stuId, stageName).getData();
		if (weekRecord == null) {
			weekRecord = new WeekRecord();
			weekRecord.setStageName(stageName);
			weekRecord.setStuId(stuId);
			weekRecord.setIsLock(false);
			weekRecord.setScore(0);
			weekRecord.setGuidance("");
		}
		weekRecord.setDepartment(department);
		weekRecord.setPlace(place);
		weekRecord.setActivity(activity);
		return weekRecordService.saveOrUpdate(weekRecord);
	}

	@RequestMapping(value = "/t/sop", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission
	public Result<NullData> saveOrUpdate(String stuId, String stageName, String guidance, int score, boolean isLock) {
		WeekRecord weekRecord = weekRecordService.select(stuId, stageName).getData();
		if (weekRecord == null) {
			return new Result<NullData>("保存失败！！学生未提交");
		}
		weekRecord.setScore(score);
		weekRecord.setGuidance(guidance);
		weekRecord.setIsLock(isLock);
		return weekRecordService.saveOrUpdate(weekRecord);

	}

	@RequestMapping(value = "/sel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission
	public Result<WeekRecord> saveOrUpdate(String stuId, String stageName) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		return weekRecordService.select(stuId, stageName);

	}

	@RequestMapping(value = "/nextSub", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	@FPValidatePermission
	public Result<Student> nextSub(String stuId, String stageName) {
		ArrayList<String> stuIdCaches = (ArrayList<String>) session.getAttribute("stuIdCaches");
		for (String string : stuIdCaches) {
			System.out.println(string);
		}
		if (stuIdCaches != null) {
			int next = stuIdCaches.indexOf(stuId) + 1;
			System.out.println(next);
			if (next < stuIdCaches.size()) {
				Student student = studentService.select(stuIdCaches.get(next)).getData();
				return new Result<Student>(student);
			}
		}
		return new Result<Student>("返回错误");
	}
}
