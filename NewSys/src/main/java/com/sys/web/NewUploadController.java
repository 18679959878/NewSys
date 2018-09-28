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
	// 文件类型
	private final String STUDENT_EXCEL_TYPE = "student_e";// 学生表
	private final String TEACHER_EXCEL_TYPE = "teacher_e";// 教师表

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
				// 最终下载的文件
				u_file = new File(uploadPath, file.getOriginalFilename());
				System.out.println("hahha");
				FileUtils.copyInputStreamToFile(file.getInputStream(), u_file);
				// result.put("tag", true);
				// 如果为学生表则调用EXcel文件解析类型
				if (type.equals(STUDENT_EXCEL_TYPE)) {
					// 调用方法对文件进行处理分析
					return studentService.importStudentExcel(u_file);
				} else if (type.equals(TEACHER_EXCEL_TYPE)) {
					return teacherService.importTeacherExcel(u_file);
				}
				return new Result<NullData>(new NullData());
			} else {
				return new Result<NullData>("文件为空");
			}
		} catch (Exception e) {
			return new Result<NullData>("传输失败");
		} finally {
		}

	}

	@ResponseBody
	@RequestMapping("/getInfo")
	public Map<String, Object> getUploadInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		response.setHeader("Cache-Control", "no-store"); // 禁止浏览器缓存
		response.setHeader("Pragrma", "no-cache"); // 禁止浏览器缓存

		response.setDateHeader("Expires", 0); // 禁止浏览器缓存

		Progress status = (Progress) request.getSession(true).getAttribute("status");// 从session中读取上传信息
		if (status == null) {
			result.put("error", "没发现上传文件!");
			return result;
		}

		long startTime = status.getStartTime(); // 上传开始时间
		long currentTime = System.currentTimeMillis(); // 现在时间
		long time = (currentTime - startTime) / 1000 + 1;// 已经传顺的时间 单位：s
		double velocity = status.getBytesRead() / time / 1024; // 传输速度：kb/s
		double totalTime = status.getContentLength() / velocity; // 估计总时间
		double timeLeft = totalTime - time; // 估计剩余时间
		int percent = (int) (100 * (double) status.getBytesRead() / (double) status.getContentLength()); // 百分比
		double length = (double) status.getBytesRead() / 1024; // 已完成数
		double totalLength = (double) status.getContentLength() / 1024; // 总长度,单位M
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
