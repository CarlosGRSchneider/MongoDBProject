package com.carlosg.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosg.workshopmongo.domain.Post;
import com.carlosg.workshopmongo.repository.PostRepository;
import com.carlosg.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public Post findById( String id ) {
		Optional<Post> post = postRepository.findById(id);
		return post.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
	}

	public List<Post> searchTitle( String text) {
		return postRepository.findByTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date( maxDate.getTime() + (24 * 60 * 60 * 1000));
		
		return postRepository.fullSearch(text, minDate, maxDate);
	}
}



