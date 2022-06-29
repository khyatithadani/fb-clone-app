package com.khyati.fbclone.repository;

import com.khyati.fbclone.model.Post;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  List<Post> findByUserId(int user_id);

  List<Post> findByText(String text);

  @Transactional
  void deleteByUserId(int user_id);

  List<Post> findPostsByTagsId(Long tagId);
}
