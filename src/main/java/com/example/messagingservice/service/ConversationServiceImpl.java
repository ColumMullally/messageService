package com.example.messagingservice.service;

import com.example.messagingservice.model.Conversation;
import com.example.messagingservice.repository.ConversationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationServiceImpl implements ConversationService {
	@Autowired
	ConversationRepository conversationRepository;

	@Override
	public Conversation startConversation(String to, String from) {
		List<Conversation> conversationList = conversationRepository.findAllByParticipants(new String[] {to, from});
		if (conversationList.isEmpty()) {
			return conversationRepository.save(new Conversation(null, new String[] {to, from}));
		} else return conversationList.get(0);
	}

	@Override
	public Conversation getConversationById(String id) {
		Optional<Conversation> conversation = conversationRepository.findById(id);
		return conversation.get();
	}

	@Override
	public List<Conversation> getAllConversationForUser(String userId) {
		System.out.println("test"+userId);
		return conversationRepository.findAllByParticipants(new String[] {userId});
	}
}