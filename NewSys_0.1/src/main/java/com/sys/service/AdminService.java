package com.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sys.dto.Result;
import com.sys.entity.Admin;

public interface AdminService {
	/**
	 * 将管理员对象添加到数据库
	 * 
	 * @author 金小瑶
	 * @return 返回添加成功标示，true为添加成功,false为失败
	 * @param 持久化的管理员对象
	 */
	public abstract boolean storageAdmin(Admin admin);

	/**
	 * 判断数据库是否该管理员账号登陆信息是否正确
	 * 
	 * @author 金小瑶
	 * @return 返回账号验证信息
	 * @param account
	 *            登陆账号
	 * @param password
	 *            登陆密码
	 */
	public abstract Result<Map<Object, Object>> successLogin(String account, String password);

	/**
	 * 给出当前session中，客户的姓名
	 * 
	 * @author 金小瑶
	 * @return 返回客户姓名
	 */
	public abstract String getAdminName();
}
