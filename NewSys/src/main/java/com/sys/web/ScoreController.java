package com.sys.web;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.ScoreModel;
import com.sys.service.ScoreService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/score"})
public class ScoreController
{
  @Autowired
  private ScoreService scoreService;
  
  @RequestMapping(value={"/class/couName/select"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json; charset=utf-8"})
  @ResponseBody
  public Result<HashMap<Object, Object>> selectScores(String couName, String className, String stuYear)
  {
    return this.scoreService.selectScoresOfClassNameAndCouName(couName, className, stuYear);
  }
  
  @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"application/json; charset=utf-8"})
  @ResponseBody
  public Result<NullData> save(ScoreModel scores)
  {
    return this.scoreService.save(scores.getScores());
  }
}
