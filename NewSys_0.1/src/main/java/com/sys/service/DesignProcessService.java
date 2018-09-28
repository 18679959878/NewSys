package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DesignProcess;
import com.sys.entity.FinalProjectTitel;

public interface DesignProcessService {
	public Result<NullData> saveOrUpdate(DesignProcess designProcess);

	Result<DesignProcess> select(String stuId, String stageName);

	Result<NullData> saveOrUpdate(String stuId, String stageName, String amendOpinion, String guidancesasq, int score,
			boolean isLock);
}
