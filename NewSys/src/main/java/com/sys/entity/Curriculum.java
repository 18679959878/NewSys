package com.sys.entity;

public class Curriculum
{
  private String teaAccount;
  private String couName;
  private String className;
  private String stuYear;
  private boolean isLock;
  private boolean isSub;
  
  public Curriculum() {}
  
  public Curriculum(String teaAccount, String couName, String className, String stuYear)
  {
    this.teaAccount = teaAccount;
    this.couName = couName;
    this.className = className;
    this.stuYear = stuYear;
  }
  
  public String getTeaAccount()
  {
    return this.teaAccount;
  }
  
  public void setTeaAccount(String teaAccount)
  {
    this.teaAccount = teaAccount;
  }
  
  public String getCouName()
  {
    return this.couName;
  }
  
  public void setCouName(String couName)
  {
    this.couName = couName;
  }
  
  public String getClassName()
  {
    return this.className;
  }
  
  public void setClassName(String className)
  {
    this.className = className;
  }
  
  public String getStuYear()
  {
    return this.stuYear;
  }
  
  public void setStuYear(String stuYear)
  {
    this.stuYear = stuYear;
  }
  
  public boolean getIsLock()
  {
    return this.isLock;
  }
  
  public void setIsLock(boolean isLock)
  {
    this.isLock = isLock;
  }
  
  public boolean getIsSub()
  {
    return this.isSub;
  }
  
  public void setIsSub(boolean isSub)
  {
    this.isSub = isSub;
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.className == null ? 0 : this.className.hashCode());
    result = 31 * result + (this.couName == null ? 0 : this.couName.hashCode());
    result = 31 * result + (this.stuYear == null ? 0 : this.stuYear.hashCode());
    result = 31 * result + (this.teaAccount == null ? 0 : this.teaAccount.hashCode());
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
    Curriculum other = (Curriculum)obj;
    if (this.className == null)
    {
      if (other.className != null) {
        return false;
      }
    }
    else if (!this.className.equals(other.className)) {
      return false;
    }
    if (this.couName == null)
    {
      if (other.couName != null) {
        return false;
      }
    }
    else if (!this.couName.equals(other.couName)) {
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
    if (this.teaAccount == null)
    {
      if (other.teaAccount != null) {
        return false;
      }
    }
    else if (!this.teaAccount.equals(other.teaAccount)) {
      return false;
    }
    return true;
  }
}