package com.example.messagingservice.mapper;

import com.example.messagingservice.dto.MessageDTO;
import com.example.messagingservice.model.Conversation;
import com.example.messagingservice.model.Message;
import java.security.NoSuchAlgorithmException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper extends Mapper<Message, MessageDTO> {
	@Autowired
	ModelMapper modelMapper;

	@Override
	public Message toEntity(MessageDTO dto) {
		Message message = modelMapper.map(dto, Message.class);
		if (dto.getUuid() != null) {
			message.setId(mapUUIDtoObjectID(dto.getUuid()));
		}
		String[] splitUri = dto.getConversationURI().split("/");
		message.setConversationId(mapUUIDtoObjectID(splitUri[splitUri.length-1]));
		return message;
	}

	@Override
	public MessageDTO toDTO(Message model) throws NoSuchAlgorithmException {
		MessageDTO messageDTO = modelMapper.map(model, MessageDTO.class);
		messageDTO.setUuid(mapObjectIdToUUID(model.getClass().getName(), model.getId()));
		messageDTO.setUri("/message/" + messageDTO.getUuid());
		messageDTO.setConversationURI("/conversation/" + mapObjectIdToUUID(Conversation.class.getName(), model.getConversationId()));
		return messageDTO;
	}
}
