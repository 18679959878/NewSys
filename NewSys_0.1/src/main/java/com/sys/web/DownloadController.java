package com.sys.web;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.service.DownloadService;
import com.sys.utils.DownLoadUtils;

@Controller
@RequestMapping("/download")
public class DownloadController {
	@Autowired
	private DownloadService downloadService;

	@RequestMapping(value = "/template", produces = "application/vnd.ms-excel;charset=UTF-8")
	public ResponseEntity<byte[]> downloadTemplate(String fileName) throws IOException {
		// 获得文件
		File file = downloadService.templatePath(fileName);
		return DownLoadUtils.downLoadTemplate(file);
	}

	@RequestMapping(value = "/excel/student", produces = "application/vnd.ms-excel;charset=UTF-8")
	public ResponseEntity<byte[]> downloadDataExcel(String className) throws IOException {
		File file = downloadService.studentExcelPath(className);
		return DownLoadUtils.downLoadTemplate(file);
	}
}