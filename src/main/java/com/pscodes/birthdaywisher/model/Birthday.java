package com.pscodes.birthdaywisher.model;

import java.time.LocalDate;

public class Birthday {
	
	private String name;
	private LocalDate dob;
	private String email;
	
	
	
	public Birthday() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Birthday(String name, LocalDate dob, String email) {
		super();
		this.name = name;
		this.dob = dob;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Birthday [name=" + name + ", dob=" + dob + ", email=" + email + "]";
	}
	
	
}
