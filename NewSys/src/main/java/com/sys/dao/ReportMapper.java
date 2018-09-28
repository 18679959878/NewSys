package com.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.sys.entity.Report;

public interface ReportMapper {
	Report select(@Param("stuId") String stuId, @Param("stageName") String stageName);

	void saveOrUpdate(Report report);
}
