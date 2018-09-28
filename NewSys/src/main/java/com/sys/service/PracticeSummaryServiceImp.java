package com.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.PracticeSummaryMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSummary;
@Service
public class PracticeSummaryServiceImp implements PracticeSummaryService {
	@Autowired
	private PracticeSummaryMapper practiceSummaryMapper;

	@Override
	public Result<NullData> saveOrUpdate(PracticeSummary practiceSummary) {
		try {
			practiceSummaryMapper.saveOrUpdate(practiceSummary);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("±£¥Ê ß∞‹");
		}

	}

	@Override
	public Result<PracticeSummary> select(String stuId) {
		try {
			PracticeSummary practiceSummary = practiceSummaryMapper.select(stuId);
			return new Result<PracticeSummary>(practiceSummary);
		} catch (Exception e) {
			return new Result<PracticeSummary>("≤È—Ø ß∞‹");
		}
	}

}
