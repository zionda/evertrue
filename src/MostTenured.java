package com.evertrue;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MostTenured {
	private String company;
	@SerializedName("avg_retention")
	private float averageRetention;
	@SerializedName("most_tenured")
	private ArrayList<TenuredPeople> tenuredPeople;
	public String getCompany() {
		return company;
	}
	public MostTenured(String company, float averageRetention) {
		super();
		this.company = company;
		this.averageRetention = averageRetention;
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
	public ArrayList<TenuredPeople> getTenuredPeople() {
		return tenuredPeople;
	}
	public void setTenuredPeople(ArrayList<TenuredPeople> tenuredPeople) {
		this.tenuredPeople = tenuredPeople;
	}
}