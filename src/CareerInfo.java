package com.evertrue;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class CareerInfo {
	private String position;
	private String company;
	private String state;
	@SerializedName("start_date")
	private Date startDate;
	@SerializedName("end_date") 
	private Date endDate;

	public CareerInfo(String position, String company, String state, Date startDate, Date endDate) {
		super();
		this.position = position;
		this.company = company;
		this.state = state;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompaany(String company) {
		this.company = company;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}