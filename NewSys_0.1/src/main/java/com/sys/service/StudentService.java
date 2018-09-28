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
	 * ����student�����ݿ�
	 * 
	 * @author ��С��
	 * @param student
	 *            ��Ҫ�����ѧ������
	 * @return ���ظ��������Ķ���
	 */
	public Result<Student> addStudentOne(Student student);

	/**
	 * ���ݰ༶����ѯ�ð༶���е�ѧ��
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @return �ð༶����ѧ��
	 */
	public Result<Map<String, List<Student>>> selectClassStudent(String className);

	/**
	 * �޸İ༶����
	 * 
	 * @author ��С��
	 * @param student
	 *            ��Ҫ�޸ĵ�ѧ������
	 * @return ���ظ������������ݶ���
	 */
	public Result<NullData> update(Student student);

	/**
	 * ɾ��ѧ������
	 * 
	 * @author ��С��
	 * @param stuId
	 *            ѧ��
	 * @return ���ظ�������������
	 */
	public Result<NullData> delete(String stuId);

	/**
	 * �����ϴ���ѧ��excel��
	 * 
	 * @param file
	 *            excel�ļ�����
	 * @return ���ظ�������������
	 */
	public Result<NullData> importStudentExcel(File file) throws FileNotFoundException, IOException;

	/**
	 * ѧ����¼
	 * 
	 * @param stuId
	 * @param password
	 * @return
	 */
	public Result<NullData> studentLogin(String stuId, String password);
	
	/**
	 * ��ѯѧ����Ϣ
	 */
	public Result<Student> select(String stuId);
}
