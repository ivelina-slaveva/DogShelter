package com.example.demo.entities;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.entities.Dog;

@Entity
@Table(name = "shelter")
public class Shelter {

	@javax.persistence.Id
	@GeneratedValue
	private int Id;
	
	String name;
	String adress;
	int capacity;
	
	// ---------OneToMany-------------------
	@OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name="dogs_id")
	private List <Dog> dogs;
	
	@OneToMany(mappedBy = "shelter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List <Employee> employee;
	// ---------OneToMany-------------------
	
	
	public Shelter(String name, String adress, int capacity) {
		this.name = name;
		this.adress = adress;
		this.capacity = capacity;
	}


	public Shelter() {
		super();
	}
	
//	--------Getters and Setters----------
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public Dog getDog(int id) {
		for (Dog dog : dogs) {
			if(dog.getId() == id) {
				return dog;
			}
		}
		return null;
	}
	
	public Dog setDog(Dog modifiedDog, int dogId) {
		for (int i = 0; i < dogs.size(); i++) {
			if(dogs.get(i).getId() == dogId) {
				System.out.println("Modified dog " + modifiedDog.getName() + "!");
				dogs.set(i, modifiedDog);
				return modifiedDog;
			}
		}
		System.out.println("No dog with id" + modifiedDog.getId() + "!");
		return null;
	}
	
	public void removeDog(int dogId) {
		for (int i = 0; i < dogs.size(); i++) {
			if(dogs.get(i).getId() == dogId) {
				System.out.println("Deleted dog with id: " + dogId + "!");
				dogs.remove(i);
			}
		}
		System.out.println("No dog with id" + dogId + "!");
	}


	public List<Dog> getDogs(){
		return this.dogs;
	}
	
	public void addDog(Dog dog) {
		this.dogs.add(dog);
	}

}
