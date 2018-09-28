package com.sys.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.ClassGrade;

public interface ClassGradeMapper {
	/**
	 * ����һ���༶���󵽶���
	 * 
	 * @author ��С��
	 * @param classGrade
	 *            ����İ༶����
	 * @return ��Ӱ������
	 */
	public int save(ClassGrade classGrade);

	/**
	 * ͨ���༶����ѯ�༶
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @return ��ѯ�İ༶����
	 */
	public ClassGrade select(String className);

	/**
	 * ���ݰ༶���Ͱ༶�����Ͳ�ѯ�༶��Ϣ
	 * 
	 * @author ��С��
	 * @param className
	 * @param classType
	 * @return
	 */
	public ClassGrade selectOfClassType(@Param("className") String className, @Param("classType") int classType);

	/**
	 * ���ݿ�ʼλ�úͽ���λ�ò�ѯ�༶List
	 * 
	 * @author ��С��
	 * @param start
	 *            ��ʼλ��
	 * @param end
	 *            ����λ��
	 * @return �༶�����б�
	 */
	public List<ClassGrade> selectPage(@Param("start") int start, @Param("end") int end,
			@Param("classType") int classType);

	/**
	 * ��ѯ�༶���е�������
	 * 
	 * @author ��С��
	 * @return ������
	 */
	public Integer selectCount(int classType);

	/**
	 * �����ݰ༶��ɾ�����ݿ�ð༶�ļ�¼
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @return ��Ӱ������
	 */
	public Integer deleteClass(String className);

	/**
	 * �޸İ༶��Ϣ
	 * 
	 * @author ��С��
	 * @param classGrade
	 *            �༶����
	 * @return ��Ӱ������
	 */
	public Integer updateClass(ClassGrade classGrade);

	// �༶ѧ����һ
	public void reduceStuNum(String className);

	// �༶ѧ����һ
	public void addStuNum(String className);

	// ��ѯ���а༶��
	public ArrayList<String> selectAllClassName(@Param("classType") int classType);

	/**
	 * ���ð༶����
	 * 
	 * @author ��С��
	 * @param list
	 *            �༶����list
	 */
	public void resetStuNum(ArrayList<String> list);

	/**
	 * ����Ѿ��������ڣ�ʵϰ���ںͱ�ҵ������ڣ������а༶
	 * 
	 * @param classType
	 *            �༶����
	 *            0����ԭ�༶��ClassGrade.OLD_CLASS����1����ַ���༶(ClassGrade.FIELD_CLASS)
	 * @param stageType
	 *            ������������
	 *            0����ʵϰ��������(DateStage.FIELD_WORK_TYPE)��1�����ҵ�����������(ClassGrade.WORKS_TYPE)
	 * @return ��ѯ���İ༶��Ϣ
	 */
	public ArrayList<ClassGrade> selectClassGradePageOfDateStage(@Param("classType") int classType,
			@Param("stageType") int stageType, @Param("start") int start, @Param("end") int end);

	public Integer selectCountOfDateStage(@Param("classType") int classType, @Param("stageType") int stageType);
}
