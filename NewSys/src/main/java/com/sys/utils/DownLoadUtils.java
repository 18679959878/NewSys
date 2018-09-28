package com.sys.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class DownLoadUtils {
	/**
	 * 构建下载类
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> downLoadTemplate(File file) throws IOException {
		byte[] body = null;
		// 获取文件
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		HttpHeaders headers = new HttpHeaders();
		// 设置文件类型
		headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(file.getName(), "utf8"));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 设置http状态码
		HttpStatus statusCode = HttpStatus.OK;
		// 返回数据
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return entity;
	}
}
