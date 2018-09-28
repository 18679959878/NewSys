package com.sys.entity;

public class OpenReport {
	private String stuId;
	private String basisAndSignificance;
	private String situation;
	private String researchContent;
	private String proposal;
	private String literature;
	private String otherInstructions;
	private String agree;
	private boolean isLock;
	private int score;
	private int count;
	public OpenReport() {
		super();
	}
	public OpenReport(String stuId, String basisAndSignificance, String situation, String researchContent,
			String proposal, String literature, String otherInstructions, String agree) {
		super();
		this.stuId = stuId;
		this.basisAndSignificance = basisAndSignificance;
		this.situation = situation;
		this.researchContent = researchContent;
		this.proposal = proposal;
		this.literature = literature;
		this.otherInstructions = otherInstructions;
		this.agree = agree;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getBasisAndSignificance() {
		return basisAndSignificance;
	}
	public void setBasisAndSignificance(String basisAndSignificance) {
		this.basisAndSignificance = basisAndSignificance;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getResearchContent() {
		return researchContent;
	}
	public void setResearchContent(String researchContent) {
		this.researchContent = researchContent;
	}
	public String getProposal() {
		return proposal;
	}
	public void setProposal(String proposal) {
		this.proposal = proposal;
	}
	public String getLiterature() {
		return literature;
	}
	public void setLiterature(String literature) {
		this.literature = literature;
	}
	public String getOtherInstructions() {
		return otherInstructions;
	}
	public void setOtherInstructions(String otherInstructions) {
		this.otherInstructions = otherInstructions;
	}
	public String getAgree() {
		return agree;
	}
	public void setAgree(String agree) {
		this.agree = agree;
	}
	public boolean getIsLock() {
		return isLock;
	}
	public void setIsLock(boolean isLock) {
		this.isLock = isLock;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
		result = prime * result + ((agree == null) ? 0 : agree.hashCode());
		result = prime * result + ((basisAndSignificance == null) ? 0 : basisAndSignificance.hashCode());
		result = prime * result + (isLock ? 1231 : 1237);
		result = prime * result + ((literature == null) ? 0 : literature.hashCode());
		result = prime * result + ((otherInstructions == null) ? 0 : otherInstructions.hashCode());
		result = prime * result + ((proposal == null) ? 0 : proposal.hashCode());
		result = prime * result + ((researchContent == null) ? 0 : researchContent.hashCode());
		result = prime * result + score;
		result = prime * result + ((situation == null) ? 0 : situation.hashCode());
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
		OpenReport other = (OpenReport) obj;
		if (agree == null) {
			if (other.agree != null)
				return false;
		} else if (!agree.equals(other.agree))
			return false;
		if (basisAndSignificance == null) {
			if (other.basisAndSignificance != null)
				return false;
		} else if (!basisAndSignificance.equals(other.basisAndSignificance))
			return false;
		if (isLock != other.isLock)
			return false;
		if (literature == null) {
			if (other.literature != null)
				return false;
		} else if (!literature.equals(other.literature))
			return false;
		if (otherInstructions == null) {
			if (other.otherInstructions != null)
				return false;
		} else if (!otherInstructions.equals(other.otherInstructions))
			return false;
		if (proposal == null) {
			if (other.proposal != null)
				return false;
		} else if (!proposal.equals(other.proposal))
			return false;
		if (researchContent == null) {
			if (other.researchContent != null)
				return false;
		} else if (!researchContent.equals(other.researchContent))
			return false;
		if (score != other.score)
			return false;
		if (situation == null) {
			if (other.situation != null)
				return false;
		} else if (!situation.equals(other.situation))
			return false;
		if (stuId == null) {
			if (other.stuId != null)
				return false;
		} else if (!stuId.equals(other.stuId))
			return false;
		return true;
	}
	
}
