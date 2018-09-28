package com.sys.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.ClassGradeMapper;
import com.sys.dao.StudentMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.ClassGrade;
import com.sys.entity.Student;
import com.sys.utils.ExcelProcess;

@Service
public class StudentServiceImp implements StudentService {
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private ClassGradeMapper classGradeMapper;
	@Autowired(required = false)
	// @Autowired
	private HttpSession session;

	@Override
	@Transactional
	public Result<Student> addStudentOne(Student student) {
		// TODO Auto-generated method stub
		// 判读数据库是否已经存在，存在则返回错误信息，正确则添加并返回添加成功信息
		if (studentMapper.select(student.getStuId()) == null) {
			studentMapper.save(student);
			return new Result<Student>(new Student());
		} else {
			return new Result<Student>("学生学号已存在");
		}
	}

	@Override
	public Result<Map<String, List<Student>>> selectClassStudent(String className) {
		// 判断班级名是否为空
		if (className != null) {
			// 查询班级所有的学生对象
			ArrayList<Student> students = studentMapper.selectClassStudents(className);
			Map<String, List<Student>> map = new HashedMap();
			map.put("students", students);
			return new Result<Map<String, List<Student>>>(map);
		}
		return new Result<Map<String, List<Student>>>("班级名为空");
	}

	@Override
	@Transactional
	public Result<NullData> update(Student student) {
		// 更新学生对象
		try {
			// 获得原来的学生对象
			Student oldStudent = studentMapper.select(student.getStuId());
			// 判断两个学生对象班级名是否一致
			if (student.getClassName() != null && !student.getClassName().equals(oldStudent.getClassName())) {
				System.out.println("到达");
				// 不一致，对班级人数进行更新
				ClassGrade newClassGrade = classGradeMapper.select(student.getClassName());
				ClassGrade oldClassGrade = classGradeMapper.select(oldStudent.getClassName());
				classGradeMapper.addStuNum(newClassGrade.getClassName());
				classGradeMapper.reduceStuNum(oldClassGrade.getClassName());
			}
			studentMapper.update(student);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("修改失败，请核对指定的教师是否已经存在");
		}
	}

	@Override
	@Transactional
	public Result<NullData> delete(String stuId) {
		try {
			studentMapper.delete(stuId);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("删除失败");
		}
	}

	@Override
	@Transactional
	public Result<NullData> importStudentExcel(File file) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		ArrayList<Student> students = (ArrayList<Student>) ExcelProcess.importExcel(file,
				ExcelProcess.EXCEL_TYPE_STUDENT);
		System.out.println("hahah9o");
		// 如果为空，构建错误结果对象返回
		if (students == null) {
			System.out.println("触摸屏惊恐万");
			return new Result<NullData>("文件解析失败，请确认是否为标准模板，并仔细阅读表格模板注意行内容");
		}
		try {
			// 批量导入
			studentMapper.insertOrupdateBatch(students);
			// 获得所有班级列表
			ArrayList<String> list = classGradeMapper.selectAllClassName(ClassGrade.OLD_CLASS);
			// 重置班级人数
			classGradeMapper.resetStuNum(list);
			System.out.println("录入结束");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("解析失败");
		}
		return new Result<NullData>(new NullData());
	}

	@Override
	public Result<NullData> studentLogin(String stuId, String password) {
		Student student = studentMapper.select(stuId);
		if (studentMapper.select(stuId) != null) {
			if (student.getPassword().equals(password)) {
				session.setAttribute("student", student);
				return new Result<NullData>(new NullData());
			} else {
				return new Result<NullData>("密码错误");
			}
		} else {
			return new Result<NullData>("学号不存在");
		}

	}

	@Override
	public Result<Student> select(String stuId) {
		return new Result<Student>(studentMapper.select(stuId));
	}

}
