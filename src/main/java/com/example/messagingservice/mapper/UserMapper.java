package com.example.messagingservice.mapper;

import com.example.messagingservice.dto.UserDTO;
import com.example.messagingservice.model.User;
import java.security.NoSuchAlgorithmException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends Mapper<User, UserDTO> {
	@Autowired
	ModelMapper modelMapper;

	@Override
	public User toEntity(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		if (userDTO.getUuid() != null) {
			user.setId(mapUUIDtoObjectID(userDTO.getUuid()));
		}
		return user;
	}

	@Override
	public UserDTO toDTO(User model) throws NoSuchAlgorithmException {
		UserDTO userDTO = modelMapper.map(model, UserDTO.class);
		userDTO.setUuid(mapObjectIdToUUID(model.getClass().getName(), model.getId()));
		userDTO.setUri("/user/" + userDTO.getUuid());
		return userDTO;
	}
}
