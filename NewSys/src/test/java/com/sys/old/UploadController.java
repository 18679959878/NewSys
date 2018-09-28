package com.sys.old;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.service.StudentService;

@RequestMapping("/file")
@Controller
public class UploadController {
	// ѧ��������
	private final int STUDENT_EXCEL_TYPE = 0;
	@Autowired
	private StudentService studentService;
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Result<NullData> upload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpSession session, int type) {
		// ����ļ���Ϊ��
		if (!file.isEmpty()) {
			// �ļ��洢·��
			String path = "f://fileUpload/student";
			// �ļ�����
			String name = String.valueOf(file.getOriginalFilename());
			// �����������ص��ļ�����
			File destFile = new File(path, name);
			// ת���ļ�
			try {
				file.transferTo(destFile);
			} catch (IllegalStateException | IOException e) {
				destFile.delete();
				return new Result<NullData>("�ļ��ϴ�ʧ��");
			}
			// ����ϴ�����ѧ����id�������ѧstudentService���д���
			if (type == STUDENT_EXCEL_TYPE) {
				try {
					// ����studentService��importStudentExcel�������ݿ��ڵ�����
					studentService.importStudentExcel(destFile);
				} catch (IOException e) {
					destFile.delete();
					return new Result<NullData>("�ϴ��ɹ�����������ʧ��");
				}
			}
		}
		return new Result<NullData>("�ϴ��ɹ��������ɹ�");
	}
}
