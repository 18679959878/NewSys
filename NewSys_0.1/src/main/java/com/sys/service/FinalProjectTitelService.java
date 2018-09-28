package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.FinalProjectTitel;

public interface FinalProjectTitelService {
	public Result<NullData> saveOrUpdate(FinalProjectTitel finalProjectTitel);
	
	Result<FinalProjectTitel> select(String stuId);
}
