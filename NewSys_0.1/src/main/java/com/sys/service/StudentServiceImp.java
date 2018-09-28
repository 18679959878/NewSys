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
		// �ж����ݿ��Ƿ��Ѿ����ڣ������򷵻ش�����Ϣ����ȷ����Ӳ�������ӳɹ���Ϣ
		if (studentMapper.select(student.getStuId()) == null) {
			studentMapper.save(student);
			return new Result<Student>(new Student());
		} else {
			return new Result<Student>("ѧ��ѧ���Ѵ���");
		}
	}

	@Override
	public Result<Map<String, List<Student>>> selectClassStudent(String className) {
		// �жϰ༶���Ƿ�Ϊ��
		if (className != null) {
			// ��ѯ�༶���е�ѧ������
			ArrayList<Student> students = studentMapper.selectClassStudents(className);
			Map<String, List<Student>> map = new HashedMap();
			map.put("students", students);
			return new Result<Map<String, List<Student>>>(map);
		}
		return new Result<Map<String, List<Student>>>("�༶��Ϊ��");
	}

	@Override
	@Transactional
	public Result<NullData> update(Student student) {
		// ����ѧ������
		try {
			// ���ԭ����ѧ������
			Student oldStudent = studentMapper.select(student.getStuId());
			// �ж�����ѧ������༶���Ƿ�һ��
			if (student.getClassName() != null && !student.getClassName().equals(oldStudent.getClassName())) {
				System.out.println("����");
				// ��һ�£��԰༶�������и���
				ClassGrade newClassGrade = classGradeMapper.select(student.getClassName());
				ClassGrade oldClassGrade = classGradeMapper.select(oldStudent.getClassName());
				classGradeMapper.addStuNum(newClassGrade.getClassName());
				classGradeMapper.reduceStuNum(oldClassGrade.getClassName());
			}
			studentMapper.update(student);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("�޸�ʧ�ܣ���˶�ָ���Ľ�ʦ�Ƿ��Ѿ�����");
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
			return new Result<NullData>("ɾ��ʧ��");
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
		// ���Ϊ�գ��������������󷵻�
		if (students == null) {
			System.out.println("������������");
			return new Result<NullData>("�ļ�����ʧ�ܣ���ȷ���Ƿ�Ϊ��׼ģ�壬����ϸ�Ķ����ģ��ע��������");
		}
		try {
			// ��������
			studentMapper.insertOrupdateBatch(students);
			// ������а༶�б�
			ArrayList<String> list = classGradeMapper.selectAllClassName(ClassGrade.OLD_CLASS);
			// ���ð༶����
			classGradeMapper.resetStuNum(list);
			System.out.println("¼�����");
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("����ʧ��");
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
				return new Result<NullData>("�������");
			}
		} else {
			return new Result<NullData>("ѧ�Ų�����");
		}

	}

	@Override
	public Result<Student> select(String stuId) {
		return new Result<Student>(studentMapper.select(stuId));
	}

}
