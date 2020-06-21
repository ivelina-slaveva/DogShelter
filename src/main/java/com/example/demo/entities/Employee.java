package com.example.demo.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "employee")
public class Employee {
	
	@javax.persistence.Id
	@GeneratedValue
	private int Id;

	private String name;
	private int age;
	private int salary;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shelter_id", nullable = false)
	@JsonIgnore
    private Shelter shelter;
	
	public Employee(String name, int age, int salary) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
	
	public Employee() {
		super();
	}
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
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Shelter getShelter() {
		return shelter;
	}

	public void setShelter(Shelter shelter) {
		this.shelter = shelter;
	} 
}
