package com.sys.dao;

import com.sys.entity.Score;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;

public abstract interface ScoreMapper
{
  public abstract ArrayList<Score> select(@Param("className") String paramString1, @Param("couName") String paramString2, @Param("stuYear") String paramString3);
  
  public abstract void saveOrUpdate(Score paramScore);
}
