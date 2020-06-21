package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.entities.Dog;
import com.example.demo.entities.Shelter;

public interface ShelterRepo extends JpaRepository<Shelter, Integer>{

	Shelter findById(int id);
	
	Shelter findByName(String name);
	
	void deleteByName(String name);
	
	//Get dogs from specific shelter
	//List<Dog> findDogsById(int shelterId);
	
}
