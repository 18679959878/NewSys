package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSummary;

public interface PracticeSummaryService {
	public Result<NullData> saveOrUpdate(PracticeSummary practiceSummary);

	public Result<PracticeSummary> select(String stuId);
}
