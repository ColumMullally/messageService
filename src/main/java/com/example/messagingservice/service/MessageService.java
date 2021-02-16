package com.example.messagingservice.service;

import com.example.messagingservice.model.Message;
import java.util.List;

public interface MessageService {
	public Message sendMessage(String conversationId, String message);

	public Message updateMessage(Message updatedMessage);

	public boolean deleteMessageById(String id);

	public Message getMessageById(String id);

	public List<Message> getAllMessagesByConversationId(String conversationId);

}
