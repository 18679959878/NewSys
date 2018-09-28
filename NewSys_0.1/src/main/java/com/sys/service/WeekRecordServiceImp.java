package com.sys.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.WeekRecordMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.WeekRecord;
@Service
public class WeekRecordServiceImp implements WeekRecordService {
	@Autowired
	private WeekRecordMapper weekRecordMapper;

	@Override
	public Result<WeekRecord> select(String stuId, String stageName) {
		try {
			WeekRecord weekRecord = weekRecordMapper.select(stuId, stageName);
			return new Result<WeekRecord>(weekRecord);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<WeekRecord>("≤È—Ø¥ÌŒÛ");
		}
	}

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(WeekRecord weekRecord) {
		try {
			weekRecordMapper.saveOrUpdate(weekRecord);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("±£¥Ê ß∞‹");
		}
	}

}
