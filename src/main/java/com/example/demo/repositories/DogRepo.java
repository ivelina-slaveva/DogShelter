package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Dog;

public interface DogRepo extends JpaRepository<Dog, Integer>{

	Dog findById(int id);
	
	Dog findByName(String name);
	
	void deleteByName(String name);
}
