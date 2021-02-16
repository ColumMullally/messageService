package com.example.messagingservice.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.messagingservice.dto.UserDTO;
import com.example.messagingservice.model.User;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {
	@Autowired
	private UserMapper mapper;
	//TODO Mock id mapping methods
	private final String uuid = "f0186016-5cb6-db79-705d-971ba9edcebb";
	private final String objectId = "5d971ba9165cb6db79edcebb";
	private final String email = "timmy@timmy.com";
	private final String name = "timmy";
	private final String uri = "/user/" + uuid;

	@Test
	void toEntity() {
		UserDTO userDTO = new UserDTO(name, email);
		userDTO.setUuid(uuid);
		userDTO.setUri(uri);
		User result = mapper.toEntity(userDTO);
		assertThat(result.getId()).isEqualTo(objectId);
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getEmail()).isEqualTo(email);
	}

	@Test
	void toDTO() {
		User user = new User(objectId, name, email);
		try {
			System.out.println(mapper);
			System.out.println(user);
			UserDTO result = mapper.toDTO(user);
			assertThat(result.getUuid()).isEqualTo(uuid);
			assertThat(result.getUri()).isEqualTo(uri);
			assertThat(result.getName()).isEqualTo(name);
			assertThat(result.getEmail()).isEqualTo(email);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}

	}
}