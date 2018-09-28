package com.sys.dto;

public class Result<T> {
	//返回数据是否成功标志
	private boolean success;
	//返回成功时的数据
	private T data;
	private String error;

	public Result() {

	}

	// 成功时调用的构造器
	public Result(T data) {
		this.success = true;
		this.data = data;
	}

	// 失败时调用的构造器
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
