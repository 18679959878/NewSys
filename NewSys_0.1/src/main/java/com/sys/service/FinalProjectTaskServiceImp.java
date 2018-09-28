package com.sys.service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.FinalProjectTaskMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.FinalProjectTask;
import com.sys.entity.Student;

@Service
public class FinalProjectTaskServiceImp implements FinalProjectTaskService {
	@Autowired(required = false)
	private HttpSession session;
	@Autowired
	private FinalProjectTaskMapper finalProjectTaskMapper;

	@Override
	@Transactional
	public Result<FinalProjectTask> select(String stuId) {
		if (stuId == null || stuId.equals("")) {
			stuId = ((Student) session.getAttribute("student")).getStuId();
		}
		FinalProjectTask finalProjectTask = finalProjectTaskMapper.select(stuId);
		if (finalProjectTask != null) {
			finalProjectTask.setState("已提交");
			return new Result<FinalProjectTask>(finalProjectTask);
		} else {
			return new Result<FinalProjectTask>("未查询到相关结果");
		}
	}

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(FinalProjectTask finalProjectTask) {
		try {
			finalProjectTaskMapper.saveOrUpdate(finalProjectTask);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("错误");
			return new Result<NullData>("添加错误");
		}
	}

}
