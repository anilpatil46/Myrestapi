package com.example.demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

	@GetMapping()
	public String getUsers() {
		return "HTTPS GET request was sent";
		}
	@PostMapping()
	public String creatUser() {
		return "HTTPS POST request was sent";
		}
	@DeleteMapping()
	public String deleteUser() {
		return "HTTPS DELETE request was sent";
		}
	@PutMapping()
	public String updateUser() {
		return "HTTPS PUT request was sent";
		}

}
