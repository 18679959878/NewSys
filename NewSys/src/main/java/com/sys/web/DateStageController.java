package com.sys.web;

import java.text.ParseException;
import java.util.HashMap;

import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DateStage;
import com.sys.entity.Student;
import com.sys.service.DateStageService;
import com.sys.utils.DateTimeUtils;

@Controller
@RequestMapping("/ds")
public class DateStageController {
	@Autowired
	private DateStageService dateStageService;
	@Autowired
	private HttpSession session;
	// 页面大小
	private final int pageSize = 10;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> addDateStage(String stageName, String grade, String education, String startDate,
			String endDate, int type) {
		System.out.println("是否进入");
		if (stageName != null && !stageName.equals("") && grade != null && !grade.equals("") && education != null
				&& !education.equals("") && startDate != null && !startDate.equals("") && endDate != null
				&& !endDate.equals("")) {
			try {
				return dateStageService.saveDateStage(new DateStage(stageName, grade, education,
						DateTimeUtils.strToDate(startDate), DateTimeUtils.strToDate(endDate), type));
			} catch (ParseException e) {
				return new Result<NullData>("添加失败！！");
			}
		} else {
			return new Result<NullData>("添加失败!!输入框内容不能为空");
		}
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> PaperStagePapeData(int queryPage, int type) {
		return dateStageService.dateStagePageData(queryPage, pageSize, type);

	}

	@RequestMapping(value = "/sel/stu", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> selectStageOfStu(String stuId, String type) {

		if (stuId == null || stuId.equals("")) {
			Student student = ((Student) session.getAttribute("student"));
			if (student != null) {
				stuId = student.getStuId();
			}
		}
		if (type == null || type.equals("")) {
			return dateStageService.selectDesStageOfStu(stuId);
		} else {
			return dateStageService.selectReportStageOfStu(stuId);
		}

	}

	@RequestMapping(value = "/sel/week/stu", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> selectStageOfStu(String stuId,int queryPage, int stageType) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		return dateStageService.dateStagePageData(stuId, queryPage, pageSize, stageType);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> deletePaperStage(String stageName, String grade, String education, int type) {
		return dateStageService.deleteDateStage(stageName, grade, education, type);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> updatePaperStage(String stageName, String grade, String education, String startDate,
			String endDate, int type) {
		if (stageName != null && !stageName.equals("") && grade != null && !grade.equals("") && education != null
				&& !education.equals("") && startDate != null && !startDate.equals("") && endDate != null
				&& !endDate.equals("")) {
			try {
				System.out.println(stageName + "|" + grade + "|" + education + "|" + startDate);
				return dateStageService.updateDateStage(new DateStage(stageName, grade, education,
						DateTimeUtils.strToDate(startDate), DateTimeUtils.strToDate(endDate), type));
			} catch (ParseException e) {
				return new Result<NullData>("时间格式出错");
			}
		} else {
			return new Result<NullData>("各项不能为空");
		}
	}
}
