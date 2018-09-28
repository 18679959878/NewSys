package com.sys.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Cache;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.dao.CommentMapper;
import com.sys.dao.DesignProcessMapper;
import com.sys.dao.FinalProjectTaskMapper;
import com.sys.dao.FinalProjectTitelMapper;
import com.sys.dao.OpenReportMapper;
import com.sys.dao.ReportMapper;
import com.sys.dao.StudentMapper;
import com.sys.dao.TeacherMapper;
import com.sys.dao.WeekRecordMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.ClassGrade;
import com.sys.entity.Comment;
import com.sys.entity.DesignProcess;
import com.sys.entity.FinalProjectTask;
import com.sys.entity.FinalProjectTitel;
import com.sys.entity.OpenReport;
import com.sys.entity.Report;
import com.sys.entity.Student;
import com.sys.entity.Teacher;
import com.sys.entity.WeekRecord;
import com.sys.utils.ExcelProcess;

@Service
public class TeacherServiceImp implements TeacherService {
	@Autowired
	private WeekRecordMapper weekRecordMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private FinalProjectTitelMapper finalProjectTitelMapper;
	@Autowired
	private FinalProjectTaskMapper finalProjectTaskMapper;
	@Autowired
	private OpenReportMapper openReportMapper;
	@Autowired
	private DesignProcessMapper designProcessMapper;
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired(required = false)
	// @Autowired
	private HttpSession session;

	@Override
	@Transactional
	public Result<Teacher> save(Teacher teacher) {
		try {
			teacherMapper.save(teacher);
			return new Result<Teacher>(teacher);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<Teacher>("���ʧ�ܣ���鿴�ý�ʦ�Ƿ��Ѿ������");
		}
	}

	@Override
	public Result<HashMap<String, Object>> pageTeacher(int pageSize, int queryPage) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		int maxPage = (int) Math.ceil((double) teacherMapper.selectCount() / pageSize);
		// �����ҳ�����map
		map.put("maxPage", maxPage);
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<Teacher> pageList = (ArrayList<Teacher>) teacherMapper.selectPage((queryPage - 1) * pageSize,
				queryPage * pageSize);
		// ����ҳ�༶�б����map
		map.put("pageList", pageList);
		// ���صĽ��
		return new Result<HashMap<String, Object>>(map);
	}

	@Override
	@Transactional
	public Result<NullData> deleteTeacher(String teaAccount) {
		// TODO Auto-generated method stub
		try {
			teacherMapper.delete(teaAccount);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("�h��ʧ��");
		}
	}

	@Override
	@Transactional
	public Result<NullData> update(Teacher teacher) {
		try {
			teacherMapper.update(teacher);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("�޸�ʧ��");
		}
	}

	@Override
	@Transactional
	public Result<NullData> importTeacherExcel(File file) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		ArrayList<Teacher> teachers = (ArrayList<Teacher>) ExcelProcess.importExcel(file,
				ExcelProcess.EXCEL_TYPE_TEACHER);
		// ���Ϊ�գ��������������󷵻�
		if (teachers == null) {
			return new Result<NullData>("�ļ�����ʧ�ܣ���ȷ���Ƿ�Ϊ��׼ģ�壬����ϸ�Ķ����ģ��ע��������");
		}
		try {
			// ��������
			teacherMapper.insertOrupdateBatch(teachers);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("����ʧ��");
		}
		return new Result<NullData>(new NullData());
	}

	@Override
	public Result<ArrayList<Teacher>> allTeacher() {
		try {
			ArrayList<Teacher> list = teacherMapper.selectAll();
			return new Result<ArrayList<Teacher>>(list);
		} catch (Exception e) {
			return new Result<ArrayList<Teacher>>("��ѯʧ��");
		}
	}

	@Override
	public Result<NullData> login(String teaAccount, String password) {
		try {
			Teacher teacher = teacherMapper.select(teaAccount);
			if (teacher != null) {
				if (teacher.getPassword().equals(password)) {
					session.setAttribute("teacher", teacher);
					return new Result<NullData>(new NullData());
				} else {
					return new Result<NullData>("�������");
				}
			} else {
				return new Result<NullData>("�˺Ų�����");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("ϵͳ���ϣ��޷���½");
		} finally {
			session.removeAttribute("student");
			session.removeAttribute("admin");
		}
	}

	@Override
	@Transactional
	public Result<NullData> addFiledStudents(String stuId) {
		try {
			Student student = studentMapper.select(stuId);
			if (student != null) {
				System.out.println(student.getFieldworkTea() + "  " + student.getFieldworkTea() == null);
				if (student.getFieldworkTea() == null || student.getFieldworkTea().trim().equals("")
						|| student.getFieldworkTea().equals("null")) {
					student.setFieldworkTea(((Teacher) session.getAttribute("teacher")).getTeaAccount());
					studentMapper.update(student);
					return new Result<NullData>(new NullData());
				} else {
					return new Result<NullData>(
							"���ʧ�ܣ���ѧ��" + student.getStuName() + "����ʦ" + student.getFieldworkTea() + "ָ����������ϵ����ʦɾ����ѧ��");
				}
			} else {
				return new Result<NullData>("ѧ��������");
			}
		} catch (Exception e) {
			return new Result<NullData>("���ʧ��");
		}
	}

	@Override
	public Result<Map<String, Object>> selectFiledStuPage(int queryPage, int pageSize) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		String fieldworkTea = ((Teacher) session.getAttribute("teacher")).getTeaAccount();
		int maxPage = (int) Math.ceil((double) studentMapper.selectFiledStuCount(fieldworkTea) / pageSize);
		// �����ҳ�����map
		map.put("maxPage", maxPage);
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<Student> pageList = studentMapper.selectFiledStuPage(fieldworkTea, (queryPage - 1) * pageSize,
				queryPage * pageSize);
		for (Student student : pageList) {
			String stuId = student.getStuId();
			FinalProjectTitel finalProjectTitel = finalProjectTitelMapper.select(stuId);
			FinalProjectTask finalProjectTask = finalProjectTaskMapper.select(stuId);
			OpenReport openReport = openReportMapper.select(stuId);
			if (finalProjectTitel != null) {
				if (finalProjectTitel.getIsSumbit()) {
					student.setFtpState("������");
				} else {
					student.setFtpState("���ύ");
				}
				student.setTitelCn(finalProjectTitel.getTitelCn());
			} else {
				student.setTitelCn("");
				student.setFtpState("δ�ύ");
			}
			if (finalProjectTask != null) {
				student.setFtpTaskState("���ύ");
			} else {
				student.setFtpTaskState("δ�ύ");
			}
			if (openReport != null) {
				if (openReport.getIsLock() == true) {
					student.setOrState("������");
					student.setOrScore(openReport.getScore());
				} else {
					student.setOrState("���ύ");
				}
				student.setOrScore(openReport.getScore());
			} else {
				student.setOrState("δ�ύ");
			}
		}
		// ����ҳ�༶�б����map
		map.put("pageList", pageList);
		// ���صĽ��
		return new Result<Map<String, Object>>(map);
	}

	@Override
	@Transactional
	public Result<NullData> deleteFieldStu(String stuId) {
		try {
			Student student = studentMapper.select(stuId);
			student.setFieldworkTea(null);
			studentMapper.update(student);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("ɾ��ʧ��");
		}
	}

	@Override
	public Result<Map<String, Object>> selectFiledStuPage(int queryPage, int pageSize, String stageName) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		String fieldworkTea = ((Teacher) session.getAttribute("teacher")).getTeaAccount();
		int maxPage = (int) Math.ceil((double) studentMapper.selectFiledStuCount(fieldworkTea) / pageSize);
		// �����ҳ�����map
		map.put("maxPage", maxPage);
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<Student> pageList = studentMapper.selectFiledStuPage(fieldworkTea, (queryPage - 1) * pageSize,
				queryPage * pageSize);
		for (Student student : pageList) {
			DesignProcess designProcess = designProcessMapper.select(student.getStuId(), stageName);
			Report report = reportMapper.select(student.getStuId(), stageName);
			Comment comment = commentMapper.select(student.getStuId());
			if (designProcess != null) {
				student.setDeStageName(stageName);
				if (designProcess.getIsLock()) {
					student.setDeStageState("������");
				} else {
					student.setDeStageState("���ύ");
				}
				student.setDeScore(designProcess.getScore());
			} else {
				student.setDeStageState("δ�ύ");
			}
			if (report != null) {
				student.setReStageName(stageName);
				if (report.getIsLock()) {
					student.setReStageState("������");
				} else {
					student.setReStageState("���ύ");
				}
				student.setReScore(report.getScore());
			} else {
				student.setReStageState("δ�ύ");
			}
			if (comment != null) {
				student.setCoStageState("���ύ");
			} else {
				student.setCoStageState("δ�ύ");
			}
		}
		// ����ҳ�༶�б����map
		map.put("pageList", pageList);
		// ���صĽ��
		return new Result<Map<String, Object>>(map);
	}

	@Override
	public Result<Map<String, Object>> selectWeekRecordStuPage(String stageName) {
		// ���ص���������
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ���ҳ��,��һ��
		String workTea = ((Teacher) session.getAttribute("teacher")).getTeaAccount();
		// �����ݿ��ѯ��ҳ�༶�б�
		ArrayList<Student> pageList = studentMapper.selectworkTeaStudents(workTea);
		// ��������дѧ��ѧ��
		List<String> stuIdCaches = new ArrayList<String>();
		for (Student student : pageList) {
			WeekRecord weekRecord = weekRecordMapper.select(student.getStuId(), stageName);
			student.setWrStageName(stageName);
			if (weekRecord != null) {
				stuIdCaches.add(student.getStuId());
				if (weekRecord.getGuidance() != null && !weekRecord.getGuidance().equals("")) {
					if (weekRecord.getIsLock()) {
						student.setWrStageState("��������������");
					} else {
						student.setWrStageState("��������δ����");
					}
				} else {
					student.setWrStageState("ѧ�����ύ��δ�������");
				}
				student.setWrScore(weekRecord.getScore());
			} else {
				student.setWrStageState("ѧ��δ�ύ");
			}
		}
		// �����ύѧ�Ż��浽session
		session.setAttribute("stuIdCaches", stuIdCaches);
		// ����ҳ�༶�б����map
		map.put("pageList", pageList);
		// ���صĽ��
		return new Result<Map<String, Object>>(map);
	}
}
