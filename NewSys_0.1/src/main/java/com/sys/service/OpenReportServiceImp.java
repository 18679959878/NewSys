package com.sys.service;

import javax.persistence.Cache;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.OpenReportMapper;
import com.sys.dao.StudentMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.OpenReport;
import com.sys.entity.Student;

@Service
public class OpenReportServiceImp implements OpenReportService {
	@Autowired
	private OpenReportMapper openReportMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired(required = false)
	private HttpSession session;

	@Override
	public Result<OpenReport> select(String stuId) {
		try {
			Student student = (Student) session.getAttribute("student");
			if (student == null) {
				student = studentMapper.select(stuId);
			}
			OpenReport openReport = openReportMapper.select(student.getStuId());
			return new Result<OpenReport>(openReport);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<OpenReport>("服务器错误");
		}
	}

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(OpenReport openReport) {
		try {
			OpenReport s_openReport = openReportMapper.select(openReport.getStuId());
			if (session.getAttribute("teacher") == null && s_openReport != null && s_openReport.getIsLock() == true) {
				return new Result<NullData>("开题报告被锁定，请联系指导老师解锁后更改");
			} else {
				System.out.println(openReport.getScore()+"cxccccxdxdffggfvcdwwsaa");
				openReportMapper.saveOrUpdate(openReport);
				return new Result<NullData>(new NullData());
			}
		} catch (Exception e) {
			return new Result<NullData>("服务器错误");
		}
	}

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(String stuId, String agree, int score, boolean isLock) {
		try {
			OpenReport s_openReport = openReportMapper.select(stuId);
			s_openReport.setStuId(stuId);
			s_openReport.setAgree(agree);
			s_openReport.setScore(score);
			s_openReport.setIsLock(isLock);
			openReportMapper.saveOrUpdate(s_openReport);
			return new Result<NullData>(new NullData());

		} catch (Exception e) {
			return new Result<NullData>("服务器错误");
		}
	}

}
