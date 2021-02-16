package com.example.messagingservice.mapper;

import com.example.messagingservice.dto.ConversationDTO;
import com.example.messagingservice.model.Conversation;
import com.example.messagingservice.model.User;
import java.security.NoSuchAlgorithmException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapper extends Mapper<Conversation, ConversationDTO> {
	@Autowired
	ModelMapper modelMapper;

	@Override
	public Conversation toEntity(ConversationDTO dto) {
		Conversation conversation = modelMapper.map(dto, Conversation.class);
		if (dto.getUuid() != null) {
			conversation.setId(mapUUIDtoObjectID(dto.getUuid()));
		}
		String[] participantsUri = dto.getParticipants();
		String[] splitUser1Uri = participantsUri[0].split("/");
		String[] splitUser2Uri = participantsUri[1].split("/");
		String user1Id = mapUUIDtoObjectID(splitUser1Uri[splitUser1Uri.length - 1]);
		String user2Id = mapUUIDtoObjectID(splitUser2Uri[splitUser2Uri.length - 1]);
		conversation.setParticipants(new String[] {user1Id, user2Id});
		return conversation;
	}

	@Override
	public ConversationDTO toDTO(Conversation model) throws NoSuchAlgorithmException {
		ConversationDTO conversationDTO = modelMapper.map(model, ConversationDTO.class);
		conversationDTO.setUuid(mapObjectIdToUUID(model.getClass().getName(), model.getId()));
		conversationDTO.setUri("/conversation/" + conversationDTO.getUuid());
		String[] participantsUri = model.getParticipants();
		String user1Id = "/user/" + mapObjectIdToUUID(User.class.getName(),participantsUri[0]);
		String user2Id = "/user/" + mapObjectIdToUUID(User.class.getName(),participantsUri[1]);
		conversationDTO.setParticipants(new String[] {user1Id, user2Id});
		return conversationDTO;
	}
}
