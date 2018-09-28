package com.sys.web;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Curriculum;
import com.sys.entity.Teacher;
import com.sys.service.CurriculumService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping({"/curriculum"})
@Controller
public class CurriculumController
{
  @Autowired
  private CurriculumService curriculumService;
  private final int pageSize = 10;
  @Autowired
  private HttpSession session;
  
  @RequestMapping(value={"/save"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json; charset=utf-8"})
  @ResponseBody
  public Result<NullData> save(String couName, String teaAccount, String className, String stuYear)
  {
    System.out.println(className);
    return this.curriculumService.save(new Curriculum(teaAccount, couName, className, stuYear));
  }
  
  @RequestMapping(value={"/stuYear/infos"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json; charset=utf-8"})
  @ResponseBody
  public Result<ArrayList<HashMap<String, String>>> selectCurriculumInfos()
  {
    return this.curriculumService.selectCurriculumInfos();
  }
  
  @RequestMapping(value={"/stuYear/t/infos"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json; charset=utf-8"})
  @ResponseBody
  public Result<ArrayList<HashMap<String, String>>> selectCurriculumInfosOfTea()
  {
    Teacher teacher = (Teacher)this.session.getAttribute("teacher");
    return this.curriculumService.selectCurriculumInfosOfTea(teacher.getTeaAccount());
  }
  
  @RequestMapping(value={"/stuYear/info"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json; charset=utf-8"})
  @ResponseBody
  public Result<HashMap<String, Object>> selectCurriculumInfo(String stuYear, int queryPage)
  {
    return this.curriculumService.selectCurriculumInfo(stuYear, queryPage, 10);
  }
  
  @RequestMapping(value={"/stuYear/t/info"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json; charset=utf-8"})
  @ResponseBody
  public Result<HashMap<String, Object>> selectCurriculumInfoOfTea(String stuYear, int queryPage)
  {
    Teacher teacher = (Teacher)this.session.getAttribute("teacher");
    return this.curriculumService.selectCurriculumInfosOfTea(stuYear, teacher.getTeaAccount(), queryPage, 10);
  }
}
