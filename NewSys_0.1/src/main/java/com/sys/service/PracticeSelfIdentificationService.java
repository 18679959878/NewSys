package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSelfIdentification;

public interface PracticeSelfIdentificationService {
	public Result<NullData> saveOrUpdate(PracticeSelfIdentification practiceSelfIdentification);
	public Result<PracticeSelfIdentification> select(String stuId);
}
