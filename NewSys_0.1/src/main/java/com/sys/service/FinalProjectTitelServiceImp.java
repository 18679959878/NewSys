package com.sys.service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.ClassGradeMapper;
import com.sys.dao.FinalProjectTitelMapper;
import com.sys.dao.StudentMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.ClassGrade;
import com.sys.entity.FinalProjectTitel;
import com.sys.entity.Student;

@Service
public class FinalProjectTitelServiceImp implements FinalProjectTitelService {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private FinalProjectTitelMapper finalProjectTitelMapper;
	@Autowired
	private ClassGradeMapper classGradeMapper;
	@Autowired(required = false)
	// @Autowired
	private HttpSession session;

	@Override
	@Transactional
	public Result<NullData> saveOrUpdate(FinalProjectTitel finalProjectTitel) {

		FinalProjectTitel finProjectTitel = finalProjectTitelMapper.select(finalProjectTitel.getStuId());
		if (session.getAttribute("teacher") == null && finProjectTitel != null
				&& finProjectTitel.getIsSumbit() == true) {
			return new Result<NullData>("开题报告被锁定，请联系指导老师解锁后更改");
		} else {
			finalProjectTitel.setStuId(finalProjectTitel.getStuId());
			finalProjectTitelMapper.saveOrUpdate(finalProjectTitel);
			return new Result<NullData>(new NullData());
		}
	}

	@Override
	public Result<FinalProjectTitel> select(String stuId) {
		Student student;
		student = (Student) session.getAttribute("student");
		if (student == null) {
			student = studentMapper.select(stuId);
		}
		FinalProjectTitel finalProjectTitel = finalProjectTitelMapper.select(student.getStuId());
		if (finalProjectTitel != null) {
			finalProjectTitel.setStuName(student.getStuName());
			ClassGrade classGrade = classGradeMapper.select(student.getClassName());
			if (classGrade.getEducation().equals("本科")) {
				finalProjectTitel.setDivision((Integer.valueOf(classGrade.getGrade().substring(0, 4)) + 4) + "届");
			} else {
				finalProjectTitel.setDivision((Integer.valueOf(classGrade.getGrade().substring(0, 4)) + 3) + "届");
			}
			System.out.println(classGrade.getMajor());
			finalProjectTitel.setMajorName(classGrade.getMajor());
			return new Result<FinalProjectTitel>(finalProjectTitel);
		} else {
			return new Result<FinalProjectTitel>("返回失败");
		}
	}

}
