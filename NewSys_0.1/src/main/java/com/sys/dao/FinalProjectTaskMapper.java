package com.sys.dao;

import com.sys.entity.FinalProjectTask;

public interface FinalProjectTaskMapper {
	FinalProjectTask select(String stuId);

	void saveOrUpdate(FinalProjectTask finalProjectTask);
}
