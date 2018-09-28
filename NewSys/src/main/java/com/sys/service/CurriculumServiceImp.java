package com.sys.service;

import com.sys.dao.ClassGradeMapper;
import com.sys.dao.CurriculumMapper;
import com.sys.dao.TeacherMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Curriculum;
import java.util.ArrayList;
import java.util.HashMap;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurriculumServiceImp implements CurriculumService {
	@Autowired
	private CurriculumMapper curriculumMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private ClassGradeMapper classGradeMapper;

	@Transactional
	public Result<NullData> save(Curriculum curriculum) {
		try {
			if (this.teacherMapper.select(curriculum.getTeaAccount()) == null) {
				return new Result<NullData>("教师不存在");
			}
			if (this.classGradeMapper.select(curriculum.getClassName()) == null) {
				return new Result<NullData>("班级不存在");
			}
			this.curriculumMapper.save(curriculum);
			return new Result<NullData>(new NullData());
		} catch (Exception e) {
		}
		return new Result<NullData>("");
	}

	public Result<ArrayList<HashMap<String, String>>> selectCurriculumInfos() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		ArrayList<String> stuYears = this.curriculumMapper.selectStuYear();
		for (String string : stuYears) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("stuYear", string);
			map.put("stuYearNums", this.curriculumMapper.selectStuYearNums(string) + "");
			result.add(map);
		}
		return new Result<ArrayList<HashMap<String, String>>>(result);
	}

	public Result<HashMap<String, Object>> selectCurriculumInfo(String stuYear, int queryPage, int pageSize) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		int maxPage = (int) Math.ceil(this.curriculumMapper.selectStuYearNums(stuYear) / pageSize);

		map.put("maxPage", Integer.valueOf(maxPage));

		ArrayList<Curriculum> pageList = this.curriculumMapper.selectPageOfStuYear((queryPage - 1) * pageSize,
				queryPage * pageSize, stuYear);

		map.put("pageList", pageList);

		return new Result<HashMap<String,Object>>(map);
	}

	public Result<ArrayList<HashMap<String, String>>> selectCurriculumInfosOfTea(String teaAccount) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		ArrayList<String> stuYears = this.curriculumMapper.selectStuYearOfTea(teaAccount);
		for (String string : stuYears) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("stuYear", string);
			map.put("stuYearNums", this.curriculumMapper.selectStuYearOfTeaAccountNums(teaAccount, string)+"");
			result.add(map);
		}
		return new Result<ArrayList<HashMap<String,String>>>(result);
	}

	public Result<HashMap<String, Object>> selectCurriculumInfosOfTea(String stuYear, String teaAccount, int queryPage,
			int pageSize) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		int maxPage = (int) Math
				.ceil(this.curriculumMapper.selectStuYearOfTeaAccountNums(teaAccount, stuYear) / pageSize);

		map.put("maxPage", Integer.valueOf(maxPage));

		ArrayList<Curriculum> pageList = this.curriculumMapper.selectPageOfStuYearAndTea(stuYear, teaAccount,
				(queryPage - 1) * pageSize, queryPage * pageSize);

		map.put("pageList", pageList);

		return new Result<HashMap<String,Object>>(map);
	}
}
