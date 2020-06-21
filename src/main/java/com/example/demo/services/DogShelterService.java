package com.example.demo.services;

import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Dog;
import com.example.demo.entities.Shelter;
import com.example.demo.repositories.DogRepo;
import com.example.demo.repositories.ShelterRepo;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Service
public class DogShelterService {

	@Autowired
	DogRepo dogRepo;
	
	@Autowired
	ShelterRepo shelterRepo;
	
	
	public List<Dog> getAllDogsFromShelter(int shelterId) {
		Shelter foundShelter = shelterRepo.findById(shelterId);
		if(foundShelter == null) {
			System.out.printf("Shelter with id - %d not found!", shelterId);
			return null;
		} else {
			System.out.printf("Dogs in shelter with id - %d: %s\n",shelterId, foundShelter);
			return shelterRepo.findById(shelterId).getDogs();
		}
	}
	
	public String postDogInShelter(int shelterId, Dog dog) {
		Shelter foundShelter = shelterRepo.findById(shelterId);
		if(foundShelter == null) {
			System.out.printf("Shelter with id - %d not found!", shelterId);
			return null;
		}  else if(foundShelter.getCapacity() - 1 < 0) {
			sendEmail(foundShelter.getName() + " has no capacity left!");
			System.out.println(foundShelter.getName() + " has no capacity left!");
			return "Didin't save " + dog.getName() + "!";
		} else {
			System.out.printf("Saving %s in shelter with id - %d\n", dog.getName(),shelterId);
			dog.setShelter(foundShelter);
			dogRepo.save(dog);
			foundShelter.addDog(dog);
			shelterRepo.save(foundShelter);
			return "Saved " + dog.getName() + "!";
		}
	}
	
	public String updateDogFromShelter(int shelterId, Dog dog, int dogId) {
		Shelter foundShelter = shelterRepo.findById(shelterId);
		if(foundShelter == null) {
			System.out.printf("Shelter with id - %d not found!", shelterId);
			return null;
		} else {
			Dog foundDog = dogRepo.findById(dogId);
			if(foundDog != null) {
				System.out.printf("Modifying %s in shelter with id - %d\n", dog.getName(),shelterId);
				foundDog.setName(dog.getName());
				foundDog.setBreed(dog.getBreed());
				foundDog.setWeight(dog.getWeight());
				foundDog.setGender(dog.getGender());
				dogRepo.save(foundDog);
				
				return "Modified " + foundDog.getName() + " in shelter: " + foundShelter.getName();
			}
			return "No dog with name: " + dog.getName() + " !";
		}
	}
	
	public String deleteDogFromShelter(int shelterId, int dogId) {
		Shelter foundShelter = shelterRepo.findById(shelterId);
		if(foundShelter == null) {
			System.out.printf("Shelter with id - %d not found!", shelterId);
			return null;
		} else {
			Dog foundDog = foundShelter.getDog(dogId);
			if(foundDog == null) return "No dog with id: " + dogId + " !"; 
			
			foundShelter.removeDog(dogId);
			foundShelter.setCapacity(foundShelter.getCapacity() + 1);
			
			dogRepo.deleteById(dogId);
			shelterRepo.save(foundShelter);
			
			return "Deleted " + foundDog.getName() + " in shelter: " + foundShelter.getName();
		}
	}

	public void sendEmail(String msg) {

        final String username = "kakashi_m@abv.bg";
        final String password = "lupinek_1317!@";

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
