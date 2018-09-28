package com.sys.dao;

import com.sys.entity.Admin;

public interface AdminMapper {
	/**
	 * 
	 * @param admin
	 *            功能:向数据库添加一个管理员，返回受影响行数
	 */
	public int save(Admin admin);

	/**
	 * 
	 * @param account 管理员账号
	 * @return 管理员对象 
	 * 功能:根据账号查询数据库中的账号
	 */
	public Admin select(String account);

}
