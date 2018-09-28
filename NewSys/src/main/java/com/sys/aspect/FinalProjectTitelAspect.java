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

	// 切面方法，FPTiltelValidatePermission注释方法前，进行调用
	public void doBefore(JoinPoint jp) throws Exception {
		// 获得切面方法
		Method soruceMethod = getSourceMethod(jp);
		String stageName;
		if (soruceMethod != null) {
			// 获得方法前的注释
			FPValidatePermission oper = soruceMethod.getAnnotation(FPValidatePermission.class);
			// 如果注释不为空
			if (oper != null) {
				// 获得切入目标方法的所有
				Object[] args = jp.getArgs();
				//注解参数为空值，则从方法参数获得相关值
				if (oper.stageName().equals("")) {
					stageName = (String) args[1];
				} else {
					stageName = oper.stageName();
				}
				// 通过注释获得切面方法的参数，学号
				String stuId = (String) args[0];
				if (stuId == null || stuId.trim().equals("")) {
					// 学号为空，则判断当前登陆用户是否为学生，为学生则通过session获得学号，否则抛出登陆权限异常
					if (session.getAttribute("student") != null) {
						stuId = ((Student) session.getAttribute("student")).getStuId();
					} else {
						throw new LandJurisdictionException();
					}
				} else {
					// 学号不为空，则判断当前登陆用户是否为教师，不为教师，则抛出登陆权限异常
					if (session.getAttribute("teacher") == null) {
						throw new LandJurisdictionException();
					}
				}
				DateStage dateStage = dateStageMapper.select(stuId, stageName);
				if (dateStage != null) {
					// 判断当前时间是否已经开放权限，如果没有开放权限，则抛出时间权限异常
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
