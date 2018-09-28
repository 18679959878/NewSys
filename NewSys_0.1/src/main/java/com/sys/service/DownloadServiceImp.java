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
	// ģ���ļ�·��
	private final String templateFilePath = "e://template/";
	// �������а༶��ʾ
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
		// �洢��ѯ����ѧ��
		ArrayList<Student> students = new ArrayList<Student>();
		// �ļ���
		String fileName;
		// ���Ϊall�����ѯ���а༶��ѧ��
		if (className.equals(ALL)) {
			// ��ѯ���а༶��
			ArrayList<String> classNames = classGradeMapper.selectAllClassName(ClassGrade.OLD_CLASS);
			for (String cName : classNames) {
				// ����ѯ���Ľ����ӵ�students
				students.addAll(studentMapper.selectClassStudents(cName));
			}
			fileName = "�������ѧԺѧ����";
		} else {
			students = studentMapper.selectClassStudents(className);
			fileName = className + "ѧ����";
		}
		File file = ExcelProcess.exportStudentExcel(students, fileName);
		return file;
	}

}
