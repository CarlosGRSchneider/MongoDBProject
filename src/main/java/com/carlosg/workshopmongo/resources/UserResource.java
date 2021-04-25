package com.carlosg.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlosg.workshopmongo.domain.Post;
import com.carlosg.workshopmongo.domain.User;
import com.carlosg.workshopmongo.dto.UserDTO;
import com.carlosg.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> allUsers = service.findAll();
		List<UserDTO> allUsersDTO = allUsers.stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
		return ResponseEntity.ok().body(allUsersDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User thisUser = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(thisUser));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody UserDTO userDto) {
		User thisUser = service.fromDTO(userDto);
		thisUser = service.insert(thisUser);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(thisUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserDTO userDto, @PathVariable String id) {
		User thisUser = service.fromDTO(userDto);
		thisUser.setId(id);
		thisUser = service.update(thisUser);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(thisUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody String id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User thisUser = service.findById(id);
		return ResponseEntity.ok().body(thisUser.getPosts());
	}

}
