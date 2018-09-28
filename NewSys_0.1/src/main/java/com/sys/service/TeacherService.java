package com.sys.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Teacher;

public interface TeacherService {
	/**
	 * 保存教师数据
	 * 
	 * @param teacher
	 * @return
	 */
	public Result<Teacher> save(Teacher teacher);

	/**
	 * 查询当前页的教师列表
	 * 
	 * @author 金小瑶
	 * @param queryPage
	 *            查询页码
	 * @param pageSize
	 *            页大小
	 * @return 当前页教师，以及最大页码等页面信息
	 */
	public Result<HashMap<String, Object>> pageTeacher(int queryPage, final int pageSize);

	/**
	 * 删除教师
	 * 
	 * @param teaAccount
	 * @return
	 */
	public Result<NullData> deleteTeacher(String teaAccount);

	/**
	 * 更改教师信息
	 * 
	 * @param teacher
	 * @return
	 */
	public Result<NullData> update(Teacher teacher);

	public Result<ArrayList<Teacher>> allTeacher();

	public Result<NullData> importTeacherExcel(File file) throws FileNotFoundException, IOException;

	public Result<NullData> login(String teaAccount, String password);

	public Result<NullData> addFiledStudents(String stuId);

	public Result<Map<String, Object>> selectFiledStuPage(int queryPage, final int pageSize);

	public Result<NullData> deleteFieldStu(String stuId);

	public Result<Map<String, Object>> selectFiledStuPage(int queryPage, final int pageSize, String stageName);

	public Result<Map<String, Object>> selectWeekRecordStuPage( String stageName);
}
