package com.example.messagingservice.service;

import com.example.messagingservice.model.Conversation;
import java.util.List;

public interface ConversationService {
	public Conversation startConversation(String to, String from);

	public List<Conversation> getAllConversationForUser(String userId);

	public Conversation getConversationById(String id);
}
