package com.evertrue;

public class MostTenured {
	private String company;
	private float averageRetention;
	private TenuredPeople[] tenuredPeople;
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
	public TenuredPeople[] getTenuredPeople() {
		return tenuredPeople;
	}
	public void setTenuredPeople(TenuredPeople[] tenuredPeople) {
		this.tenuredPeople = tenuredPeople;
	}
}