package com.sys.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.Student;
import com.sys.entity.Teacher;

public interface TeacherMapper {
	/**
	 * �洢��ʦ����
	 * 
	 * @author ��С��
	 * @param teacher
	 * @return
	 */
	public Integer save(Teacher teacher);

	/**
	 * ��ѯstart�� endλ���ڵĽ�ʦ����
	 * 
	 * @author ��С��
	 * @param start
	 * @param end
	 * @return
	 */
	public ArrayList<Teacher> selectPage(@Param("start") int start, @Param("end") int end);

	/**
	 * ��ѯ��ʦ���е�������
	 * 
	 * @author ��С��
	 * @return ������
	 */
	public Integer selectCount();

	/**
	 * ɾ����Ӧ�Ľ�ʦ��Ϣ
	 * 
	 * @param teaAccount
	 * @return
	 */
	public Integer delete(String teaAccount);

	/**
	 * ���Ľ�ʦ��Ϣ
	 * 
	 * @param teacher
	 * @return
	 */
	public Integer update(Teacher teacher);

	/**
	 * ����������޸�
	 * 
	 * @author ��С��
	 * @param teachers
	 * @return
	 */
	public Integer insertOrupdateBatch(ArrayList<Teacher> teachers);

	public ArrayList<Teacher> selectAll();

	public Teacher select(String teaAccount);

}
