package com.sys.entity;

public class FinalProjectTask {
	private String stuId;
	private String contentAndRequire;
	private String literature;
	private String state;
	private int count;
	public FinalProjectTask(String stuId, String contentAndRequire, String literature) {
		super();
		this.stuId = stuId;
		this.contentAndRequire = contentAndRequire;
		this.literature = literature;
	}
	public FinalProjectTask() {
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
	public String getContentAndRequire() {
		return contentAndRequire;
	}
	public void setContentAndRequire(String contentAndRequire) {
		this.contentAndRequire = contentAndRequire;
	}
	public String getLiterature() {
		return literature;
	}
	public void setLiterature(String literature) {
		this.literature = literature;
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
		result = prime * result + ((contentAndRequire == null) ? 0 : contentAndRequire.hashCode());
		result = prime * result + ((literature == null) ? 0 : literature.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		FinalProjectTask other = (FinalProjectTask) obj;
		if (contentAndRequire == null) {
			if (other.contentAndRequire != null)
				return false;
		} else if (!contentAndRequire.equals(other.contentAndRequire))
			return false;
		if (literature == null) {
			if (other.literature != null)
				return false;
		} else if (!literature.equals(other.literature))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (stuId == null) {
			if (other.stuId != null)
				return false;
		} else if (!stuId.equals(other.stuId))
			return false;
		return true;
	}
	
}
