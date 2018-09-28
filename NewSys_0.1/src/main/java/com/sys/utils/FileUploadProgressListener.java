package com.sys.utils;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import com.sys.entity.Progress;

@Component
public class FileUploadProgressListener implements ProgressListener {
	private HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
		Progress status = new Progress();// �����ϴ�״̬
		session.setAttribute("status", status);
	}
	

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		Progress status = (Progress) session.getAttribute("status");
		status.setBytesRead(bytesRead);// �Ѷ�ȡ���ݳ���
		status.setContentLength(contentLength);// �ļ��ܳ���
		status.setItems(items);// ���ڱ���ڼ����ļ�

	}

}