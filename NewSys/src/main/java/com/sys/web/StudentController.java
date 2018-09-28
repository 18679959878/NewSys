package com.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Student;
import com.sys.service.ClassGradeService;
import com.sys.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassGradeService classGradeService;
	@Autowired
	private HttpSession session;

	/**
	 * ��ӵ���ѧ����Ϣ
	 * 
	 * @author ��С��
	 * @param stuId
	 *            ǰ�˴�����ѧ��
	 * @param stuName
	 *            ǰ�˴���������
	 * @param className
	 *            ǰ�˴����İ༶��
	 * @param password
	 *            ~����
	 * @param email
	 *            ~����
	 * @param worksTea
	 *            ~��ҵ���ָ����ʦ
	 * @param fieldworkTea
	 *            ~ʵϰָ����ʦ
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<Student> add(String stuId, String stuName, String className, String password, String email,
			String worksTea, String fieldworkTea) {
		if (stuId != null && stuName != null && className != null && !stuId.equals("") && !stuName.equals("")
				&& !className.equals("")) {
			Student student = new Student(stuId, stuName, email, password, className, fieldworkTea, worksTea);
			return studentService.addStudentOne(student);
		} else {
			return new Result<Student>("���ʧ�ܣ�ѧ��/����/�༶Ϊ��");
		}

	}

	/**
	 * ��ѯ���༶ѧ��
	 * 
	 * @author Administrator
	 * @param className
	 *            �༶��
	 * @return ���ظ�ǰ�˵���Ϣ
	 */
	@RequestMapping(value = "/class/select", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public Result<Map<String, List<Student>>> selectClassStudent(String className) {
		return studentService.selectClassStudent(className);

	}

	@RequestMapping(value = "/upd", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> update(String stuId, String stuName, String password, String email, String className,
			String worksTea, String fieldworkTea) {
		System.out.println(email);
		if (stuName != null && !stuName.equals("") && className != null && !className.equals("")) {
			return studentService
					.update(new Student(stuId, stuName, email, password, className, fieldworkTea, worksTea));
		}
		return new Result<NullData>("���������벻��Ϊ��");
	}

	/**
	 * @param password
	 * @param c_password
	 * @return
	 */
	@RequestMapping(value = "/password/upd", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> updPassword(String password, String c_password) {
		try {
			if (!password.equals(c_password)) {
				return new Result<NullData>("�����������벻һ��");
			}
			Student student = (Student) session.getAttribute("student");
			student.setPassword(c_password);
			studentService.update(student);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("�������޸�ʧ��");
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<NullData> delete(String stuId) {
		return studentService.delete(stuId);
	}

	@RequestMapping(value = "/myInfo", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<Student> myInfo() {
		try {
			Student student = (Student) session.getAttribute("student");
			return new Result<Student>(student);
		} catch (Exception e) {
			return new Result<Student>("��ѯʧ��");

		}
	}
}
