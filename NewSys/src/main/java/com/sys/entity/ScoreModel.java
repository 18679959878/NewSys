package com.sys.entity;

import java.util.List;

public class ScoreModel
{
  private List<Score> scores;
  
  public ScoreModel() {}
  
  public ScoreModel(List<Score> scores)
  {
    this.scores = scores;
  }
  
  public List<Score> getScores()
  {
    return this.scores;
  }
  
  public void setScores(List<Score> scores)
  {
    this.scores = scores;
  }
}
