package com.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.PracticeSelfIdentificationMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSelfIdentification;

@Service
public class PracticeSelfIdentificationServiceImp implements PracticeSelfIdentificationService {
	@Autowired
	private PracticeSelfIdentificationMapper practiceSelfIdentificationMapper;

	@Override
	public Result<NullData> saveOrUpdate(PracticeSelfIdentification practiceSelfIdentification) {
		try {
			practiceSelfIdentificationMapper.saveOrUpdate(practiceSelfIdentification);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("±£¥Ê ß∞‹");
		}
	}

	@Override
	public Result<PracticeSelfIdentification> select(String stuId) {
		try {
			PracticeSelfIdentification practiceSelfIdentification = practiceSelfIdentificationMapper.select(stuId);
			return new Result<PracticeSelfIdentification>(practiceSelfIdentification);
		} catch (Exception e) {
			return new Result<PracticeSelfIdentification>("≤È’“¥ÌŒÛ");
		}
	}

}
