package com.sys.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Student;

public interface StudentService {
	/**
	 * 保存student到数据库
	 * 
	 * @author 金小瑶
	 * @param student
	 *            将要保存的学生对象
	 * @return 返回给控制器的对象
	 */
	public Result<Student> addStudentOne(Student student);

	/**
	 * 根据班级名查询该班级所有的学生
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @return 该班级所有学生
	 */
	public Result<Map<String, List<Student>>> selectClassStudent(String className);

	/**
	 * 修改班级对象
	 * 
	 * @author 金小瑶
	 * @param student
	 *            需要修改的学生对象
	 * @return 返回给控制器的数据对象
	 */
	public Result<NullData> update(Student student);

	/**
	 * 删除学生数据
	 * 
	 * @author 金小瑶
	 * @param stuId
	 *            学号
	 * @return 返回给控制器的数据
	 */
	public Result<NullData> delete(String stuId);

	/**
	 * 导出上传的学生excel表
	 * 
	 * @param file
	 *            excel文件对象
	 * @return 返回给控制器的数据
	 */
	public Result<NullData> importStudentExcel(File file) throws FileNotFoundException, IOException;

	/**
	 * 学生登录
	 * 
	 * @param stuId
	 * @param password
	 * @return
	 */
	public Result<NullData> studentLogin(String stuId, String password);
	
	/**
	 * 查询学生信息
	 */
	public Result<Student> select(String stuId);
}
