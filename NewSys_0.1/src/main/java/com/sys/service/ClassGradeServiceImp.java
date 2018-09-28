package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.ClassGradeMapper;
import com.sys.dto.Result;
import com.sys.entity.ClassGrade;

@Service
public class ClassGradeServiceImp implements ClassGradeService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClassGradeMapper classGradeMapper;
	@Autowired(required = false)
	// @Autowired
	private HttpSession session;

	@Override
	@Transactional
	public Result<Map<Object, Object>> addClass(ClassGrade classGrade) {
		ClassGrade sel_classGrade = classGradeMapper.select(classGrade.getClassName());
		if (sel_classGrade == null) {
			if (classGrade.getEducation().equals("") || classGrade.getGrade().equals("")
					|| classGrade.getGrade() == null || classGrade.getEducation() == null) {
				return new Result<Map<Object, Object>>("添加失败!!未选择本专科或没有选择年级");
			} else {
				classGrade.setFiledWorkTea("无");
				classGradeMapper.save(classGrade);
				return new Result<Map<Object, Object>>(new HashedMap());
			}
		} else {
			return new Result<Map<Object, Object>>("添加失败!!数据库已存在该班级名");
		}
	}

	@Override
	@Transactional
	public Result<HashMap<String, Object>> pageDClass(int queryPage, final int pageSize) {
		// 返回的正文数据
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最大页码,进一法
		int maxPage = (int) Math.ceil((double) classGradeMapper.selectCount(ClassGrade.OLD_CLASS) / pageSize);
		logger.debug(classGradeMapper.selectCount(ClassGrade.OLD_CLASS) + "");
		// 将最大页码放入map
		map.put("maxPage", maxPage);
		// 从数据库查询此页班级列表
		ArrayList<ClassGrade> pageList = (ArrayList<ClassGrade>) classGradeMapper.selectPage((queryPage - 1) * pageSize,
				queryPage * pageSize, ClassGrade.OLD_CLASS);
		// 将本页班级列表放入map
		map.put("pageList", pageList);
		// 返回的结果
		return new Result<HashMap<String, Object>>(map);
	}

	@Override
	public Result<ClassGrade> deleteClass(String className) {
		// admin用户才有操作权限
		if (session.getAttribute("admin") != null) {
			// 判断班级名是否为空,不为空执行删除，为空则返回给控制器错误消息
			if (className != null) {
				// 执行删除
				int row = classGradeMapper.deleteClass(className);
				// 如果受影响行数为0，则返回信息添加失败
				if (row == 0) {
					return new Result<ClassGrade>("删除失败!无法识别班级名");
				} else {
					// 返回删除成功的信息
					return new Result<ClassGrade>(new ClassGrade());
				}
			} else {
				return new Result<ClassGrade>("删除失败!!服务器接收数据为空");
			}
		} else {
			return new Result<ClassGrade>("无操作权限");
		}
	}

	@Override
	@Transactional
	public Result<ClassGrade> updateClass(String className, String grade, String education, String major) {
		// 判断前端是否传来空值
		if (grade == null || grade.equals("") || education == null || education.equals("")) {
			return new Result<ClassGrade>("修改失败！！年级或者本/专科为空");
		} else {
			// 修改数据库数据
			classGradeMapper.updateClass(new ClassGrade(className, grade, education, major));
			// 返回成功
			return new Result<ClassGrade>(new ClassGrade());
		}
	}

	@Override
	public Result<HashMap<String, Object>> selectClassGradeOfDateStage(int classType, int stageType, int queryPage,
			int pageSize) {
		// 返回的正文数据
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最大页码,进一法
		int maxPage = (int) Math
				.ceil((double) classGradeMapper.selectCountOfDateStage(classType, stageType) / pageSize);
		logger.debug(classGradeMapper.selectCount(ClassGrade.OLD_CLASS) + "");
		// 将最大页码放入map
		map.put("maxPage", maxPage);
		// 从数据库查询此页班级列表
		ArrayList<ClassGrade> pageList = (ArrayList<ClassGrade>) classGradeMapper.selectClassGradePageOfDateStage(
				classType, stageType, (queryPage - 1) * pageSize, queryPage * pageSize);
		// 将本页班级列表放入map
		map.put("pageList", pageList);
		// 返回的结果
		return new Result<HashMap<String, Object>>(map);
	}

}
