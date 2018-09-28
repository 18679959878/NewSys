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
			return new Result<Teacher>("添加失败，请查看该教师是否已经被添加");
		}
	}

	@Override
	public Result<HashMap<String, Object>> pageTeacher(int pageSize, int queryPage) {
		// 返回的正文数据
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最大页码,进一法
		int maxPage = (int) Math.ceil((double) teacherMapper.selectCount() / pageSize);
		// 将最大页码放入map
		map.put("maxPage", maxPage);
		// 从数据库查询此页班级列表
		ArrayList<Teacher> pageList = (ArrayList<Teacher>) teacherMapper.selectPage((queryPage - 1) * pageSize,
				queryPage * pageSize);
		// 将本页班级列表放入map
		map.put("pageList", pageList);
		// 返回的结果
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
			return new Result<NullData>("刪除失敗");
		}
	}

	@Override
	@Transactional
	public Result<NullData> update(Teacher teacher) {
		try {
			teacherMapper.update(teacher);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
			return new Result<NullData>("修改失败");
		}
	}

	@Override
	@Transactional
	public Result<NullData> importTeacherExcel(File file) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		ArrayList<Teacher> teachers = (ArrayList<Teacher>) ExcelProcess.importExcel(file,
				ExcelProcess.EXCEL_TYPE_TEACHER);
		// 如果为空，构建错误结果对象返回
		if (teachers == null) {
			return new Result<NullData>("文件解析失败，请确认是否为标准模板，并仔细阅读表格模板注意行内容");
		}
		try {
			// 批量导入
			teacherMapper.insertOrupdateBatch(teachers);
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("解析失败");
		}
		return new Result<NullData>(new NullData());
	}

	@Override
	public Result<ArrayList<Teacher>> allTeacher() {
		try {
			ArrayList<Teacher> list = teacherMapper.selectAll();
			return new Result<ArrayList<Teacher>>(list);
		} catch (Exception e) {
			return new Result<ArrayList<Teacher>>("查询失败");
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
					return new Result<NullData>("密码错误");
				}
			} else {
				return new Result<NullData>("账号不存在");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new Result<NullData>("系统故障，无法登陆");
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
							"添加失败！！学生" + student.getStuName() + "由老师" + student.getFieldworkTea() + "指导，请先联系该老师删除该学生");
				}
			} else {
				return new Result<NullData>("学生不存在");
			}
		} catch (Exception e) {
			return new Result<NullData>("添加失败");
		}
	}

	@Override
	public Result<Map<String, Object>> selectFiledStuPage(int queryPage, int pageSize) {
		// 返回的正文数据
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最大页码,进一法
		String fieldworkTea = ((Teacher) session.getAttribute("teacher")).getTeaAccount();
		int maxPage = (int) Math.ceil((double) studentMapper.selectFiledStuCount(fieldworkTea) / pageSize);
		// 将最大页码放入map
		map.put("maxPage", maxPage);
		// 从数据库查询此页班级列表
		ArrayList<Student> pageList = studentMapper.selectFiledStuPage(fieldworkTea, (queryPage - 1) * pageSize,
				queryPage * pageSize);
		for (Student student : pageList) {
			String stuId = student.getStuId();
			FinalProjectTitel finalProjectTitel = finalProjectTitelMapper.select(stuId);
			FinalProjectTask finalProjectTask = finalProjectTaskMapper.select(stuId);
			OpenReport openReport = openReportMapper.select(stuId);
			if (finalProjectTitel != null) {
				if (finalProjectTitel.getIsSumbit()) {
					student.setFtpState("已锁定");
				} else {
					student.setFtpState("已提交");
				}
				student.setTitelCn(finalProjectTitel.getTitelCn());
			} else {
				student.setTitelCn("");
				student.setFtpState("未提交");
			}
			if (finalProjectTask != null) {
				student.setFtpTaskState("已提交");
			} else {
				student.setFtpTaskState("未提交");
			}
			if (openReport != null) {
				if (openReport.getIsLock() == true) {
					student.setOrState("已锁定");
					student.setOrScore(openReport.getScore());
				} else {
					student.setOrState("已提交");
				}
				student.setOrScore(openReport.getScore());
			} else {
				student.setOrState("未提交");
			}
		}
		// 将本页班级列表放入map
		map.put("pageList", pageList);
		// 返回的结果
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
			return new Result<NullData>("删除失败");
		}
	}

	@Override
	public Result<Map<String, Object>> selectFiledStuPage(int queryPage, int pageSize, String stageName) {
		// 返回的正文数据
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最大页码,进一法
		String fieldworkTea = ((Teacher) session.getAttribute("teacher")).getTeaAccount();
		int maxPage = (int) Math.ceil((double) studentMapper.selectFiledStuCount(fieldworkTea) / pageSize);
		// 将最大页码放入map
		map.put("maxPage", maxPage);
		// 从数据库查询此页班级列表
		ArrayList<Student> pageList = studentMapper.selectFiledStuPage(fieldworkTea, (queryPage - 1) * pageSize,
				queryPage * pageSize);
		for (Student student : pageList) {
			DesignProcess designProcess = designProcessMapper.select(student.getStuId(), stageName);
			Report report = reportMapper.select(student.getStuId(), stageName);
			Comment comment = commentMapper.select(student.getStuId());
			if (designProcess != null) {
				student.setDeStageName(stageName);
				if (designProcess.getIsLock()) {
					student.setDeStageState("已锁定");
				} else {
					student.setDeStageState("已提交");
				}
				student.setDeScore(designProcess.getScore());
			} else {
				student.setDeStageState("未提交");
			}
			if (report != null) {
				student.setReStageName(stageName);
				if (report.getIsLock()) {
					student.setReStageState("已锁定");
				} else {
					student.setReStageState("已提交");
				}
				student.setReScore(report.getScore());
			} else {
				student.setReStageState("未提交");
			}
			if (comment != null) {
				student.setCoStageState("已提交");
			} else {
				student.setCoStageState("未提交");
			}
		}
		// 将本页班级列表放入map
		map.put("pageList", pageList);
		// 返回的结果
		return new Result<Map<String, Object>>(map);
	}

	@Override
	public Result<Map<String, Object>> selectWeekRecordStuPage(String stageName) {
		// 返回的正文数据
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 最大页码,进一法
		String workTea = ((Teacher) session.getAttribute("teacher")).getTeaAccount();
		// 从数据库查询此页班级列表
		ArrayList<Student> pageList = studentMapper.selectworkTeaStudents(workTea);
		// 缓存已填写学生学号
		List<String> stuIdCaches = new ArrayList<String>();
		for (Student student : pageList) {
			WeekRecord weekRecord = weekRecordMapper.select(student.getStuId(), stageName);
			student.setWrStageName(stageName);
			if (weekRecord != null) {
				stuIdCaches.add(student.getStuId());
				if (weekRecord.getGuidance() != null && !weekRecord.getGuidance().equals("")) {
					if (weekRecord.getIsLock()) {
						student.setWrStageState("已添加评语，已锁定");
					} else {
						student.setWrStageState("已添加评语，未锁定");
					}
				} else {
					student.setWrStageState("学生已提交，未添加评语");
				}
				student.setWrScore(weekRecord.getScore());
			} else {
				student.setWrStageState("学生未提交");
			}
		}
		// 将已提交学号缓存到session
		session.setAttribute("stuIdCaches", stuIdCaches);
		// 将本页班级列表放入map
		map.put("pageList", pageList);
		// 返回的结果
		return new Result<Map<String, Object>>(map);
	}
}
