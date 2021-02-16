package com.example.messagingservice.controller;

import com.example.messagingservice.dto.UserDTO;
import com.example.messagingservice.mapper.UserMapper;
import com.example.messagingservice.model.User;
import com.example.messagingservice.service.UserService;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
	//TODO Error handling for things like not found or bad request
	@Autowired
	UserMapper userMapper;

	private UserService userService;

	@Autowired public UserController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> userDTOList = userService.getAllUsers().stream()
			.map(user -> {
				try {
					return userMapper.toDTO(user);
				} catch (NoSuchAlgorithmException exception) {
					return null;
				}
			})
			.collect(Collectors.toList());
		if (userDTOList.contains(null)) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
	}

	@PostMapping("/user")
	ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		User user = userMapper.toEntity(userDTO);
		user = userService.createUser(user);
		try {
			userDTO = userMapper.toDTO(user);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}

	@GetMapping("/user/{uuid}")
	ResponseEntity<UserDTO> getUser(@PathVariable String uuid) {
		String objectId = userMapper.mapUUIDtoObjectID(uuid);
		try {
			User user = userService.getUserById(objectId);
			UserDTO userDTO = userMapper.toDTO(user);
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch (NoSuchElementException exception) {
			exception.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (NoSuchAlgorithmException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}