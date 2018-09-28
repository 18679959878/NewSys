package com.sys.service;

import java.io.File;

public interface DownloadService {
	/**
	 * 下载模板
	 * 
	 * @author 金小瑶
	 * @param fileName
	 *            下载的文件名
	 * @return 下载的对象文件
	 */
	public File templatePath(String fileName);

	/**
	 * 根据班级名生成学生信息表
	 * 
	 * @param className
	 *            班级名，为all时为下载所有学生
	 * @return 生成的excel文件
	 */
	public File studentExcelPath(String className);
}
