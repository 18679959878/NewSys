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
	// 学生表类型
	private final int STUDENT_EXCEL_TYPE = 0;
	@Autowired
	private StudentService studentService;
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Result<NullData> upload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpSession session, int type) {
		// 如果文件不为空
		if (!file.isEmpty()) {
			// 文件存储路径
			String path = "f://fileUpload/student";
			// 文件名称
			String name = String.valueOf(file.getOriginalFilename());
			// 构造最终下载的文件对象
			File destFile = new File(path, name);
			// 转存文件
			try {
				file.transferTo(destFile);
			} catch (IllegalStateException | IOException e) {
				destFile.delete();
				return new Result<NullData>("文件上传失败");
			}
			// 如果上传的是学生表id，则调用学studentService进行处理
			if (type == STUDENT_EXCEL_TYPE) {
				try {
					// 调用studentService的importStudentExcel到处数据库内的数据
					studentService.importStudentExcel(destFile);
				} catch (IOException e) {
					destFile.delete();
					return new Result<NullData>("上传成功，解析过程失败");
				}
			}
		}
		return new Result<NullData>("上传成功，解析成功");
	}
}
