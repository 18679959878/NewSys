package com.sys.dao;

import com.sys.entity.FinalProjectTitel;

public interface FinalProjectTitelMapper {
	public FinalProjectTitel select(String stuId);

	public void saveOrUpdate(FinalProjectTitel finalProjectTitel);
}
