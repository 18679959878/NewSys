package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.WeekRecord;

public interface WeekRecordService {
	Result<WeekRecord> select(String stuId,String stageName);

	Result<NullData> saveOrUpdate(WeekRecord weekRecord);
}
