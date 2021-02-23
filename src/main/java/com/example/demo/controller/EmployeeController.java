package com.example.demo.controller;

import java.util.Optional;

import com.example.demo.modele.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * Retourne tous les employés
     * @return
     */
    @GetMapping("/employees")
    public Iterable<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    /**
     * Retourne l'employé avec un id donné
     * @param id
     * @return employee ajouté
     */
    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") final Long id) {
        Optional<Employee> employee = employeeService.getEmployee(id);
        if(employee.isPresent()) {
            return employee.get();
        } else {
            return null;
        }
    }

    /**
     * Modifier un employé dont on connait l'id
     * @param id
     * @param employee
     * @return
     */
    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable("id") final Long id, @RequestBody Employee employee) {
        Optional<Employee> e = employeeService.getEmployee(id);

        if(e.isPresent()) {
            Employee currentEmployee = e.get();

            String firstName = employee.getFirstName();
            if(firstName != null) {
                currentEmployee.setFirstName(firstName);
            }

            String lastName = employee.getLastName();
            if(lastName != null) {
                currentEmployee.setLastName(lastName);
            }

            String email = employee.getMail();
            if(email != null) {
                currentEmployee.setMail(email);
            }

            String password = employee.getPassword();
            if(password != null) {
                currentEmployee.setPassword(password);
            }

            employeeService.saveEmployee(currentEmployee);
            return currentEmployee;
        } else {
            return null;
        }
    }

    /**
     * Ajouter un employé
     * @param employee
     * @return
     */
    @PostMapping("/employee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        if(! employee.equals(null)) {
           Employee savedEmployee = employee;
            employeeService.saveEmployee(savedEmployee);
            return savedEmployee;
        } else {
            System.out.println("Impossible d'inserer un employe null");
            return null;
        }
    }

    /**
     * Supprinmer un employée dont l'id est connu
     * @param id
     * @return
     */
    @DeleteMapping("/employee/delete/{id}")
    public Optional<Employee> deleteEmployee(@PathVariable("id") final Long id) {
        Optional<Employee> employee = employeeService.getEmployee(id);
        if (employee.isPresent()) {
            employeeService.deleteEmployee(id);
            return employee;
        } else {
            System.out.println("Employée non existant");
            return null;
        }
    }
    

    
}
