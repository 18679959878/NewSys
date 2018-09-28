package com.sys.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.ClassGrade;

public interface ClassGradeMapper {
	/**
	 * 保存一个班级对象到对象
	 * 
	 * @author 金小瑶
	 * @param classGrade
	 *            保存的班级对象
	 * @return 受影响行数
	 */
	public int save(ClassGrade classGrade);

	/**
	 * 通过班级名查询班级
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @return 查询的班级对象
	 */
	public ClassGrade select(String className);

	/**
	 * 根据班级名和班级名类型查询班级信息
	 * 
	 * @author 金小瑶
	 * @param className
	 * @param classType
	 * @return
	 */
	public ClassGrade selectOfClassType(@Param("className") String className, @Param("classType") int classType);

	/**
	 * 根据开始位置和结束位置查询班级List
	 * 
	 * @author 金小瑶
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return 班级对象列表
	 */
	public List<ClassGrade> selectPage(@Param("start") int start, @Param("end") int end,
			@Param("classType") int classType);

	/**
	 * 查询班级表中的总行数
	 * 
	 * @author 金小瑶
	 * @return 总行数
	 */
	public Integer selectCount(int classType);

	/**
	 * 根根据班级名删除数据库该班级的记录
	 * 
	 * @author 金小瑶
	 * @param className
	 *            班级名
	 * @return 受影响行数
	 */
	public Integer deleteClass(String className);

	/**
	 * 修改班级信息
	 * 
	 * @author 金小瑶
	 * @param classGrade
	 *            班级对象
	 * @return 受影响行数
	 */
	public Integer updateClass(ClassGrade classGrade);

	// 班级学生减一
	public void reduceStuNum(String className);

	// 班级学生加一
	public void addStuNum(String className);

	// 查询所有班级名
	public ArrayList<String> selectAllClassName(@Param("classType") int classType);

	/**
	 * 重置班级人数
	 * 
	 * @author 金小瑶
	 * @param list
	 *            班级名的list
	 */
	public void resetStuNum(ArrayList<String> list);

	/**
	 * 获得已经设置日期（实习日期和毕业设计日期）的所有班级
	 * 
	 * @param classType
	 *            班级类型
	 *            0代表原班级（ClassGrade.OLD_CLASS），1代表分方向班级(ClassGrade.FIELD_CLASS)
	 * @param stageType
	 *            日期设置类型
	 *            0代表实习日期设置(DateStage.FIELD_WORK_TYPE)，1代表毕业设计日期设置(ClassGrade.WORKS_TYPE)
	 * @return 查询到的班级信息
	 */
	public ArrayList<ClassGrade> selectClassGradePageOfDateStage(@Param("classType") int classType,
			@Param("stageType") int stageType, @Param("start") int start, @Param("end") int end);

	public Integer selectCountOfDateStage(@Param("classType") int classType, @Param("stageType") int stageType);
}
