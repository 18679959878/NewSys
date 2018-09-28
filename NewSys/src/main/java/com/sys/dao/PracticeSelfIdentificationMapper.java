package com.sys.dao;

import com.sys.entity.PracticeSelfIdentification;

public interface PracticeSelfIdentificationMapper {
	public void saveOrUpdate(PracticeSelfIdentification practiceSelfIdentification);

	public PracticeSelfIdentification select(String stuId);
}
