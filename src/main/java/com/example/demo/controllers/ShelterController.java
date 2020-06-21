package com.example.demo.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Shelter;
import com.example.demo.services.ShelterService;
import com.itextpdf.text.DocumentException;

@RestController
public class ShelterController {

	@Autowired
	ShelterService service;
	
	//GET - get all shelters 
	@RequestMapping("/shelters")
	public List<Shelter> getAllShelters() {
		return service.getAllShelters();
	}
	
	//get all shelters on a PDF file
	@RequestMapping("/shelters/pdf")
	public ResponseEntity<InputStreamResource> getSheltersPdf() throws FileNotFoundException, DocumentException {
		ByteArrayInputStream bis = service.getSheltersPdf();
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=warehouseReport.pdf");

	    return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
	}
		
	//POST - create a new shelter
	@PostMapping("/shelters")
	public String postShelter(@RequestBody Shelter shelter) {					
		return service.postShelter(shelter);
	}
		
	//PUT - modify information on specific shelter
	@PutMapping("/shelters/{id}")
	public String updateShelter(@RequestBody Shelter shelter, @PathVariable int id) {					
		return service.updateShelter(shelter, id);
	}
		
	//DELETE - delete a single shelter entry
	@DeleteMapping("/shelters/{id}")
	public String deleteShelter(@PathVariable int id) {
		return service.deleteShelter(id);
	}
	
}
