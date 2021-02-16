package com.example.messagingservice.repository;

import com.example.messagingservice.model.Message;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MessageRepository extends MongoRepository<Message, String> {
	@Query("{'conversationId': ?0}")
	public List<Message> findAllByConversationId(String conversationId);
}
