package com.example.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.exception.ResourceNotFoundException;
import com.example.springboot.model.AppUser;
import com.example.springboot.repository.AppUserRepository;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	private AppUserRepository userRepo;
	
	// get users
	@GetMapping("users")
	public List<AppUser> getAllUser(){
		return this.userRepo.findAll();
	}
	
	// get user by id
	@GetMapping("/users/{id}")
	public ResponseEntity<AppUser> getUserById(@PathVariable(value = "id") Long userId) 
			throws ResourceNotFoundException{
		AppUser user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id:" + userId));
		return ResponseEntity.ok().body(user);
	}
	
	// save user
	@PostMapping("users")
	public AppUser createUser(@RequestBody AppUser user) {
		return this.userRepo.save(user);
	}
	
	// update user
	@PutMapping("/users/{id}")
	public ResponseEntity<AppUser> updateUser(@PathVariable(value = "id") Long userId, 
			@Valid @RequestBody AppUser userDetails) throws ResourceNotFoundException {
		AppUser user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id:" + userId));
		user.setCode(userDetails.getCode());
		user.setDescription(userDetails.getDescription());
		return ResponseEntity.ok(this.userRepo.save(user));
	}
	
	// delete user
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) 
			throws ResourceNotFoundException{
		AppUser user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id:" + userId));
		this.userRepo.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
