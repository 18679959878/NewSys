package com.sys.dao;

import com.sys.entity.PracticeSummary;

public interface PracticeSummaryMapper {

	public void saveOrUpdate(PracticeSummary practiceSummary);

	public PracticeSummary select(String stuId);
}
