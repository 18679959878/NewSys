package com.sys.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.Student;

public interface StudentMapper {
	/**
	 * 保存学生对象
	 * 
	 * @author 金小瑶
	 * @param student
	 *            保存的学生对象
	 * @return 受影响行数
	 */
	public Integer save(Student student);

	/**
	 * 根据id查询学生记录
	 * 
	 * @author 金小瑶
	 * @param stuId
	 *            学号
	 * @return 学生对象
	 */
	public Student select(String stuId);

	/**
	 * 根据班级名查询班级学生
	 * 
	 * @author 金小瑶
	 * @param className
	 *            控制器传来的班级名
	 * @return 该班级的所有学生
	 */
	public ArrayList<Student> selectClassStudents(String className);

	/**
	 * 修改 数据库得学生数据
	 * 
	 * @author 金小瑶
	 * @param student
	 *            修改的学生对象
	 * @return 受影响行数
	 */
	public Integer update(Student student);

	/**
	 * 删除数据库学生数据
	 * 
	 * @author 金小瑶
	 * @param stuId
	 *            学生姓名
	 * @return 受影响行数
	 */
	public Integer delete(String stuId);

	public Integer insertOrupdateBatch(ArrayList<Student> list);

	public Integer selectFiledStuCount(String fieldworkTea);

	public ArrayList<Student> selectFiledStuPage(@Param("fieldworkTea") String fieldworkTea, @Param("start") int start,
			@Param("end") int end);

	public ArrayList<Student> selectFieldworkStudents(String fieldworkTea);

	public ArrayList<Student> selectworkTeaStudents(String workTea);
}
