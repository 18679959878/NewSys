package com.sys.entity;

public class PracticeSelfIdentification {
	private String stuId;
	private String identification;
	private int count;
	public PracticeSelfIdentification(String stuId, String identification) {
		super();
		this.stuId = stuId;
		this.identification = identification;
	}

	public PracticeSelfIdentification() {
		super();
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
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
		result = prime * result + ((identification == null) ? 0 : identification.hashCode());
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
		PracticeSelfIdentification other = (PracticeSelfIdentification) obj;
		if (identification == null) {
			if (other.identification != null)
				return false;
		} else if (!identification.equals(other.identification))
			return false;
		if (stuId == null) {
			if (other.stuId != null)
				return false;
		} else if (!stuId.equals(other.stuId))
			return false;
		return true;
	}

}
