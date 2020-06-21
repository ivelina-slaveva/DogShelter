package com.example.demo.dto;


public class DogDto {


	String name;
	String breed;
	String ShelterName;
	int gender;	// gender: 0 - unknown, 1 - male, 2 - female
	int weight;
	
	public DogDto(String name, String breed, String ShelteName, int gender, int weight) {
		this.name = name;
		this.breed = breed;
		this.gender = gender;
		this.weight = weight;
		this.ShelterName = ShelteName;
	}

	public DogDto() {
    
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

	public String getShelterName() {
		return ShelterName;
	}

	public void setShelterName(String shelter) {
		this.ShelterName = shelter;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
