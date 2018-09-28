package com.sys.aspect;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.sys.annotation.FPValidatePermission;
import com.sys.dao.DateStageMapper;
import com.sys.entity.DateStage;
import com.sys.entity.Student;
import com.sys.exception.DateJurisdictionException;
import com.sys.exception.LandJurisdictionException;

public class FinalProjectTitelAspect {
	@Autowired
	private DateStageMapper dateStageMapper;
	@Autowired
	private HttpSession session;

	private final int DATE_TYPE = 1;

	// ���淽����FPTiltelValidatePermissionע�ͷ���ǰ�����е���
	public void doBefore(JoinPoint jp) throws Exception {
		// ������淽��
		Method soruceMethod = getSourceMethod(jp);
		String stageName;
		if (soruceMethod != null) {
			// ��÷���ǰ��ע��
			FPValidatePermission oper = soruceMethod.getAnnotation(FPValidatePermission.class);
			// ���ע�Ͳ�Ϊ��
			if (oper != null) {
				// �������Ŀ�귽��������
				Object[] args = jp.getArgs();
				//ע�����Ϊ��ֵ����ӷ�������������ֵ
				if (oper.stageName().equals("")) {
					stageName = (String) args[1];
				} else {
					stageName = oper.stageName();
				}
				// ͨ��ע�ͻ�����淽���Ĳ�����ѧ��
				String stuId = (String) args[0];
				if (stuId == null || stuId.trim().equals("")) {
					// ѧ��Ϊ�գ����жϵ�ǰ��½�û��Ƿ�Ϊѧ����Ϊѧ����ͨ��session���ѧ�ţ������׳���½Ȩ���쳣
					if (session.getAttribute("student") != null) {
						stuId = ((Student) session.getAttribute("student")).getStuId();
					} else {
						throw new LandJurisdictionException();
					}
				} else {
					// ѧ�Ų�Ϊ�գ����жϵ�ǰ��½�û��Ƿ�Ϊ��ʦ����Ϊ��ʦ�����׳���½Ȩ���쳣
					if (session.getAttribute("teacher") == null) {
						throw new LandJurisdictionException();
					}
				}
				DateStage dateStage = dateStageMapper.select(stuId, stageName);
				if (dateStage != null) {
					// �жϵ�ǰʱ���Ƿ��Ѿ�����Ȩ�ޣ����û�п���Ȩ�ޣ����׳�ʱ��Ȩ���쳣
					if (dateStageMapper.select(stuId, stageName).getStartDate().getTime() < System
							.currentTimeMillis()) {
						return;
					} else {
						throw new DateJurisdictionException();
					}
				} else {
					throw new DateJurisdictionException();
				}
			}
		}
	}

	private Method getSourceMethod(JoinPoint jp) {
		Method proxyMethod = ((MethodSignature) jp.getSignature()).getMethod();
		try {
			return jp.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
