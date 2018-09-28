package com.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.Result;
import com.sys.entity.ClassGrade;
import com.sys.service.ClassGradeService;

@Controller
@RequestMapping("/class")
public class ClassController {
	// public
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClassGradeService classGradeService;
	// 页面大小
	private final int pageSize = 10;

	// 最大页码
	private int maxPage;

	/**
	 * 存储前端传来的班级信息
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @param grade
	 *            年级
	 * @param education
	 *            本/专科
	 * @return 返回给前端的json数据
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<Object, Object>> addClass(String className, String grade, String education, String major) {

		// 调用service返回数据
		return classGradeService.addClass(new ClassGrade(className, grade, education, major));

	}

	/**
	 * 查询当前页的信息
	 * 
	 * @param queryPage
	 *            查询的页码
	 * @return 返回的页面信息
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> classPageData(int queryPage) {
		return classGradeService.pageDClass(queryPage, pageSize);
	}

	/**
	 * 删除该班级在数据库所对应的班级，以及该班级所有的学生相关信息，包括成绩
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @return 返回给前端的json数据
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<ClassGrade> deleteClass(String className) {
		return classGradeService.deleteClass(className);
	}

	/**
	 * 修改前端传来的班级信息
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @param grade
	 *            年级
	 * @param education
	 *            本/专科
	 * @return 给前端的json数据
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<ClassGrade> updateClass(String className, String grade, String education, String major) {
		return classGradeService.updateClass(className, grade, education, major);
	}

	@RequestMapping(value = "/cd/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> selectClassGradeOfDateStage(int classType, int stageType,int queryPage) {
		return classGradeService.selectClassGradeOfDateStage(classType, stageType, queryPage, pageSize);
	}
}
