package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.FinalProjectTask;
import com.sys.entity.OpenReport;

public interface OpenReportService {
	Result<OpenReport> select(String stuId);

	Result<NullData> saveOrUpdate(OpenReport openReport);

	public Result<NullData> saveOrUpdate(String stuId, String agree, int score, boolean isLock);
}
