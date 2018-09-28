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
	 * ����������
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> downLoadTemplate(File file) throws IOException {
		byte[] body = null;
		// ��ȡ�ļ�
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		HttpHeaders headers = new HttpHeaders();
		// �����ļ�����
		headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(file.getName(), "utf8"));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// ����http״̬��
		HttpStatus statusCode = HttpStatus.OK;
		// ��������
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return entity;
	}
}
