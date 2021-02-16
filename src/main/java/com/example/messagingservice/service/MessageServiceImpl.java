package com.example.messagingservice.service;

import com.example.messagingservice.model.Message;
import com.example.messagingservice.repository.MessageRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
	@Autowired
	MessageRepository messageRepository;
	@Override
	public Message sendMessage(String conversationId, String messageContent) {
		Message message = new Message();
		message.setMessageContent(messageContent);
		message.setConversationId(conversationId);
		message.setLike(false);
		return messageRepository.save(message);
	}

	@Override
	public Message updateMessage(Message updatedMessage) {
		return messageRepository.save(updatedMessage);
	}

	@Override
	public boolean deleteMessageById(String id) {
		messageRepository.deleteById(id);
		return true;
	}

	@Override
	public Message getMessageById(String id)  throws NoSuchElementException {
		Optional<Message> message = messageRepository.findById(id);
		return message.get();
	}

	@Override
	public List<Message> getAllMessagesByConversationId(String conversationId) {
		return messageRepository.findAllByConversationId(conversationId);
	}
}
