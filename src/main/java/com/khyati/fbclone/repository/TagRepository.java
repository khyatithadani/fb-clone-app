package com.khyati.fbclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import com.khyati.fbclone.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

  List<Tag> findTagsByPostsId(int postId);

}
