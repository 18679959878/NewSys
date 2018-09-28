package com.sys.entity;

public class WeekRecord {
	private String stuId;
	private String stageName;
	private String department;
	private String place;
	private String activity;
	private String guidance;
	private int score;
	private boolean isLock;
	private int count;

	public WeekRecord() {
		super();
	}

	public WeekRecord(String stuId, String stageName, String department, String place, String activity,
			String guidance) {
		super();
		this.stuId = stuId;
		this.stageName = stageName;
		this.department = department;
		this.place = place;
		this.activity = activity;
		this.guidance = guidance;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getGuidance() {
		return guidance;
	}

	public void setGuidance(String guidance) {
		this.guidance = guidance;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(boolean isLock) {
		this.isLock = isLock;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activity == null) ? 0 : activity.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((guidance == null) ? 0 : guidance.hashCode());
		result = prime * result + (isLock ? 1231 : 1237);
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + score;
		result = prime * result + ((stageName == null) ? 0 : stageName.hashCode());
		result = prime * result + ((stuId == null) ? 0 : stuId.hashCode());
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
		WeekRecord other = (WeekRecord) obj;
		if (activity == null) {
			if (other.activity != null)
				return false;
		} else if (!activity.equals(other.activity))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (guidance == null) {
			if (other.guidance != null)
				return false;
		} else if (!guidance.equals(other.guidance))
			return false;
		if (isLock != other.isLock)
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		if (score != other.score)
			return false;
		if (stageName == null) {
			if (other.stageName != null)
				return false;
		} else if (!stageName.equals(other.stageName))
			return false;
		if (stuId == null) {
			if (other.stuId != null)
				return false;
		} else if (!stuId.equals(other.stuId))
			return false;
		return true;
	}

}
