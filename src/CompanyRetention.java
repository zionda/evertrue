package com.evertrue;

import com.google.gson.annotations.SerializedName;

public class CompanyRetention {
	private String company;
	@SerializedName("avg_retention")
	private float averageRetention;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public float getAverageRetention() {
		return averageRetention;
	}
	public void setAverageRetention(float averageRetention) {
		this.averageRetention = averageRetention;
	}
	public CompanyRetention(String company, float averageRetention) {
		super();
		this.company = company;
		this.averageRetention = averageRetention;
	}
}