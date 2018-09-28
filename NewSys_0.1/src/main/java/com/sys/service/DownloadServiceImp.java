package com.sys.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.ClassGradeMapper;
import com.sys.dao.StudentMapper;
import com.sys.entity.ClassGrade;
import com.sys.entity.Student;
import com.sys.utils.ExcelProcess;

@Service
public class DownloadServiceImp implements DownloadService {
	// 模板文件路径
	private final String templateFilePath = "e://template/";
	// 下载所有班级标示
	private final String ALL = "all";
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private ClassGradeMapper classGradeMapper;

	@Override
	public File templatePath(String fileName) {
		File file = new File(templateFilePath + fileName);
		return file;
	}

	@Override
	public File studentExcelPath(String className) {
		// TODO Auto-generated method stub
		// 存储查询到的学生
		ArrayList<Student> students = new ArrayList<Student>();
		// 文件名
		String fileName;
		// 如果为all，则查询所有班级的学生
		if (className.equals(ALL)) {
			// 查询所有班级名
			ArrayList<String> classNames = classGradeMapper.selectAllClassName(ClassGrade.OLD_CLASS);
			for (String cName : classNames) {
				// 将查询到的结果添加到students
				students.addAll(studentMapper.selectClassStudents(cName));
			}
			fileName = "软件动漫学院学生表";
		} else {
			students = studentMapper.selectClassStudents(className);
			fileName = className + "学生表";
		}
		File file = ExcelProcess.exportStudentExcel(students, fileName);
		return file;
	}

}
