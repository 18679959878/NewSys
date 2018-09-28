package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSituation;

public interface PracticeSituationService {
	public Result<NullData> saveOrUpdate(PracticeSituation practiceSituation);

	public Result<PracticeSituation> select(String stuId);
}
