package com.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.poi.ss.formula.eval.ConcatEval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.w3c.dom.ls.LSException;

import com.mysql.cj.util.TimeUtil;
import com.sys.dao.DateStageMapper;
import com.sys.dao.DesignProcessMapper;
import com.sys.dao.ReportMapper;
import com.sys.dao.StudentMapper;
import com.sys.dao.TeacherMapper;
import com.sys.dao.WeekRecordMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.DateStage;
import com.sys.entity.DesignProcess;
import com.sys.entity.Report;
import com.sys.entity.Student;
import com.sys.entity.Teacher;
import com.sys.entity.WeekRecord;
import com.sys.utils.DateTimeUtils;

@Service
public class DateStageServiceImp implements DateStageService {
	@Autowired
	private DateStageMapper dateStageMapper;
	@Autowired
	private DesignProcessMapper designProcessMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired(required = false)
	private HttpSession session;
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private WeekRecordMapper weekRecordMapper;

	@Override
	@Transactional
	public Result<NullData> saveDateStage(DateStage paperStage) {
		try {
			dateStageMapper.save(paperStage);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("���ʧ��");
		}
	}

	@Override
	public Result<HashMap<String, Object>> dateStagePageData(int queryPage, int pageSize, int type) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		int maxPage = (int) Math.ceil((double) dateStageMapper.selectCount(type) / pageSize);
		// �����ҳ�����map
		map.put("maxPage", maxPage);
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<DateStage> pageList = (ArrayList<DateStage>) dateStageMapper.selectPage((queryPage - 1) * pageSize,
				queryPage * pageSize, type);
		Collections.sort(pageList);
		// ����ҳ�༶�б����map
		map.put("pageList", pageList);
		// ���صĽ��
		return new Result<HashMap<String, Object>>(map);
	}

	@Override
	public Result<NullData> deleteDateStage(String stageName, String grade, String education, int type) {
		try {
			int row = dateStageMapper.delete(stageName, grade, education, type);
			if (row > 0)
				return new Result<NullData>(new NullData());
			else
				return new Result<NullData>("ɾ��ʧ��");
		} catch (Exception e) {
			return new Result<NullData>("ɾ��ʧ��");
		}
	}

	@Override
	public Result<NullData> updateDateStage(DateStage dateStage) {
		try {
			dateStageMapper.update(dateStage);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("�޸�ʧ��");
		}
	}

	@Override
	public Result<HashMap<String, Object>> selectDesStageOfStu(String stuId) {
		List<DateStage> stageList;
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		List<Student> students = new ArrayList<Student>();
		if (teacher != null) {
			students = studentMapper.selectFieldworkStudents(teacher.getTeaAccount());
			stageList = dateStageMapper.selectStageOfStu(students.get(0).getStuId(), "%��ҵ���%");
		} else {
			students.add(studentMapper.select(stuId));
			stageList = dateStageMapper.selectStageOfStu(stuId, "%��ҵ���%");
		}
		for (DateStage dateStage : stageList) {
			if (dateStage.getStageName().equals("��ҵ��ƶ���")) {
				dateStage.setMaxPhotoNum(1);
			} else {
				dateStage.setMaxPhotoNum(4);
			}
			if (teacher == null) {
				DesignProcess designProcess = designProcessMapper.select(stuId, dateStage.getStageName());
				if (designProcess == null) {
					dateStage.setState("δ�ύ");
				} else {
					if (designProcess.getIsLock() == true) {
						dateStage.setState("������");
					} else {
						dateStage.setState("���ύ");
					}
				}
			} else {
				int handable = 0;
				int notProcessed = 0;
				for (Student student : students) {
					DesignProcess designProcess = designProcessMapper.select(student.getStuId(),
							dateStage.getStageName());
					if (designProcess != null) {
						if (!designProcess.getIsLock()) {
							notProcessed++;
						}
						handable++;
					}
				}
				dateStage.setHandable(handable);
				dateStage.setNotProcessed(notProcessed);
			}
			dateStage.setSd(DateTimeUtils.dateToStr(dateStage.getStartDate()));
			dateStage.setEd(DateTimeUtils.dateToStr(dateStage.getEndDate()));
		}
		Collections.sort(stageList);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("stageList", stageList);
		return new Result<HashMap<String, Object>>(map);
	}

	@Override
	public Result<DateStage> select(String stuId, String stageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<HashMap<String, Object>> selectReportStageOfStu(String stuId) {
		List<DateStage> stageList;
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		List<Student> students = new ArrayList<Student>();
		if (teacher != null) {
			students = studentMapper.selectFieldworkStudents(teacher.getTeaAccount());
			stageList = dateStageMapper.selectStageOfStu(students.get(0).getStuId(), "%˵����%");
		} else {
			students.add(studentMapper.select(stuId));
			stageList = dateStageMapper.selectStageOfStu(stuId, "%˵����%");
		}
		for (DateStage dateStage : stageList) {
			if (teacher == null) {
				Report report = reportMapper.select(stuId, dateStage.getStageName());
				if (report == null) {
					dateStage.setState("δ�ύ");
				} else {
					if (report.getIsLock() == true) {
						dateStage.setState("������");
					} else {
						dateStage.setState("���ύ");
					}
				}
			} else {
				int handable = 0;
				int notProcessed = 0;
				for (Student student : students) {
					Report report = reportMapper.select(student.getStuId(), dateStage.getStageName());
					if (report != null) {
						if (!report.getIsLock()) {
							notProcessed++;
						}
						handable++;
					}
				}
				dateStage.setHandable(handable);
				dateStage.setNotProcessed(notProcessed);
			}
			dateStage.setSd(DateTimeUtils.dateToStr(dateStage.getStartDate()));
			dateStage.setEd(DateTimeUtils.dateToStr(dateStage.getEndDate()));
		}
		Collections.sort(stageList);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("stageList", stageList);
		return new Result<HashMap<String, Object>>(map);
	}

	@Override
	public Result<HashMap<String, Object>> dateStagePageData(String stuId, int queryPage, int pageSize, int type) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		int maxPage = (int) Math.ceil((double) dateStageMapper.selectCount(type) / pageSize);
		// �����ҳ�����map
		map.put("maxPage", maxPage);
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<DateStage> pageList = (ArrayList<DateStage>) dateStageMapper.selectPage((queryPage - 1) * pageSize,
				queryPage * pageSize, type);
		ArrayList<DateStage> r_pageList = new ArrayList<DateStage>();
		Collections.sort(pageList);
		for (DateStage dateStage : pageList) {
			WeekRecord weekRecord = weekRecordMapper.select(stuId, dateStage.getStageName());
			if (weekRecord != null) {
				if (weekRecord.getIsLock()) {
					dateStage.setState("������");
				} else {
					dateStage.setState("���ύ");
				}
			} else {
				dateStage.setState("δ�ύ");
			}
			r_pageList.add(dateStage);
		}
		// ����ҳ�༶�б����map
		map.put("pageList", r_pageList);
		// ���صĽ��
		return new Result<HashMap<String, Object>>(map);
	}

}
