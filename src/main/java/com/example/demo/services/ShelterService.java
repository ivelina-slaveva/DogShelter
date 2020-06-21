package com.example.demo.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Dog;
import com.example.demo.entities.Shelter;
import com.example.demo.repositories.DogRepo;
import com.example.demo.repositories.ShelterRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ShelterService {

	@Autowired
	ShelterRepo shelterRepo;
	
	//GET - get all shelters 
	public List<Shelter> getAllShelters() {
		return shelterRepo.findAll();
	}
	
	//GET all shelters - pdf
	public ByteArrayInputStream getSheltersPdf() throws DocumentException, FileNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		List<Shelter> shelters = new ArrayList(shelterRepo.findAll());
		
		Document document = new Document();
		 
		
		//Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		//Chunk chunk = new Chunk(new String(warehouses.split("")), font);
		
		PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(60);
        table.setWidths(new int[]{1, 3, 3, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("Id", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Name", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        
        hcell = new PdfPCell(new Phrase("Capacity", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Adress", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
		
		for (Shelter shelter : shelters) {

            PdfPCell cell;

            cell = new PdfPCell(new Phrase(new String(String.valueOf(shelter.getId()))));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(shelter.getName()));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(shelter.getCapacity())));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(shelter.getAdress())));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingRight(5);
            table.addCell(cell);
        }
		 
		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);
		
		document.close();
		
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	//POST - create a new shelter
	public String postShelter(Shelter shelter) {
		String name = shelter.getName();
		//Check to see if there is already a shelter with that name
		if(shelterRepo.findByName(name) != null) {
			//Send error message!
			return "A shelter with that name is already present!";
		} else {
			shelterRepo.save(shelter);	
			return "Saved " + shelter.getName() + "!" ;
		}
	}
	
	//PUT - modify information on specific shelter
	public String updateShelter (Shelter shelter, int id) {
		Shelter foundShelter = shelterRepo.findById(id);
		if(foundShelter == null) {
			System.out.printf("Error, dog with id - %d not found!", id);
			return "Error in put route!";
		} else {
			foundShelter.setName(shelter.getName());
			foundShelter.setAdress(shelter.getAdress());
			foundShelter.setCapacity(shelter.getCapacity());
			shelterRepo.save(foundShelter);
			return "Modified " + foundShelter.getName() + "!";
		}
	}
	
	//DELETE - delete a single shelter entry
	public String deleteShelter(int id) {
		Shelter foundShelter = shelterRepo.findById(id);
		if(foundShelter == null) {
			System.out.printf("Error, shelter with id - %d not found!", id);
			return "Error in deleted route!";
		} else {
			String deletedShelterName = foundShelter.getName();
			shelterRepo.deleteById(id);
			return "Deleted " + deletedShelterName + "!";
		}
	}

}
