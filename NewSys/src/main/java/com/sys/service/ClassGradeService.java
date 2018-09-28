
package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sys.dto.Result;
import com.sys.entity.ClassGrade;

public interface ClassGradeService {
	/**
	 * ���༶����洢�����ݿ�
	 * 
	 * @author ��С��
	 * @param classGrade
	 *            ��ӵ����ݿ�İ༶����
	 * @return ������ӽ����Ϣ
	 */
	public Result<Map<Object, Object>> addClass(ClassGrade classGrade);

	/**
	 * ��ѯ��ǰҳ�İ༶�б�
	 * 
	 * @author ��С��
	 * @param queryPage
	 *            ��ѯҳ��
	 * @param pageSize
	 *            ҳ��С
	 * @return ��ǰҳ�༶���Լ����ҳ���ҳ����Ϣ
	 */
	public Result<HashMap<String, Object>> pageDClass(int queryPage, final int pageSize);

	/**
	 * ���ݰ༶��ɾ�����ݿ���ڸð༶�ļ�¼,ɾ���󽫻�ɾ���ð༶���е�ѧ����Ϣ������ʹ��
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @return ���ظ�������������
	 */
	public Result<ClassGrade> deleteClass(String className);

	/**
	 * ���ݿ����������İ༶��Ϣ�����ݿ�༶��Ϣ�����޸�
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @param grade
	 *            �꼶
	 * @param education
	 *            ��/ר��
	 * @return ���ظ�������������
	 */
	public Result<ClassGrade> updateClass(String className, String grade, String education, String major);

	public Result<HashMap<String, Object>> selectClassGradeOfDateStage(int classType, int stageType, int queryPage,
			int pageSize);
}
