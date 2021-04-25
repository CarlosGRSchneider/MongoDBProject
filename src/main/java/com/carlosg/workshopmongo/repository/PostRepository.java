package com.carlosg.workshopmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carlosg.workshopmongo.domain.Post;

@Repository
public interface PostRepository  extends MongoRepository<Post, String>{

}
