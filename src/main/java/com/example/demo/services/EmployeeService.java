package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Shelter;
import com.example.demo.repositories.EmployeeRepo;
import com.example.demo.repositories.ShelterRepo;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepo emplRepo;
	
	@Autowired
	ShelterRepo shelterRepo;
	
	//GET all employees
	public List<Employee> getAllEmployees() {
		return emplRepo.findAll();
	}
	 
	//POST a single employees
	public void postEmployee(EmployeeDto employeeDto) {
		Employee employee =  new Employee(employeeDto.getName(), employeeDto.getAge(), employeeDto.getSalary());
		Shelter foundShelter = shelterRepo.findByName(employeeDto.getShelterName());
		
		if(emplRepo.findByName(employee.getName()) != null) {
			System.out.println("An employee with that name is already present!");
		} else if(foundShelter == null) {	
			System.out.println("No shelter with that name!");
		} else {
			employee.setShelter(foundShelter);
			emplRepo.save(employee);
			System.out.println("Saved " + employee.getName() + "!" );
		}
	}
	
	//PUT a single employees
	public void updateEmployee (Employee employee, int id) {
		Employee foundEmployee = emplRepo.findById(id);
		if (foundEmployee != null) {
			foundEmployee.setName(employee.getName());
			foundEmployee.setAge(employee.getAge());
			foundEmployee.setSalary(employee.getSalary());
		}
		
	}
	
	//DELETE a employee
	public void deleteEmployee(int id) {
		Employee foundEmployee = emplRepo.findById(id);
		if(foundEmployee == null) {
			System.out.printf("Error, employee with id - %d not found!", id);

		} else {
			emplRepo.deleteById(id);
		}
	}
}
