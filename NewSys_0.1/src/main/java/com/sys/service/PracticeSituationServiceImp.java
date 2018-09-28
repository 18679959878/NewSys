package com.sys.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.PracticeSituationMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.PracticeSituation;

@Service
public class PracticeSituationServiceImp implements PracticeSituationService {
	@Autowired
	private PracticeSituationMapper practiceSituationMapper;

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(PracticeSituation practiceSituation) {
		try {
			practiceSituationMapper.saveOrUpdate(practiceSituation);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("系统错误，保存失败");
		}
	}

	@Override
	public Result<PracticeSituation> select(String stuId) {
		try {
			PracticeSituation practiceSituation = practiceSituationMapper.select(stuId);
			return new Result<PracticeSituation>(practiceSituation);
		} catch (Exception e) {
			return new Result<PracticeSituation>("系统错误");
		}
	}

}
