package com.sys.dao;


import com.sys.entity.OpenReport;

public interface OpenReportMapper {
	OpenReport select(String stuId);
	public void saveOrUpdate(OpenReport openReport);
}
