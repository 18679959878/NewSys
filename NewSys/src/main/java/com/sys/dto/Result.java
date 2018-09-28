package com.sys.dto;

public class Result<T> {
	//���������Ƿ�ɹ���־
	private boolean success;
	//���سɹ�ʱ������
	private T data;
	private String error;

	public Result() {

	}

	// �ɹ�ʱ���õĹ�����
	public Result(T data) {
		this.success = true;
		this.data = data;
	}

	// ʧ��ʱ���õĹ�����
	public Result(String error) {
		this.success = false;
		this.error = error;

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
