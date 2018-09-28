package com.sys.service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.DesignProcessMapper;
import com.sys.dao.ReportMapper;
import com.sys.dao.StudentMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DesignProcess;
import com.sys.entity.Report;
import com.sys.entity.Student;

@Service
public class ReportServiceImp implements ReportService {
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired(required = false)
	private HttpSession session;

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(Report report) {
		try {
			System.out.println("ceshi1");
			Report s_report = reportMapper.select(report.getStuId(), report.getStageName());
			System.out.println("iohjsaho");
			System.out.println(s_report==null);
			if (session.getAttribute("teacher") == null && s_report != null && s_report.getIsLock() == true) {
				return new Result<NullData>("开题报告被锁定，请联系指导老师解锁后更改");
			} else {
				System.out.println("nihnnisabnibnisa");
		
				reportMapper.saveOrUpdate(report);
				System.out.println("找到");
				return new Result<NullData>(new NullData());
			}
		} catch (Exception e) {
			return new Result<NullData>("服务器错误");
		}
	}

	@Override
	public Result<Report> select(String stuId, String stageName) {
		try {
			Student student = (Student) session.getAttribute("student");
			if (student == null) {
				student = studentMapper.select(stuId);
			}
			Report report = reportMapper.select(student.getStuId(), stageName);
			System.out.println(report.getIsLock());
			return new Result<Report>(report);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<Report>("服务器错误");
		}
	}

	@Override
	public Result<NullData> saveOrUpdate(String stuId, String stageName, String reportText, String guidance, int score,
			boolean isLock) {
		try {
			Report s_report = reportMapper.select(stuId, stageName);
			if (s_report != null) {
				s_report.setScore(score);
				s_report.setIsLock(isLock);
				s_report.setGuidance(guidance);
				reportMapper.saveOrUpdate(s_report);
				return new Result<NullData>(new NullData());
			} else {
				return new Result<NullData>("学生暂时没有提交");
			}

		} catch (Exception e) {
			return new Result<NullData>("服务器错误");
		}
	}

}