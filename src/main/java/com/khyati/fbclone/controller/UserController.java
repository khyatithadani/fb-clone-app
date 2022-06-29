package com.khyati.fbclone.controller;

import com.khyati.fbclone.exception.ResourceNotFoundException;
import com.khyati.fbclone.model.User;
import com.khyati.fbclone.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
  @Autowired
  UserRepository userRepository;

  // Get all users
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) Boolean is_verified) {
    List<User> users = new ArrayList<User>();
    if (is_verified == null)
      userRepository.findAll().forEach(users::add);
    else
      userRepository.findByIsVerified(is_verified).forEach(users::add);
    if (users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  // Create new user
  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    User _user = userRepository
        .save(new User(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getEmail(),
            user.getMobile(), user.getProfilePicUrl(), false));
    return new ResponseEntity<>(_user, HttpStatus.CREATED);
  }

  // Get single user
  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  // Update user
  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
    User _user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
    _user.setFirstName(user.getFirstName());
    _user.setLastName(user.getLastName());
    _user.setEmail(user.getEmail());
    _user.setMobile(user.getMobile());
    _user.setVerified(user.isVerified());
    return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
  }

  // Delete user
  @DeleteMapping("/users/{id}")
  public ResponseEntity<HttpStatus> deleteAllUsers() {
    userRepository.deleteAll();

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // Delete user by id
  @DeleteMapping("/tutorials/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
    userRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
