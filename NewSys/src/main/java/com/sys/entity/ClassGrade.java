
package com.sys.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ClassGrade {
	// �༶��
	private String className;
	// �༶���� 0����ԭ�༶��1����ַ���༶��Ĭ��ԭ�༶
	private int classType = 0;
	// ԭ�༶
	public static final int OLD_CLASS = 0;
	// �ַ���༶
	public static final int FIELD_CLASS = 1;
	// רҵ��
	private String major;
	// �༶�����꼶
	private String grade;
	// �༶ר����
	private String education;
	// �༶����
	private int stuNum;
	//ʵϰ֪����ʦ
	private String filedWorkTea;
	// һ�Զ�student��ԭ�༶ѧ��
	@JSONField(serialize = false)
	private List<Student> students;
	// һ�Զ�student���ַ���༶ѧ��
	@JSONField(serialize = false)
	private List<Student> majorStudents;

	public ClassGrade() {
		super();
	}

	public ClassGrade(String className, String grade, String education, String major) {
		super();
		this.className = className;
		this.major = major;
		this.grade = grade;
		this.education = education;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getStuNum() {
		return stuNum;
	}

	public void setStuNum(int stuNum) {
		this.stuNum = stuNum;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public int getClassType() {
		return classType;
	}

	public void setClassType(int classType) {
		this.classType = classType;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getFiledWorkTea() {
		return filedWorkTea;
	}

	public void setFiledWorkTea(String filedWorkTea) {
		this.filedWorkTea = filedWorkTea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + classType;
		result = prime * result + ((education == null) ? 0 : education.hashCode());
		result = prime * result + ((filedWorkTea == null) ? 0 : filedWorkTea.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((majorStudents == null) ? 0 : majorStudents.hashCode());
		result = prime * result + stuNum;
		result = prime * result + ((students == null) ? 0 : students.hashCode());
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
		ClassGrade other = (ClassGrade) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (classType != other.classType)
			return false;
		if (education == null) {
			if (other.education != null)
				return false;
		} else if (!education.equals(other.education))
			return false;
		if (filedWorkTea == null) {
			if (other.filedWorkTea != null)
				return false;
		} else if (!filedWorkTea.equals(other.filedWorkTea))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (majorStudents == null) {
			if (other.majorStudents != null)
				return false;
		} else if (!majorStudents.equals(other.majorStudents))
			return false;
		if (stuNum != other.stuNum)
			return false;
		if (students == null) {
			if (other.students != null)
				return false;
		} else if (!students.equals(other.students))
			return false;
		return true;
	}



}
