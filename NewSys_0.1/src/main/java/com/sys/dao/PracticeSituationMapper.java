package com.sys.dao;

import com.sys.entity.PracticeSituation;

public interface PracticeSituationMapper {
	public PracticeSituation select(String stuId);

	public void saveOrUpdate(PracticeSituation practiceSituation);
}
