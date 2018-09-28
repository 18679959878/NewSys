package com.sys.service;

import java.util.Date;
import java.util.Map;

import javax.persistence.Cache;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.AdminMapper;
import com.sys.dto.Result;
import com.sys.entity.Admin;

@Service
public class AdminServiceImp implements AdminService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdminMapper adminMapper;
	@Autowired(required = false)
	// @Autowired
	private HttpSession session;

	@Override
	@Transactional
	public boolean storageAdmin(Admin admin) {
		// 受影响行数
		int a_row = 0;
		// 如果admin不为空，将admin对象持久化到数据库
		if (admin != null) {
			a_row = adminMapper.save(admin);
		} else {
			logger.error("[ADMIN]  添加管理员错误，管理员为null");
			return false;
		}
		// 如果a_row大于0返回true,否则返回false
		if (a_row > 0) {
			logger.info("[ADMIN]   添加管理员" + admin.getAccount() + "成功!");
			return true;
		}
		return false;
	}

	@Override
	public Result<Map<Object, Object>> successLogin(String account, String password) {
		// TODO Auto-generated method stub
		try {
			if (account == null || password == null) {
				logger.info("[ADMIN LOGIN ERROR]   登陆失败，登陆账号:" + account + "  密码:" + password);
				return new Result<>("登录失败，登陆账号或者密码为空");
			} else {
				Admin admin = adminMapper.select(account);
				if (admin != null) {
					if (password.equals(admin.getPassword())) {
						logger.info("[ADMIN LOGIN SUCCESS]   登陆成功,登陆账号：" + account);
						session.setAttribute("admin", admin);
						return new Result<Map<Object, Object>>(new HashedMap());
					} else {
						logger.info("[ADMIN LOGIN SUCCESS]   登陆失败，密码错误,登陆账号：" + account + "登陆密码：" + password);
						return new Result<>("登录失败，密码错误");
					}
				} else {
					logger.info("[ADMIN LOGIN ERROR]   登陆失败，账号不存在,登陆账号：" + account);
					return new Result<>("登录失败，账号不存在");
				}
			}
		} catch (Exception e) {
			return new Result<>("系统错误，无法登陆");
		} finally {
			session.removeAttribute("teacher");
			session.removeAttribute("student");
		}
	}

	@Override
	public String getAdminName() {
		Admin admin = (Admin) session.getAttribute("admin");
		logger.debug((admin == null) + "");
		if (admin != null) {
			return admin.getAccount();
		}
		return null;
	}

}
