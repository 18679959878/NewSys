package com.sys.entity;

public class Score
{
  private String stuId;
  private String couName;
  private String stuYear;
  private String score;
  private String className;
  private String stuName;
  private int count;
  
  public Score() {}
  
  public Score(String stuId, String couName, String stuYear, String score)
  {
    this.stuId = stuId;
    this.couName = couName;
    this.stuYear = stuYear;
    this.score = score;
  }
  
  public int getCount()
  {
    return this.count;
  }
  
  public void setCount(int count)
  {
    this.count = count;
  }
  
  public String getStuId()
  {
    return this.stuId;
  }
  
  public void setStuId(String stuId)
  {
    this.stuId = stuId;
  }
  
  public String getStuName()
  {
    return this.stuName;
  }
  
  public void setStuName(String stuName)
  {
    this.stuName = stuName;
  }
  
  public String getCouName()
  {
    return this.couName;
  }
  
  public void setCouName(String couName)
  {
    this.couName = couName;
  }
  
  public String getStuYear()
  {
    return this.stuYear;
  }
  
  public void setStuYear(String stuYear)
  {
    this.stuYear = stuYear;
  }
  
  public String getScore()
  {
    return this.score;
  }
  
  public void setScore(String score)
  {
    this.score = score;
  }
  
  public String getClassName()
  {
    return this.className;
  }
  
  public void setClassName(String className)
  {
    this.className = className;
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.couName == null ? 0 : this.couName.hashCode());
    result = 31 * result + (this.score == null ? 0 : this.score.hashCode());
    result = 31 * result + (this.stuId == null ? 0 : this.stuId.hashCode());
    result = 31 * result + (this.stuYear == null ? 0 : this.stuYear.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Score other = (Score)obj;
    if (this.couName == null)
    {
      if (other.couName != null) {
        return false;
      }
    }
    else if (!this.couName.equals(other.couName)) {
      return false;
    }
    if (this.score == null)
    {
      if (other.score != null) {
        return false;
      }
    }
    else if (!this.score.equals(other.score)) {
      return false;
    }
    if (this.stuId == null)
    {
      if (other.stuId != null) {
        return false;
      }
    }
    else if (!this.stuId.equals(other.stuId)) {
      return false;
    }
    if (this.stuYear == null)
    {
      if (other.stuYear != null) {
        return false;
      }
    }
    else if (!this.stuYear.equals(other.stuYear)) {
      return false;
    }
    return true;
  }
}
