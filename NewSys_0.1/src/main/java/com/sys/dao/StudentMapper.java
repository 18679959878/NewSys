package com.sys.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.Student;

public interface StudentMapper {
	/**
	 * ����ѧ������
	 * 
	 * @author ��С��
	 * @param student
	 *            �����ѧ������
	 * @return ��Ӱ������
	 */
	public Integer save(Student student);

	/**
	 * ����id��ѯѧ����¼
	 * 
	 * @author ��С��
	 * @param stuId
	 *            ѧ��
	 * @return ѧ������
	 */
	public Student select(String stuId);

	/**
	 * ���ݰ༶����ѯ�༶ѧ��
	 * 
	 * @author ��С��
	 * @param className
	 *            �����������İ༶��
	 * @return �ð༶������ѧ��
	 */
	public ArrayList<Student> selectClassStudents(String className);

	/**
	 * �޸� ���ݿ��ѧ������
	 * 
	 * @author ��С��
	 * @param student
	 *            �޸ĵ�ѧ������
	 * @return ��Ӱ������
	 */
	public Integer update(Student student);

	/**
	 * ɾ�����ݿ�ѧ������
	 * 
	 * @author ��С��
	 * @param stuId
	 *            ѧ������
	 * @return ��Ӱ������
	 */
	public Integer delete(String stuId);

	public Integer insertOrupdateBatch(ArrayList<Student> list);

	public Integer selectFiledStuCount(String fieldworkTea);

	public ArrayList<Student> selectFiledStuPage(@Param("fieldworkTea") String fieldworkTea, @Param("start") int start,
			@Param("end") int end);

	public ArrayList<Student> selectFieldworkStudents(String fieldworkTea);

	public ArrayList<Student> selectworkTeaStudents(String workTea);
}
