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
		// ��Ӱ������
		int a_row = 0;
		// ���admin��Ϊ�գ���admin����־û������ݿ�
		if (admin != null) {
			a_row = adminMapper.save(admin);
		} else {
			logger.error("[ADMIN]  ��ӹ���Ա���󣬹���ԱΪnull");
			return false;
		}
		// ���a_row����0����true,���򷵻�false
		if (a_row > 0) {
			logger.info("[ADMIN]   ��ӹ���Ա" + admin.getAccount() + "�ɹ�!");
			return true;
		}
		return false;
	}

	@Override
	public Result<Map<Object, Object>> successLogin(String account, String password) {
		// TODO Auto-generated method stub
		try {
			if (account == null || password == null) {
				logger.info("[ADMIN LOGIN ERROR]   ��½ʧ�ܣ���½�˺�:" + account + "  ����:" + password);
				return new Result<>("��¼ʧ�ܣ���½�˺Ż�������Ϊ��");
			} else {
				Admin admin = adminMapper.select(account);
				if (admin != null) {
					if (password.equals(admin.getPassword())) {
						logger.info("[ADMIN LOGIN SUCCESS]   ��½�ɹ�,��½�˺ţ�" + account);
						session.setAttribute("admin", admin);
						return new Result<Map<Object, Object>>(new HashedMap());
					} else {
						logger.info("[ADMIN LOGIN SUCCESS]   ��½ʧ�ܣ��������,��½�˺ţ�" + account + "��½���룺" + password);
						return new Result<>("��¼ʧ�ܣ��������");
					}
				} else {
					logger.info("[ADMIN LOGIN ERROR]   ��½ʧ�ܣ��˺Ų�����,��½�˺ţ�" + account);
					return new Result<>("��¼ʧ�ܣ��˺Ų�����");
				}
			}
		} catch (Exception e) {
			return new Result<>("ϵͳ�����޷���½");
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
