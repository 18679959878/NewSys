
package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sys.dto.Result;
import com.sys.entity.ClassGrade;

public interface ClassGradeService {
	/**
	 * 将班级对象存储到数据库
	 * 
	 * @author 金小瑶
	 * @param classGrade
	 *            添加到数据库的班级对象
	 * @return 返回添加结果信息
	 */
	public Result<Map<Object, Object>> addClass(ClassGrade classGrade);

	/**
	 * 查询当前页的班级列表
	 * 
	 * @author 金小瑶
	 * @param queryPage
	 *            查询页码
	 * @param pageSize
	 *            页大小
	 * @return 当前页班级，以及最大页码等页面信息
	 */
	public Result<HashMap<String, Object>> pageDClass(int queryPage, final int pageSize);

	/**
	 * 根据班级名删除数据库关于该班级的记录,删除后将会删除该班级所有的学生信息，谨慎使用
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @return 返回给控制器的数据
	 */
	public Result<ClassGrade> deleteClass(String className);

	/**
	 * 根据控制器传来的班级信息对数据库班级信息进行修改
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @param grade
	 *            年级
	 * @param education
	 *            本/专科
	 * @return 返回给控制器的数据
	 */
	public Result<ClassGrade> updateClass(String className, String grade, String education, String major);

	public Result<HashMap<String, Object>> selectClassGradeOfDateStage(int classType, int stageType, int queryPage,
			int pageSize);
}
