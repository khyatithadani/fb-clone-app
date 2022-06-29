package com.khyati.fbclone.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.khyati.fbclone.exception.ResourceNotFoundException;
import com.khyati.fbclone.model.Post;
import com.khyati.fbclone.model.Tag;
import com.khyati.fbclone.repository.PostRepository;
import com.khyati.fbclone.repository.TagRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TagController {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private TagRepository tagRepository;

  @GetMapping("/tags")
  public ResponseEntity<List<Tag>> getAllTags() {
    List<Tag> tags = new ArrayList<Tag>();
    tagRepository.findAll().forEach(tags::add);
    if (tags.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(tags, HttpStatus.OK);
  }

  @GetMapping("/posts/{post_Id}/tags")
  public ResponseEntity<List<Tag>> getAllTagsByPostId(@PathVariable(value = "post_Id") int post_Id) {
    if (!postRepository.existsById(post_Id)) {
      throw new ResourceNotFoundException("Not found post with id = " + post_Id);
    }
    List<Tag> tags = tagRepository.findTagsByPostsId(post_Id);
    return new ResponseEntity<>(tags, HttpStatus.OK);
  }

  @GetMapping("/tags/{id}")
  public ResponseEntity<Tag> getTagsById(@PathVariable(value = "id") Long id) {
    Tag tag = tagRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));
    return new ResponseEntity<>(tag, HttpStatus.OK);
  }

  @GetMapping("/tags/{tag_Id}/posts")
  public ResponseEntity<List<Post>> getAllPostsByTagId(@PathVariable(value = "tag_Id") Long tag_Id) {
    if (!tagRepository.existsById(tag_Id)) {
      throw new ResourceNotFoundException("Not found Tag  with id = " + tag_Id);
    }
    List<Post> posts = postRepository.findPostsByTagsId(tag_Id);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @PostMapping("/posts/{post_Id}/tags")
  public ResponseEntity<Tag> addTag(@PathVariable(value = "post_Id") int post_Id, @RequestBody Tag tagRequest) {
    Tag tag = postRepository.findById(post_Id).map(post -> {
      long tag_Id = tagRequest.getId();

      // tag is existed
      // if (tag_Id != 0L) {
      Tag _tag = tagRepository.findById(tag_Id)
          .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " +
              tag_Id));
      post.addTag(_tag);
      postRepository.save(post);
      return _tag;
      // }

      // add and create new Tag
      // post.addTag(tagRequest);
      // return tagRepository.save(tagRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found post with id = " + post_Id));
    return new ResponseEntity<>(tag, HttpStatus.CREATED);
  }

  @PutMapping("/tags/{id}")
  public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest) {
    Tag tag = tagRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Tag_Id " + id + "not found"));
    tag.setName(tagRequest.getName());
    return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
  }

  @DeleteMapping("/posts/{post_Id}/tags/{tag_Id}")
  public ResponseEntity<HttpStatus> deleteTagFrompost(@PathVariable(value = "post_Id") int post_Id,
      @PathVariable(value = "tag_Id") Long tag_Id) {
    Post post = postRepository.findById(post_Id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found post with id = " + post_Id));

    post.removeTag(tag_Id);
    postRepository.save(post);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/tags/{id}")
  public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
    tagRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
