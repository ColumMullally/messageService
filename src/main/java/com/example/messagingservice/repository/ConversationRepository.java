package com.example.messagingservice.repository;

import com.example.messagingservice.model.Conversation;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
	@Query(value = "{ 'participants':{$in:?0}}")
	public List<Conversation> findAllByParticipants(String[] participant);
}
