package com.sys.entity;

public class FinalProjectTitel {
	private String stuId;
	private String titelCn;
	private String titelEn;
	private String stuName;
	private String majorName;
	private String division;
	private String academy;
	private String teaNameAndTitel;
	private String state;
	private boolean isSumbit;
	private int score;
	// mybatis查询统计属性
	private int count;

	public FinalProjectTitel(String stuId, String titelCn, String titelEn, String academy, String teaNameAndTitel,
			int score) {
		super();
		this.stuId = stuId;
		this.titelCn = titelCn;
		this.titelEn = titelEn;
		this.academy = academy;
		this.teaNameAndTitel = teaNameAndTitel;
		this.score = score;
	}

	public FinalProjectTitel(String stuId, String titelCn, String titelEn, String academy, String teaNameAndTitel) {
		super();
		this.stuId = stuId;
		this.titelCn = titelCn;
		this.titelEn = titelEn;
		this.academy = academy;
		this.teaNameAndTitel = teaNameAndTitel;
	}

	public FinalProjectTitel() {
		super();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getTitelCn() {
		return titelCn;
	}

	public void setTitelCn(String titelCn) {
		this.titelCn = titelCn;
	}

	public String getTitelEn() {
		return titelEn;
	}

	public void setTitelEn(String titelEn) {
		this.titelEn = titelEn;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public String getTeaNameAndTitel() {
		return teaNameAndTitel;
	}

	public void setTeaNameAndTitel(String teaNameAndTitel) {
		this.teaNameAndTitel = teaNameAndTitel;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getIsSumbit() {
		return isSumbit;
	}

	public void setIsSumbit(boolean isSumbit) {
		this.isSumbit = isSumbit;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((academy == null) ? 0 : academy.hashCode());
		result = prime * result + count;
		result = prime * result + ((division == null) ? 0 : division.hashCode());
		result = prime * result + (isSumbit ? 1231 : 1237);
		result = prime * result + ((majorName == null) ? 0 : majorName.hashCode());
		result = prime * result + score;
		result = prime * result + ((stuId == null) ? 0 : stuId.hashCode());
		result = prime * result + ((stuName == null) ? 0 : stuName.hashCode());
		result = prime * result + ((teaNameAndTitel == null) ? 0 : teaNameAndTitel.hashCode());
		result = prime * result + ((titelCn == null) ? 0 : titelCn.hashCode());
		result = prime * result + ((titelEn == null) ? 0 : titelEn.hashCode());
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
		FinalProjectTitel other = (FinalProjectTitel) obj;
		if (academy == null) {
			if (other.academy != null)
				return false;
		} else if (!academy.equals(other.academy))
			return false;
		if (count != other.count)
			return false;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (isSumbit != other.isSumbit)
			return false;
		if (majorName == null) {
			if (other.majorName != null)
				return false;
		} else if (!majorName.equals(other.majorName))
			return false;
		if (score != other.score)
			return false;
		if (stuId == null) {
			if (other.stuId != null)
				return false;
		} else if (!stuId.equals(other.stuId))
			return false;
		if (stuName == null) {
			if (other.stuName != null)
				return false;
		} else if (!stuName.equals(other.stuName))
			return false;
		if (teaNameAndTitel == null) {
			if (other.teaNameAndTitel != null)
				return false;
		} else if (!teaNameAndTitel.equals(other.teaNameAndTitel))
			return false;
		if (titelCn == null) {
			if (other.titelCn != null)
				return false;
		} else if (!titelCn.equals(other.titelCn))
			return false;
		if (titelEn == null) {
			if (other.titelEn != null)
				return false;
		} else if (!titelEn.equals(other.titelEn))
			return false;
		return true;
	}

	

}
