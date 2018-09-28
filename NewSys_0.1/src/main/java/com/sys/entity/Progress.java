package com.sys.entity;

public class Progress {

	private long bytesRead;
	private long contentLength;
	private long items;
	private long startTime = System.currentTimeMillis(); // ��ʼ�ϴ�ʱ�䣬���ڼ����ϴ�����

	public long getBytesRead() {
		return bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public long getItems() {
		return items;
	}

	public void setItems(long items) {
		this.items = items;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Progress() {
	}
}