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
	 * �����ʦ����
	 * 
	 * @param teacher
	 * @return
	 */
	public Result<Teacher> save(Teacher teacher);

	/**
	 * ��ѯ��ǰҳ�Ľ�ʦ�б�
	 * 
	 * @author ��С��
	 * @param queryPage
	 *            ��ѯҳ��
	 * @param pageSize
	 *            ҳ��С
	 * @return ��ǰҳ��ʦ���Լ����ҳ���ҳ����Ϣ
	 */
	public Result<HashMap<String, Object>> pageTeacher(int queryPage, final int pageSize);

	/**
	 * ɾ����ʦ
	 * 
	 * @param teaAccount
	 * @return
	 */
	public Result<NullData> deleteTeacher(String teaAccount);

	/**
	 * ���Ľ�ʦ��Ϣ
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
