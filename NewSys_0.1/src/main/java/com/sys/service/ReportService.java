package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Report;

public interface ReportService {
	public Result<NullData> saveOrUpdate(Report report);

	Result<Report> select(String stuId, String stageName);

	Result<NullData> saveOrUpdate(String stuId, String stageName, String reportText, String guidance, int score,
			boolean isLock);
}
