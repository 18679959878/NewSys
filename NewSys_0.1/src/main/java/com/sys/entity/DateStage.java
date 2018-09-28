package com.sys.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DateStage implements Comparable<DateStage> {
	private String stageName;
	private String grade;
	private String education;
	private Date startDate;
	private Date endDate;
	private int maxPhotoNum;
	private String state;
	private String sd;
	private String ed;

	private int handable;
	private int notProcessed;
	// 日期设置，0代表实习日期设置，1代表毕业设计日期设置
	@JSONField(serialize = false)
	private int type;
	public static final int FIELD_WORK_TYPE = 0;
	public static final int WORKS_TYPE = 1;

	public DateStage() {
		super();
	}

	public DateStage(String stageName, String grade, String education, Date startDate, Date endDate, int type) {
		super();
		this.stageName = stageName;
		this.grade = grade;
		this.education = education;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
	}

	public String getStageName() {
		return stageName;
	}

	public int getMaxPhotoNum() {
		return maxPhotoNum;
	}

	public void setMaxPhotoNum(int maxPhotoNum) {
		this.maxPhotoNum = maxPhotoNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSd() {
		return sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	public String getEd() {
		return ed;
	}

	public void setEd(String ed) {
		this.ed = ed;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getHandable() {
		return handable;
	}

	public void setHandable(int handable) {
		this.handable = handable;
	}

	public int getNotProcessed() {
		return notProcessed;
	}

	public void setNotProcessed(int notProcessed) {
		this.notProcessed = notProcessed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ed == null) ? 0 : ed.hashCode());
		result = prime * result + ((education == null) ? 0 : education.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + handable;
		result = prime * result + maxPhotoNum;
		result = prime * result + notProcessed;
		result = prime * result + ((sd == null) ? 0 : sd.hashCode());
		result = prime * result + ((stageName == null) ? 0 : stageName.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateStage other = (DateStage) obj;
		if (ed == null) {
			if (other.ed != null)
				return false;
		} else if (!ed.equals(other.ed))
			return false;
		if (education == null) {
			if (other.education != null)
				return false;
		} else if (!education.equals(other.education))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (handable != other.handable)
			return false;
		if (maxPhotoNum != other.maxPhotoNum)
			return false;
		if (notProcessed != other.notProcessed)
			return false;
		if (sd == null) {
			if (other.sd != null)
				return false;
		} else if (!sd.equals(other.sd))
			return false;
		if (stageName == null) {
			if (other.stageName != null)
				return false;
		} else if (!stageName.equals(other.stageName))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public int compareTo(DateStage o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
