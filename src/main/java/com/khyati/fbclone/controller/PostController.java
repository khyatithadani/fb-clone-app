package com.khyati.fbclone.controller;

import java.util.ArrayList;
import java.util.List;

import com.khyati.fbclone.exception.ResourceNotFoundException;
import com.khyati.fbclone.model.Post;
import com.khyati.fbclone.repository.PostRepository;
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

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  public UserRepository userRepository;

  @Autowired
  public PostRepository postRepository;

  @GetMapping("/posts")
  public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String text) {
    List<Post> posts = new ArrayList<Post>();
    if (text == null)
      postRepository.findAll().forEach(posts::add);
    else
      postRepository.findByText(text).forEach(posts::add);
    if (posts.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/users/{user_Id}/posts")
  public ResponseEntity<List<Post>> getAllPostsByUserId(@PathVariable(value = "user_Id") int user_Id) {
    if (!userRepository.existsById(user_Id)) {
      throw new ResourceNotFoundException("Not found user with id = " + user_Id);
    }
    List<Post> posts = postRepository.findByUserId(user_Id);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/posts/{Id}")
  public ResponseEntity<Post> getPostById(@PathVariable(value = "Id") int Id) {
    Post posts = postRepository.findById(Id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + Id));
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @PostMapping("/posts")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    Post _post = postRepository.save(new Post(post.getText()));
    return new ResponseEntity<>(_post, HttpStatus.CREATED);
  }

  @PostMapping("/users/{user_Id}/posts")
  public ResponseEntity<Post> createPost(@PathVariable(value = "user_Id") int user_Id,
      @RequestBody Post postRequest) {
    Post post = userRepository.findById(user_Id).map(user -> {
      postRequest.setUser(user);
      return postRepository.save(postRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + user_Id));
    return new ResponseEntity<>(post, HttpStatus.CREATED);
  }

  @PutMapping("/posts/{Id}")
  public ResponseEntity<Post> updatePost(@PathVariable(value = "Id") int Id, @RequestBody Post postRequest) {
    Post post = postRepository.findById(Id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + Id));
    post.setText(postRequest.getText());
    return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
  }

  @DeleteMapping("/posts")
  public ResponseEntity<HttpStatus> deleteAllPosts() {
    postRepository.deleteAll();

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/posts/{Id}")
  public ResponseEntity<HttpStatus> deletePost(@PathVariable(value = "Id") int Id) {
    postRepository.deleteById(Id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/users/{user_Id}/posts")
  public ResponseEntity<List<Post>> deleteAllPostsByUserId(@PathVariable(value = "user_Id") int user_Id) {
    if (!userRepository.existsById(user_Id)) {
      throw new ResourceNotFoundException("Not found user with id = " + user_Id);
    }
    postRepository.deleteByUserId(user_Id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
