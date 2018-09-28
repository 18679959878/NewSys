package com.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sys.dto.Result;
import com.sys.entity.ClassGrade;
import com.sys.service.ClassGradeService;

@Controller
@RequestMapping("/class")
public class ClassController {
	// public
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClassGradeService classGradeService;
	// ҳ���С
	private final int pageSize = 10;

	// ���ҳ��
	private int maxPage;

	/**
	 * �洢ǰ�˴����İ༶��Ϣ
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @param grade
	 *            �꼶
	 * @param education
	 *            ��/ר��
	 * @return ���ظ�ǰ�˵�json����
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<Object, Object>> addClass(String className, String grade, String education, String major) {

		// ����service��������
		return classGradeService.addClass(new ClassGrade(className, grade, education, major));

	}

	/**
	 * ��ѯ��ǰҳ����Ϣ
	 * 
	 * @param queryPage
	 *            ��ѯ��ҳ��
	 * @return ���ص�ҳ����Ϣ
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> classPageData(int queryPage) {
		return classGradeService.pageDClass(queryPage, pageSize);
	}

	/**
	 * ɾ���ð༶�����ݿ�����Ӧ�İ༶���Լ��ð༶���е�ѧ�������Ϣ�������ɼ�
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @return ���ظ�ǰ�˵�json����
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<ClassGrade> deleteClass(String className) {
		return classGradeService.deleteClass(className);
	}

	/**
	 * �޸�ǰ�˴����İ༶��Ϣ
	 * 
	 * @author ��С��
	 * @param className
	 *            �༶��
	 * @param grade
	 *            �꼶
	 * @param education
	 *            ��/ר��
	 * @return ��ǰ�˵�json����
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<ClassGrade> updateClass(String className, String grade, String education, String major) {
		return classGradeService.updateClass(className, grade, education, major);
	}

	@RequestMapping(value = "/cd/page", method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
	@ResponseBody
	public Result<HashMap<String, Object>> selectClassGradeOfDateStage(int classType, int stageType,int queryPage) {
		return classGradeService.selectClassGradeOfDateStage(classType, stageType, queryPage, pageSize);
	}
}
