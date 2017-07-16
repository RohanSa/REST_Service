package com.employee.dto;

import java.io.Serializable;
import java.util.Date;

public class EmployeeDto implements Serializable {


	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Date dateOfBirth;

	public EmployeeDto() {
		super();
	}

	public EmployeeDto(int id, String name, Date dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + "]";
	}

}
