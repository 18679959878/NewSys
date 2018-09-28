package com.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DesignProcess;

public interface DesignProcessMapper {
	DesignProcess select(@Param("stuId") String stuId, @Param("stageName") String stageName);

	void saveOrUpdate(DesignProcess designProcess);


}
