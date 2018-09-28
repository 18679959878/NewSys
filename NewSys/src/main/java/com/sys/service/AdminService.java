package com.sys.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sys.dto.Result;
import com.sys.entity.Admin;

public interface AdminService {
	/**
	 * ������Ա������ӵ����ݿ�
	 * 
	 * @author ��С��
	 * @return ������ӳɹ���ʾ��trueΪ��ӳɹ�,falseΪʧ��
	 * @param �־û��Ĺ���Ա����
	 */
	public abstract boolean storageAdmin(Admin admin);

	/**
	 * �ж����ݿ��Ƿ�ù���Ա�˺ŵ�½��Ϣ�Ƿ���ȷ
	 * 
	 * @author ��С��
	 * @return �����˺���֤��Ϣ
	 * @param account
	 *            ��½�˺�
	 * @param password
	 *            ��½����
	 */
	public abstract Result<Map<Object, Object>> successLogin(String account, String password);

	/**
	 * ������ǰsession�У��ͻ�������
	 * 
	 * @author ��С��
	 * @return ���ؿͻ�����
	 */
	public abstract String getAdminName();
}
