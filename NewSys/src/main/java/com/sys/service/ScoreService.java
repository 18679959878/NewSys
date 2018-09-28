package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Score;
import java.util.HashMap;
import java.util.List;

public abstract interface ScoreService
{
  public abstract Result<HashMap<Object, Object>> selectScoresOfClassNameAndCouName(String paramString1, String paramString2, String paramString3);
  
  public abstract Result<NullData> save(List<Score> paramList);
}
