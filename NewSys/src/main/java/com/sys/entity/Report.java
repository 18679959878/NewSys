package com.sys.entity;

public class Report {
	private String stageName;
	private String stuId;
	private String reportText;
	private String guidance;
	private int score;
	private boolean isLock;
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getReportText() {
		return reportText;
	}
	public void setReportText(String reportText) {
		this.reportText = reportText;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guidance == null) ? 0 : guidance.hashCode());
		result = prime * result + (isLock ? 1231 : 1237);
		result = prime * result + ((reportText == null) ? 0 : reportText.hashCode());
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
		Report other = (Report) obj;
		if (guidance == null) {
			if (other.guidance != null)
				return false;
		} else if (!guidance.equals(other.guidance))
			return false;
		if (isLock != other.isLock)
			return false;
		if (reportText == null) {
			if (other.reportText != null)
				return false;
		} else if (!reportText.equals(other.reportText))
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
