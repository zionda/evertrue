package com.evertrue;

public class TenuredPeople {
	private String name;
	private int age;
	private float tenure;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public float getTenure() {
		return tenure;
	}
	public void setTenure(float tenure) {
		this.tenure = tenure;
	}
	public TenuredPeople(String name, int age, float tenure) {
		super();
		this.name = name;
		this.age = age;
		this.tenure = tenure;
	}
	public TenuredPeople() {
		super();
	}
}