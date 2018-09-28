package com.sys.service;

import com.sys.dao.CurriculumMapper;
import com.sys.dao.ScoreMapper;
import com.sys.dao.StudentMapper;
import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Curriculum;
import com.sys.entity.Score;
import com.sys.entity.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImp
  implements ScoreService
{
  @Autowired
  private CurriculumMapper curriculumMapper;
  @Autowired
  private StudentMapper studentMapper;
  @Autowired
  private ScoreMapper scoreMapper;
  
  public Result<HashMap<Object, Object>> selectScoresOfClassNameAndCouName(String couName, String className, String stuYear)
  {
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    Curriculum curriculum = this.curriculumMapper.select(couName, className, stuYear);
    if (curriculum != null)
    {
      ArrayList<Score> scores = new ArrayList<Score>();
      if (curriculum.getIsSub())
      {
        scores = this.scoreMapper.select(className, couName, stuYear);
        for (Score score : scores)
        {
          score.setStuName(this.studentMapper.select(score.getStuId()).getStuName());
          score.setClassName(className);
        }
      }
      else
      {
        ArrayList<Student> students = this.studentMapper.selectClassStudents(className);
        for (Student student : students)
        {
          Score score = new Score(student.getStuId(), couName, stuYear, "");
          score.setClassName(className);
          score.setStuName(student.getStuName());
          scores.add(score);
        }
      }
      map.put("scores", scores);
    }
    else
    {
      return new Result<HashMap<Object,Object>>("查询失败");
    }
    map.put("isLock", Boolean.valueOf(curriculum.getIsLock()));
    return new Result<HashMap<Object,Object>>(map);
  }
  
  @Transactional
  public Result<NullData> save(List<Score> scores)
  {
    Curriculum curriculum = this.curriculumMapper.select(((Score)scores.get(0)).getCouName(), 
      this.studentMapper.select(((Score)scores.get(0)).getStuId()).getClassName(), ((Score)scores.get(0)).getStuYear());
    if (!curriculum.getIsSub())
    {
      curriculum.setIsSub(true);
      this.curriculumMapper.update(curriculum);
    }
    for (Score score : scores)
    {
      if (score.getScore() == null) {
        score.setScore("");
      }
      this.scoreMapper.saveOrUpdate(score);
    }
    return new Result<NullData>(new NullData());
  }
}