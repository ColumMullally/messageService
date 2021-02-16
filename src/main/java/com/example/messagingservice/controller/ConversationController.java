package com.example.messagingservice.controller;

import com.example.messagingservice.dto.ConversationDTO;
import com.example.messagingservice.mapper.ConversationMapper;
import com.example.messagingservice.model.Conversation;
import com.example.messagingservice.service.ConversationService;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConversationController {
	//TODO Error handling for things like not found or bad request
	@Autowired
	ConversationService conversationService;

	@Autowired
	ConversationMapper conversationMapper;

	@GetMapping("/conversation/{uuid}")
	public ResponseEntity<ConversationDTO> getConversationById(@PathVariable String uuid){
		Conversation conversation =conversationService.getConversationById(conversationMapper.mapUUIDtoObjectID(uuid));
		try {
			return new ResponseEntity<>(conversationMapper.toDTO(conversation),HttpStatus.OK);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/conversations")
	public ResponseEntity<List<ConversationDTO>> getAllConversationByUser(@RequestParam String user){
		System.out.println(user);
		List<Conversation> conversationList =conversationService.getAllConversationForUser(conversationMapper.mapUUIDtoObjectID(user));
		List<ConversationDTO> conversationDTOList = conversationList.stream()
			.map(conversation -> {
				try {
					System.out.println(conversation);
					return conversationMapper.toDTO(conversation);
				} catch (NoSuchAlgorithmException exception) {
					return null;
				}
			})
			.collect(Collectors.toList());
		if (conversationDTOList.contains(null)) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(conversationDTOList,HttpStatus.OK);

	}
}
