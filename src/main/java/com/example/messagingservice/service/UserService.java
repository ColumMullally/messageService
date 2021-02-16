package com.example.messagingservice.service;

import com.example.messagingservice.model.User;
import java.util.List;
import java.util.NoSuchElementException;

public interface UserService {
	public User createUser(User user);

	public List<User> getAllUsers();

	public User getUserById(String id) throws NoSuchElementException;
}
