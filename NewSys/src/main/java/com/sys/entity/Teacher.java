package com.sys.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Teacher {
	private String teaAccount;
	private String teaName;
	private String email;
	private String password;
	@JSONField(serialize = false)
	private List<Student> fieldworkStudents;
	@JSONField(serialize = false)
	private List<Student> workStudents;

	public String getTeaAccount() {
		return teaAccount;
	}

	public void setTeaAccount(String teaAccount) {
		this.teaAccount = teaAccount;
	}

	public String getTeaName() {
		return teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Student> getFieldworkStudents() {
		return fieldworkStudents;
	}

	public void setFieldworkStudents(List<Student> fieldworkStudents) {
		this.fieldworkStudents = fieldworkStudents;
	}

	public List<Student> getWorkStudents() {
		return workStudents;
	}

	public void setWorkStudents(List<Student> workStudents) {
		this.workStudents = workStudents;
	}

	public Teacher(String teaAccount, String teaName, String email, String password) {
		super();
		this.teaAccount = teaAccount;
		this.teaName = teaName;
		this.email = email;
		this.password = password;
	}

	public Teacher() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fieldworkStudents == null) ? 0 : fieldworkStudents.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((teaAccount == null) ? 0 : teaAccount.hashCode());
		result = prime * result + ((teaName == null) ? 0 : teaName.hashCode());
		result = prime * result + ((workStudents == null) ? 0 : workStudents.hashCode());
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
		Teacher other = (Teacher) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fieldworkStudents == null) {
			if (other.fieldworkStudents != null)
				return false;
		} else if (!fieldworkStudents.equals(other.fieldworkStudents))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (teaAccount == null) {
			if (other.teaAccount != null)
				return false;
		} else if (!teaAccount.equals(other.teaAccount))
			return false;
		if (teaName == null) {
			if (other.teaName != null)
				return false;
		} else if (!teaName.equals(other.teaName))
			return false;
		if (workStudents == null) {
			if (other.workStudents != null)
				return false;
		} else if (!workStudents.equals(other.workStudents))
			return false;
		return true;
	}

}
