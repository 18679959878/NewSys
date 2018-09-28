package com.sys.service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.DesignProcessMapper;
import com.sys.dao.StudentMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DesignProcess;
import com.sys.entity.FinalProjectTitel;
import com.sys.entity.OpenReport;
import com.sys.entity.Student;

@Service
public class DesignProcessServiceImp implements DesignProcessService {
	@Autowired
	private DesignProcessMapper designProcessMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired(required = false)
	private HttpSession session;

	@Override
	public Result<DesignProcess> select(String stuId, String stageName) {
		try {
			Student student = (Student) session.getAttribute("student");
			if (student == null) {
				student = studentMapper.select(stuId);
			}
			DesignProcess designProcess = designProcessMapper.select(student.getStuId(), stageName);
			System.out.println(designProcess.getIsLock());
			return new Result<DesignProcess>(designProcess);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<DesignProcess>("服务器错误");
		}
	}

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(DesignProcess designProcess) {
		try {
			DesignProcess s_designProcess = designProcessMapper.select(designProcess.getStuId(),
					designProcess.getStageName());
			if (session.getAttribute("teacher") == null && s_designProcess != null
					&& s_designProcess.getIsLock() == true) {
				return new Result<NullData>("开题报告被锁定，请联系指导老师解锁后更改");
			} else {
				designProcessMapper.saveOrUpdate(designProcess);
				return new Result<NullData>(new NullData());
			}
		} catch (Exception e) {
			System.out.println("cuowu");
			return new Result<NullData>("服务器错误");
		}
	}

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(String stuId, String stageName, String amendOpinion, String guidance,
			int score, boolean isLock) {
		try {
			DesignProcess s_designProcess = designProcessMapper.select(stuId, stageName);
			if (s_designProcess != null) {
				s_designProcess.setAmendOpinion(amendOpinion);
				s_designProcess.setScore(score);
				s_designProcess.setIsLock(isLock);
				s_designProcess.setGuidance(guidance);
				designProcessMapper.saveOrUpdate(s_designProcess);
				System.out.println("成功");
				return new Result<NullData>(new NullData());
			} else {
				return new Result<NullData>("学生暂时没有提交");
			}

		} catch (Exception e) {
			return new Result<NullData>("服务器错误");
		}
	}

}
