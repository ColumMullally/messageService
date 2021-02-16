package com.example.messagingservice.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.example.messagingservice.dto.MessageDTO;
import com.example.messagingservice.model.Message;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageMapperTest {
	@Autowired
	MessageMapper mapper;
	//TODO Mock id mapping methods
	private final String uuid = "f765a216-5cb6-db79-705d-971ba9edcebb";
	private final String conversationURI = "/conversation/95942a16-5cb6-db79-705d-971ba9edcebb";
	private final String objectId = "5d971ba9165cb6db79edcebb";
	private final String messageContent = "test message";
	private final boolean like = true;
	private final String uri = "/message/" + uuid;

	@Test
	void toEntity() {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setUuid(uuid);
		messageDTO.setUri(uri);
		messageDTO.setMessageContent(messageContent);
		messageDTO.setLike(like);
		messageDTO.setConversationURI(conversationURI);
		Message result = mapper.toEntity(messageDTO);
		assertThat(result.getId()).isEqualTo(objectId);
		assertThat(result.getConversationId()).isEqualTo(objectId);
		assertThat(result.getMessageContent()).isEqualTo(messageContent);
		assertThat(result.getLike()).isEqualTo(like);
	}

	@Test
	void toDTO() {
		Message message = new Message(objectId, objectId, messageContent, like);
		try {
			MessageDTO result = mapper.toDTO(message);
			assertThat(result.getUuid()).isEqualTo(uuid);
			assertThat(result.getUri()).isEqualTo(uri);
			assertThat(result.getConversationURI()).isEqualTo(conversationURI);
			assertThat(result.getMessageContent()).isEqualTo(messageContent);
			assertThat(result.getLike()).isEqualTo(like);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}

}