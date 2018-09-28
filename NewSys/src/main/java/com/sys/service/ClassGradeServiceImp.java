package com.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.ClassGradeMapper;
import com.sys.dto.Result;
import com.sys.entity.ClassGrade;

@Service
public class ClassGradeServiceImp implements ClassGradeService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClassGradeMapper classGradeMapper;
	@Autowired(required = false)
	// @Autowired
	private HttpSession session;

	@Override
	@Transactional
	public Result<Map<Object, Object>> addClass(ClassGrade classGrade) {
		ClassGrade sel_classGrade = classGradeMapper.select(classGrade.getClassName());
		if (sel_classGrade == null) {
			if (classGrade.getEducation().equals("") || classGrade.getGrade().equals("")
					|| classGrade.getGrade() == null || classGrade.getEducation() == null) {
				return new Result<Map<Object, Object>>("���ʧ��!!δѡ��ר�ƻ�û��ѡ���꼶");
			} else {
				classGrade.setFiledWorkTea("��");
				classGradeMapper.save(classGrade);
				return new Result<Map<Object, Object>>(new HashedMap());
			}
		} else {
			return new Result<Map<Object, Object>>("���ʧ��!!���ݿ��Ѵ��ڸð༶��");
		}
	}

	@Override
	@Transactional
	public Result<HashMap<String, Object>> pageDClass(int queryPage, final int pageSize) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		int maxPage = (int) Math.ceil((double) classGradeMapper.selectCount(ClassGrade.OLD_CLASS) / pageSize);
		logger.debug(classGradeMapper.selectCount(ClassGrade.OLD_CLASS) + "");
		// �����ҳ�����map
		map.put("maxPage", maxPage);
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<ClassGrade> pageList = (ArrayList<ClassGrade>) classGradeMapper.selectPage((queryPage - 1) * pageSize,
				queryPage * pageSize, ClassGrade.OLD_CLASS);
		// ����ҳ�༶�б����map
		map.put("pageList", pageList);
		// ���صĽ��
		return new Result<HashMap<String, Object>>(map);
	}

	@Override
	public Result<ClassGrade> deleteClass(String className) {
		// admin�û����в���Ȩ��
		if (session.getAttribute("admin") != null) {
			// �жϰ༶���Ƿ�Ϊ��,��Ϊ��ִ��ɾ����Ϊ���򷵻ظ�������������Ϣ
			if (className != null) {
				// ִ��ɾ��
				int row = classGradeMapper.deleteClass(className);
				// �����Ӱ������Ϊ0���򷵻���Ϣ���ʧ��
				if (row == 0) {
					return new Result<ClassGrade>("ɾ��ʧ��!�޷�ʶ��༶��");
				} else {
					// ����ɾ���ɹ�����Ϣ
					return new Result<ClassGrade>(new ClassGrade());
				}
			} else {
				return new Result<ClassGrade>("ɾ��ʧ��!!��������������Ϊ��");
			}
		} else {
			return new Result<ClassGrade>("�޲���Ȩ��");
		}
	}

	@Override
	@Transactional
	public Result<ClassGrade> updateClass(String className, String grade, String education, String major) {
		// �ж�ǰ���Ƿ�����ֵ
		if (grade == null || grade.equals("") || education == null || education.equals("")) {
			return new Result<ClassGrade>("�޸�ʧ�ܣ����꼶���߱�/ר��Ϊ��");
		} else {
			// �޸����ݿ�����
			classGradeMapper.updateClass(new ClassGrade(className, grade, education, major));
			// ���سɹ�
			return new Result<ClassGrade>(new ClassGrade());
		}
	}

	@Override
	public Result<HashMap<String, Object>> selectClassGradeOfDateStage(int classType, int stageType, int queryPage,
			int pageSize) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		int maxPage = (int) Math
				.ceil((double) classGradeMapper.selectCountOfDateStage(classType, stageType) / pageSize);
		logger.debug(classGradeMapper.selectCount(ClassGrade.OLD_CLASS) + "");
		// �����ҳ�����map
		map.put("maxPage", maxPage);
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<ClassGrade> pageList = (ArrayList<ClassGrade>) classGradeMapper.selectClassGradePageOfDateStage(
				classType, stageType, (queryPage - 1) * pageSize, queryPage * pageSize);
		// ����ҳ�༶�б����map
		map.put("pageList", pageList);
		// ���صĽ��
		return new Result<HashMap<String, Object>>(map);
	}

}
