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
		Progress status = new Progress();// 保存上传状态
		session.setAttribute("status", status);
	}
	

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		Progress status = (Progress) session.getAttribute("status");
		status.setBytesRead(bytesRead);// 已读取数据长度
		status.setContentLength(contentLength);// 文件总长度
		status.setItems(items);// 正在保存第几个文件

	}

}