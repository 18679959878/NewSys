package com.sys.service;

import java.io.File;

public interface DownloadService {
	/**
	 * ����ģ��
	 * 
	 * @author ��С��
	 * @param fileName
	 *            ���ص��ļ���
	 * @return ���صĶ����ļ�
	 */
	public File templatePath(String fileName);

	/**
	 * ���ݰ༶������ѧ����Ϣ��
	 * 
	 * @param className
	 *            �༶����ΪallʱΪ��������ѧ��
	 * @return ���ɵ�excel�ļ�
	 */
	public File studentExcelPath(String className);
}
