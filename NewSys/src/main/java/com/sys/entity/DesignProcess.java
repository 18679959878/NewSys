package com.sys.entity;

public class DesignProcess {
	private String stageName;
	private String stuId;
	private String sketch;
	private String amendOpinion;
	private String guidance;
	// 默认分数为0
	private int score = 0;
	private boolean isLock;

	
	private int count;

	public DesignProcess(String stageName, String stuId, String sketch, String amendOpinion, String guidance) {
		super();
		this.stageName = stageName;
		this.stuId = stuId;
		this.sketch = sketch;
		this.amendOpinion = amendOpinion;
		this.guidance = guidance;
	}

	public DesignProcess() {
		super();
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

	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	public String getAmendOpinion() {
		return amendOpinion;
	}

	public void setAmendOpinion(String amendOpinion) {
		this.amendOpinion = amendOpinion;
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
		result = prime * result + ((amendOpinion == null) ? 0 : amendOpinion.hashCode());
		result = prime * result + ((guidance == null) ? 0 : guidance.hashCode());
		result = prime * result + (isLock ? 1231 : 1237);
		result = prime * result + score;
		result = prime * result + ((sketch == null) ? 0 : sketch.hashCode());
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
		DesignProcess other = (DesignProcess) obj;
		if (amendOpinion == null) {
			if (other.amendOpinion != null)
				return false;
		} else if (!amendOpinion.equals(other.amendOpinion))
			return false;
		if (guidance == null) {
			if (other.guidance != null)
				return false;
		} else if (!guidance.equals(other.guidance))
			return false;
		if (isLock != other.isLock)
			return false;
		if (score != other.score)
			return false;
		if (sketch == null) {
			if (other.sketch != null)
				return false;
		} else if (!sketch.equals(other.sketch))
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
