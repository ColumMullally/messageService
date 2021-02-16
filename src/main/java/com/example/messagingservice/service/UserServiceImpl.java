package com.example.messagingservice.service;

import com.example.messagingservice.model.User;
import com.example.messagingservice.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	//TODO @Cacheable
	@Override
	public User getUserById(String id) throws NoSuchElementException {
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}
}
