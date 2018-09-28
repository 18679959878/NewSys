package com.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.WeekRecord;

public interface WeekRecordMapper {
	public void saveOrUpdate(WeekRecord weekRecord);

	public WeekRecord select(@Param("stuId") String stuId, @Param("stageName") String stageName);
}
