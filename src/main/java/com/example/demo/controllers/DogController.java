package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DogDto;
import com.example.demo.entities.Dog;
import com.example.demo.services.DogService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogController {
	
	@Autowired
	DogService service;

	//GET - get all dogs in the shelter
	@RequestMapping("/dogs")
	public List<Dog> getAllDogs() {
		return service.getAllDogs();
	}
	
	//POST - create a new dog in shelter
	@PostMapping("/dogs")
	public String postDog(@RequestBody DogDto dog) {					
		return service.postDog(dog);
	}
	
	//PUT - modify information on specific dog
	@PutMapping("/dogs/{id}")
	public String modifyDog(@RequestBody Dog dog, @PathVariable int id) {					
		return service.updateDog(dog, id);
	}
	
	//DELETE - delete a single dog entry
	@DeleteMapping("/dogs/{id}")
	public String deleteDog(@PathVariable int id) {
		return service.deleteDog(id);
	}
	
}
