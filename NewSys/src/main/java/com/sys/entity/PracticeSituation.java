package com.sys.entity;

public class PracticeSituation {
	private String stuId;
	private int fullWork;
	private int absenceLeave;
	private int sickLeave;
	private int late;
	private int leaveEarly;
	private int absent;
	private String workSituation;
	private int score;
	private boolean isLock;
	private int count;



	public PracticeSituation(String stuId, int fullWork, int absenceLeave, int sickLeave, int late, int leaveEarly,
			int absent, String workSituation) {
		super();
		this.stuId = stuId;
		this.fullWork = fullWork;
		this.absenceLeave = absenceLeave;
		this.sickLeave = sickLeave;
		this.late = late;
		this.leaveEarly = leaveEarly;
		this.absent = absent;
		this.workSituation = workSituation;
	}

	public PracticeSituation() {
		super();
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public int getFullWork() {
		return fullWork;
	}

	public void setFullWork(int fullWork) {
		this.fullWork = fullWork;
	}

	public int getAbsenceLeave() {
		return absenceLeave;
	}

	public void setAbsenceLeave(int absenceLeave) {
		this.absenceLeave = absenceLeave;
	}

	public int getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(int sickLeave) {
		this.sickLeave = sickLeave;
	}

	public int getLate() {
		return late;
	}

	public void setLate(int late) {
		this.late = late;
	}

	public int getLeaveEarly() {
		return leaveEarly;
	}

	public void setLeaveEarly(int leaveEarly) {
		this.leaveEarly = leaveEarly;
	}

	public int getAbsent() {
		return absent;
	}

	public void setAbsent(int absent) {
		this.absent = absent;
	}



	public String getWorkSituation() {
		return workSituation;
	}

	public void setWorkSituation(String workSituation) {
		this.workSituation = workSituation;
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
		result = prime * result + absenceLeave;
		result = prime * result + absent;
		result = prime * result + fullWork;
		result = prime * result + late;
		result = prime * result + leaveEarly;
		result = prime * result + sickLeave;
		result = prime * result + ((stuId == null) ? 0 : stuId.hashCode());
		result = prime * result + ((workSituation == null) ? 0 : workSituation.hashCode());
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
		PracticeSituation other = (PracticeSituation) obj;
		if (absenceLeave != other.absenceLeave)
			return false;
		if (absent != other.absent)
			return false;
		if (fullWork != other.fullWork)
			return false;
		if (late != other.late)
			return false;
		if (leaveEarly != other.leaveEarly)
			return false;
		if (sickLeave != other.sickLeave)
			return false;
		if (stuId == null) {
			if (other.stuId != null)
				return false;
		} else if (!stuId.equals(other.stuId))
			return false;
		if (workSituation == null) {
			if (other.workSituation != null)
				return false;
		} else if (!workSituation.equals(other.workSituation))
			return false;
		return true;
	}



}
