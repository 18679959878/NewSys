package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.FinalProjectTask;

public interface FinalProjectTaskService {
	Result<FinalProjectTask> select(String stuId);

	Result<NullData> saveOrUpdate(FinalProjectTask finalProjectTask);
}
