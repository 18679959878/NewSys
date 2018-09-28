package com.sys.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Progress;
import com.sys.service.StudentService;
import com.sys.service.TeacherService;
import com.sys.utils.ExcelProcess;

@Controller
@RequestMapping("/new")
public class NewUploadController {
	// �ļ�����
	private final String STUDENT_EXCEL_TYPE = "student_e";// ѧ����
	private final String TEACHER_EXCEL_TYPE = "teacher_e";// ��ʦ��

	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result<NullData> index(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fileObj") CommonsMultipartFile file, String type) {
		File u_file = null;
		try {
			if (file.getSize() > 0) {
				String uploadPath = "";
				if (type.equals(STUDENT_EXCEL_TYPE)){
					uploadPath = "F:/upLoadExcel/student";
				}
				else if (type.equals(TEACHER_EXCEL_TYPE))
					uploadPath = "F:/upLoadExcel/teacher";
				// �������ص��ļ�
				u_file = new File(uploadPath, file.getOriginalFilename());
				System.out.println("hahha");
				FileUtils.copyInputStreamToFile(file.getInputStream(), u_file);
				// result.put("tag", true);
				// ���Ϊѧ���������EXcel�ļ���������
				if (type.equals(STUDENT_EXCEL_TYPE)) {
					// ���÷������ļ����д������
					return studentService.importStudentExcel(u_file);
				} else if (type.equals(TEACHER_EXCEL_TYPE)) {
					return teacherService.importTeacherExcel(u_file);
				}
				return new Result<NullData>(new NullData());
			} else {
				return new Result<NullData>("�ļ�Ϊ��");
			}
		} catch (Exception e) {
			return new Result<NullData>("����ʧ��");
		} finally {
		}

	}

	@ResponseBody
	@RequestMapping("/getInfo")
	public Map<String, Object> getUploadInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		response.setHeader("Cache-Control", "no-store"); // ��ֹ���������
		response.setHeader("Pragrma", "no-cache"); // ��ֹ���������

		response.setDateHeader("Expires", 0); // ��ֹ���������

		Progress status = (Progress) request.getSession(true).getAttribute("status");// ��session�ж�ȡ�ϴ���Ϣ
		if (status == null) {
			result.put("error", "û�����ϴ��ļ�!");
			return result;
		}

		long startTime = status.getStartTime(); // �ϴ���ʼʱ��
		long currentTime = System.currentTimeMillis(); // ����ʱ��
		long time = (currentTime - startTime) / 1000 + 1;// �Ѿ���˳��ʱ�� ��λ��s
		double velocity = status.getBytesRead() / time / 1024; // �����ٶȣ�kb/s
		double totalTime = status.getContentLength() / velocity; // ������ʱ��
		double timeLeft = totalTime - time; // ����ʣ��ʱ��
		int percent = (int) (100 * (double) status.getBytesRead() / (double) status.getContentLength()); // �ٷֱ�
		double length = (double) status.getBytesRead() / 1024; // �������
		double totalLength = (double) status.getContentLength() / 1024; // �ܳ���,��λM
		length = (double) Math.round(length * 100) / 100;
		totalLength = (double) Math.round(length * 100) / 100;
		result.put("startTime", startTime);
		result.put("currentTime", currentTime);
		result.put("time", time);
		result.put("velocity", velocity);
		result.put("totalTime", totalTime);
		result.put("timeLeft", timeLeft);
		result.put("percent", percent);
		result.put("length", length);
		result.put("totalLength", totalLength);
		return result;
	}
}
