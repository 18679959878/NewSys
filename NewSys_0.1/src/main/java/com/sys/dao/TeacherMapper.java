package com.sys.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.Student;
import com.sys.entity.Teacher;

public interface TeacherMapper {
	/**
	 * 存储教师对象
	 * 
	 * @author 金小瑶
	 * @param teacher
	 * @return
	 */
	public Integer save(Teacher teacher);

	/**
	 * 查询start到 end位置内的教师数据
	 * 
	 * @author 金小瑶
	 * @param start
	 * @param end
	 * @return
	 */
	public ArrayList<Teacher> selectPage(@Param("start") int start, @Param("end") int end);

	/**
	 * 查询教师表中的总行数
	 * 
	 * @author 金小瑶
	 * @return 总行数
	 */
	public Integer selectCount();

	/**
	 * 删除对应的教师信息
	 * 
	 * @param teaAccount
	 * @return
	 */
	public Integer delete(String teaAccount);

	/**
	 * 更改教师信息
	 * 
	 * @param teacher
	 * @return
	 */
	public Integer update(Teacher teacher);

	/**
	 * 批量插入或修改
	 * 
	 * @author 金小瑶
	 * @param teachers
	 * @return
	 */
	public Integer insertOrupdateBatch(ArrayList<Teacher> teachers);

	public ArrayList<Teacher> selectAll();

	public Teacher select(String teaAccount);

}
