package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.example.demo.dto.DogDto;
import com.example.demo.entities.Dog;
import com.example.demo.entities.Shelter;
import com.example.demo.repositories.DogRepo;
import com.example.demo.repositories.ShelterRepo;

@Service
public class DogService {

	@Autowired
	DogRepo dogRepo;
	
	@Autowired
	ShelterRepo shelterRepo;
	
	//GET - get all dogs in the shelter
	public List<Dog> getAllDogs() {
		return dogRepo.findAll();
	}
	
	//POST - create a new dog in shelter
	public String postDog(DogDto dogdto) {
		Dog dog = new Dog(dogdto.getName(), dogdto.getBreed(), dogdto.getGender(), dogdto.getWeight());
		Shelter foundShelter = shelterRepo.findByName(dogdto.getShelterName());

		String name = dog.getName();
		
		//Check to see if there is already a dog with that name
		if(dogRepo.findByName(name) != null) {
			//Send error message!
			return "A dog with that name is already present in shelter!";
		} else if(foundShelter == null) {
			
			return "No warehouse with that name!";
		} else if(foundShelter.getCapacity() - 1 < 0) {
			sendEmail(foundShelter.getName() + " has no capacity left!");
			System.out.println(foundShelter.getName() + " has no capacity left!");
			return foundShelter.getName() + " has no capacity left!";

		} else {
			foundShelter.setCapacity(foundShelter.getCapacity() - 1);
			foundShelter.addDog(dog);
			
			shelterRepo.save(foundShelter);
			dogRepo.save(dog);	
			
			return "Saved " + dog.getName() + "!" ;
		}
	}
	
	//PUT - modify information on specific dog
	public String updateDog(Dog dog, int id) {
		Dog foundDog = dogRepo.findById(id);
		if(foundDog == null) {
			System.out.printf("Error, dog with id - %d not found!", id);
			return "Error in put route!";
		} else {
			foundDog.setName(dog.getName());
			foundDog.setBreed(dog.getBreed());
			foundDog.setWeight(dog.getWeight());
			foundDog.setGender(dog.getGender());
			dogRepo.save(foundDog);
			return "Modified " + dog.getName() + "!";
		}
	}
	
	//DELETE - delete a single dog entry
	public String deleteDog(int id) {
		Dog foundDog = dogRepo.findById(id);
		if(foundDog == null) {
			System.out.printf("Error, dog with id - %d not found!", id);
			return "Error in deleted route!";
		} else {
			String deletedDogName = foundDog.getName();
			dogRepo.deleteById(id);
			return "Deleted " + deletedDogName + "!";
		}
	}
	
	public void sendEmail(String msg) {

        final String username = "*************";
        final String password = "*************";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.abv.bg");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kakashi_m@abv.bg"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("ivelina.slaveva96@gmail.com, ilianlalov@gmail.com")
            );
            message.setSubject("Stock Application Messaging System");
            message.setText("This is an automated message:"
                    + "\n\n" + msg);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}