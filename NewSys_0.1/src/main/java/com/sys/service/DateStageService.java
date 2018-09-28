package com.sys.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DateStage;

public interface DateStageService {
	public Result<NullData> saveDateStage(DateStage paperStage);

	public Result<HashMap<String, Object>> dateStagePageData(int queryPage, final int pageSize, int type);

	public Result<NullData> deleteDateStage(String stageName, String grade, String education, int type);

	public Result<NullData> updateDateStage(DateStage dateStage);

	public Result<HashMap<String, Object>> selectDesStageOfStu(String stuId);

	public Result<HashMap<String, Object>> selectReportStageOfStu(String stuId);

	public Result<DateStage> select(String stuId, String stageName);

	public Result<HashMap<String, Object>> dateStagePageData(String stuId,int queryPage, int pageSize, int type);
}
