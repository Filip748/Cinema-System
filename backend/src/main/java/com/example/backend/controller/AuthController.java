package com.example.backend.controller;

import com.example.backend.dto.AuthRequest;
import com.example.backend.model.Employee;
import com.example.backend.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmployeeRepository employeeRepository;

    public AuthController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {

        String password = request.getPassword();
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

        if(password == null || !password.matches(passwordRegex)) {
            return ResponseEntity.badRequest().body("Password needs 8 signs, small and big letter and digit.");
        }

        if(employeeRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Login already exists.");
        }

        Employee newEmployee = new Employee();
        newEmployee.setUsername(request.getUsername());
        newEmployee.setPassword(request.getPassword()); // Dodac tu szyfrowanie Bcrypt
        newEmployee.setRole("ROLE_EMPLOYEE");
        employeeRepository.save(newEmployee);

        return ResponseEntity.ok("Register done.");
    }

    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(request.getUsername());

        if(employeeOptional.isPresent() && employeeOptional.get().getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok("Login succes.");
        }

        return ResponseEntity.status(401).body("Incorrect Login or Password");
    }
}
