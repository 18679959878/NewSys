package com.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.DateStage;

public interface DateStageMapper {
	public void save(DateStage dateStage);

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
	public List<DateStage> selectPage(@Param("start") int start, @Param("end") int end, @Param("type") int type);

	public Integer selectCount(@Param("type") int type);

	public Integer delete(@Param("stageName") String stageName, @Param("grade") String grade,
			@Param("education") String education, @Param("type") int type);

	public Integer update(DateStage dateStage);

	public List<DateStage> selectStageOfStu(@Param("stuId") String stuId, @Param("stageNameLike") String stageNameLike);

	public DateStage select(@Param("stuId") String stuId, @Param("stageName") String stageName);
}
