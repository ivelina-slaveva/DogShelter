package com.example.demo.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Dog {

	@javax.persistence.Id
	@GeneratedValue
	private int Id;
	
	String name;
	String breed;
	int gender;	// gender: 0 - unknown, 1 - male, 2 - female
	int weight;
	
//---------ManyToOne------------------	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shelter_id")
	@JsonIgnore
	private Shelter shelter;
	
//---------ManyToOne------------------	
	
	public Dog(String name, String breed, int gender, int weight) {
		this.name = name;
		this.breed = breed;
		this.gender = gender;
		this.weight = weight;
	}

	public Dog() {
		super();
	}
	
//--------Getters and Setters----------
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getWeight() {
		return weight;
	}

	public Shelter getShelter() {
		return shelter;
	}

	public void setShelter(Shelter shelter) {
		this.shelter = shelter;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
