package com.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.DateStage;

public interface DateStageMapper {
	public void save(DateStage dateStage);

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
	public List<DateStage> selectPage(@Param("start") int start, @Param("end") int end, @Param("type") int type);

	public Integer selectCount(@Param("type") int type);

	public Integer delete(@Param("stageName") String stageName, @Param("grade") String grade,
			@Param("education") String education, @Param("type") int type);

	public Integer update(DateStage dateStage);

	public List<DateStage> selectStageOfStu(@Param("stuId") String stuId, @Param("stageNameLike") String stageNameLike);

	public DateStage select(@Param("stuId") String stuId, @Param("stageName") String stageName);
}
