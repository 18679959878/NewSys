package com.sys.dao;

import com.sys.entity.Admin;

public interface AdminMapper {
	/**
	 * 
	 * @param admin
	 *            ����:�����ݿ����һ������Ա��������Ӱ������
	 */
	public int save(Admin admin);

	/**
	 * 
	 * @param account ����Ա�˺�
	 * @return ����Ա���� 
	 * ����:�����˺Ų�ѯ���ݿ��е��˺�
	 */
	public Admin select(String account);

}
