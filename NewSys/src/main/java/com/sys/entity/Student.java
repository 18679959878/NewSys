package com.sys.entity;

public class Student {
	// 学号
	private String stuId;
	// 学生姓名
	private String stuName;
	// 邮箱
	private String email;
	// 密码
	private String password;
	// 所属班级
	private String className;
	//专业0.......0
	private String education;
	// 毕业设计指导老师
	private String fieldworkTea;
	//实习指导老师
	private String worksTea;
	// 分方向班级
	private String majorClassName;
	// fpt提交状态，标题提交状态
	private String ftpState;
	// fptask提交状态,任务计划提交状态
	private String ftpTaskState;
	// openReport提交状态
	private String orState;
	//openReport打分
	private int orScore;
	//设计过程打分
	private int deScore;
	// 设计阶段名
	private String deStageName;
	// 设计阶段状态
	private String deStageState;
	// 说明文阶段名
	private String reStageName;
	// 说明文阶段提交状态
	private String reStageState;
	//实习周志阶段名
	private String wrStageName;
	//实习周志阶段状态
	private String wrStageState;
	//实习周志该周
	private int wrScore;
	//说明文分数
	private int reScore;
	// 毕业设计中文标题
	private String titelCn;
	// 教师指导意见提交状态
	private String coStageState;
	public Student() {
		super();
	}

	public Student(String stuId, String stuName, String email, String password, String className, String fieldworkTea,
			String worksTea) {
		super();
		this.stuId = stuId;
		this.stuName = stuName;
		this.email = email;
		this.password = password;
		this.className = className;
		this.fieldworkTea = fieldworkTea;
		this.worksTea = worksTea;
	}

	public int getWrScore() {
		return wrScore;
	}

	public void setWrScore(int wrScore) {
		this.wrScore = wrScore;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDeStageName() {
		return deStageName;
	}

	public void setDeStageName(String deStageName) {
		this.deStageName = deStageName;
	}

	public String getDeStageState() {
		return deStageState;
	}

	public void setDeStageState(String deStageState) {
		this.deStageState = deStageState;
	}

	public String getFtpTaskState() {
		return ftpTaskState;
	}

	public int getOrScore() {
		return orScore;
	}

	public int getReScore() {
		return reScore;
	}

	public void setReScore(int reScore) {
		this.reScore = reScore;
	}

	public void setOrScore(int orScore) {
		this.orScore = orScore;
	}

	public void setFtpTaskState(String ftpTaskState) {
		this.ftpTaskState = ftpTaskState;
	}

	public String getTitelCn() {
		return titelCn;
	}

	public void setTitelCn(String titelCn) {
		this.titelCn = titelCn;
	}

	public String getStuId() {
		return stuId;
	}

	public int getDeScore() {
		return deScore;
	}

	public void setDeScore(int deScore) {
		this.deScore = deScore;
	}

	public void setStuId(String stuId) {
		if (stuId != null && stuId.equals(""))
			this.stuId = null;
		else
			this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		if (stuName != null && stuName.equals(""))
			this.stuName = null;
		else
			this.stuName = stuName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null && email.equals(""))
			this.email = null;
		else
			this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null && password.equals(""))
			this.password = null;
		else
			this.password = password;
	}

	public String getClassName() {
		return className;
	}

	public String getCoStageState() {
		return coStageState;
	}

	public void setCoStageState(String coStageState) {
		this.coStageState = coStageState;
	}

	public void setClassName(String className) {
		if (className != null && className.equals(""))
			this.className = null;
		else
			this.className = className;
	}

	public String getFieldworkTea() {
		return fieldworkTea;
	}

	public void setFieldworkTea(String fieldworkTea) {
		if (fieldworkTea != null && fieldworkTea.equals(""))
			this.fieldworkTea = null;
		else
			this.fieldworkTea = fieldworkTea;
	}

	public String getWorksTea() {
		return worksTea;
	}

	public void setWorksTea(String worksTea) {
		if (worksTea != null && worksTea.equals(""))
			this.worksTea = null;
		this.worksTea = worksTea;
	}

	public String getMajorClassName() {
		return majorClassName;
	}

	public void setMajorClassName(String majorClassName) {
		this.majorClassName = majorClassName;
	}

	public String getFtpState() {
		return ftpState;
	}

	public void setFtpState(String ftpState) {
		this.ftpState = ftpState;
	}

	public String getOrState() {
		return orState;
	}

	public void setOrState(String orState) {
		this.orState = orState;
	}

	public String getReStageName() {
		return reStageName;
	}

	public void setReStageName(String reStageName) {
		this.reStageName = reStageName;
	}

	public String getReStageState() {
		return reStageState;
	}

	public void setReStageState(String reStageState) {
		this.reStageState = reStageState;
	}

	public String getWrStageName() {
		return wrStageName;
	}

	public void setWrStageName(String wrStageName) {
		this.wrStageName = wrStageName;
	}

	public String getWrStageState() {
		return wrStageState;
	}

	public void setWrStageState(String wrStageState) {
		this.wrStageState = wrStageState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fieldworkTea == null) ? 0 : fieldworkTea.hashCode());
		result = prime * result + ((majorClassName == null) ? 0 : majorClassName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((stuId == null) ? 0 : stuId.hashCode());
		result = prime * result + ((stuName == null) ? 0 : stuName.hashCode());
		result = prime * result + ((worksTea == null) ? 0 : worksTea.hashCode());
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
		Student other = (Student) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fieldworkTea == null) {
			if (other.fieldworkTea != null)
				return false;
		} else if (!fieldworkTea.equals(other.fieldworkTea))
			return false;
		if (majorClassName == null) {
			if (other.majorClassName != null)
				return false;
		} else if (!majorClassName.equals(other.majorClassName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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
		if (worksTea == null) {
			if (other.worksTea != null)
				return false;
		} else if (!worksTea.equals(other.worksTea))
			return false;
		return true;
	}

	public void print() {
		System.out.println(this.stuId + "  " + this.stuName + "  " + this.className + "  " + this.email + "  "
				+ this.password + "  " + this.worksTea + "  " + this.fieldworkTea);
	}

}
