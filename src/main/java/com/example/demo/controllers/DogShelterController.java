package com.example.demo.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Dog;
import com.example.demo.entities.Shelter;
import com.example.demo.services.DogShelterService;
import com.example.demo.services.ShelterService;

@RestController
public class DogShelterController {

	@Autowired
	DogShelterService service;
	
	//GET - get all dogs from shelter 
	@RequestMapping("/shelters/{shelterId}/dogs")
	public  List<Dog> getAllDogsFromShelter(@PathVariable int shelterId) {
		return service.getAllDogsFromShelter(shelterId);
	}
		
	//POST - create a new dog from shelter
	@PostMapping("/shelters/{shelterId}/dogs")
	public String postDogInShelter(@PathVariable int shelterId, @RequestBody Dog dog) {					
		return service.postDogInShelter(shelterId, dog);
	}
		
	//PUT - modify information on specific dog from shelter
	@PutMapping("/shelters/{shelterId}/dogs/{dogId}")
	public String updateDogFromShelter(@PathVariable int shelterId, @RequestBody Dog dog, @PathVariable int dogId) {					
		return service.updateDogFromShelter(shelterId, dog, dogId);
	}
		
	//DELETE - delete a single dog entry from shelter
	@DeleteMapping("/shelters/{shelterId}/dogs/{dogId}")
	public String deleteDogFromShelter(@PathVariable int shelterId, @PathVariable int dogId) {
		return service.deleteDogFromShelter(shelterId, dogId);
	}
	
}
