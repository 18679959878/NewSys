package com.sys.service;

import com.sys.dto.NullData;
import com.sys.dto.Result;
import com.sys.entity.Curriculum;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface CurriculumService
{
  public abstract Result<NullData> save(Curriculum paramCurriculum);
  
  public abstract Result<ArrayList<HashMap<String, String>>> selectCurriculumInfos();
  
  public abstract Result<HashMap<String, Object>> selectCurriculumInfo(String paramString, int paramInt1, int paramInt2);
  
  public abstract Result<ArrayList<HashMap<String, String>>> selectCurriculumInfosOfTea(String paramString);
  
  public abstract Result<HashMap<String, Object>> selectCurriculumInfosOfTea(String paramString1, String paramString2, int paramInt1, int paramInt2);
}