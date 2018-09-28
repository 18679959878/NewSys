package com.sys.old;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.sys.service.StudentService;

@RequestMapping("/file")
@Controller
public class FileUploadController {
	// 学生表类型
	private final int STUDENT_EXCEL_TYPE = 0;
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/newUpload")
	@ResponseBody
	public FileMeta upload(MultipartHttpServletRequest request, HttpServletResponse response, String type) {
		String path = "f://fileUpload/student";
		MultipartFile mpf = request.getFile("file");
		System.out.println("测试");
		FileMeta fileMeta = null;
		if (mpf != null) {
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
			fileMeta.setFileType(mpf.getContentType());
			try {
				fileMeta.setBytes(mpf.getBytes());
				mpf.transferTo(new File(path, mpf.getOriginalFilename()));
			} catch (IOException e) {
				System.out.println("上传失败");
				e.printStackTrace();
			}
		}
		return fileMeta;
	}

}
