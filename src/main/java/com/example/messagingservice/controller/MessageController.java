package com.example.messagingservice.controller;

import com.example.messagingservice.dto.MessageDTO;
import com.example.messagingservice.mapper.MessageMapper;
import com.example.messagingservice.model.Message;
import com.example.messagingservice.model.User;
import com.example.messagingservice.service.ConversationService;
import com.example.messagingservice.service.MessageService;
import com.example.messagingservice.service.UserService;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	//TODO Error handling for things like not found or bad request
	@Autowired
	UserService userService;
	@Autowired
	ConversationService conversationService;
	@Autowired
	MessageService messageService;
	@Autowired
	MessageMapper messageMapper;

	@PostMapping("/user/{userIdFrom}/message")
	public ResponseEntity<MessageDTO> createMessage(@PathVariable String userIdFrom, @RequestBody Map<String,String> body) {
		try {
			System.out.println(userIdFrom);
			System.out.println(body.keySet());
			User userFrom = userService.getUserById(messageMapper.mapUUIDtoObjectID(userIdFrom));
			User userTo = userService.getUserById(messageMapper.mapUUIDtoObjectID(body.get("to")));
			return new ResponseEntity<MessageDTO>(messageMapper.toDTO(messageService.sendMessage(conversationService.startConversation(userTo.getId(), userFrom.getId()).getId(), body.get("message"))), HttpStatus.CREATED);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoSuchElementException exception) {
			exception.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/message/{uuid}")
	public ResponseEntity<String> deleteMessage(@PathVariable String uuid) {
		messageService.deleteMessageById(messageMapper.mapUUIDtoObjectID(uuid));
		return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
	}

	@PostMapping("/message/{uuid}/like")
	public ResponseEntity<MessageDTO> likeMessage(@PathVariable String uuid) {
		Message message = messageService.getMessageById(messageMapper.mapUUIDtoObjectID(uuid));
		message.setLike(!message.getLike());

		try {
			return new ResponseEntity<>(messageMapper.toDTO(messageService.updateMessage(message)), HttpStatus.OK);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/message/{uuid}")
	public ResponseEntity<MessageDTO> editMessage(@PathVariable String uuid, @RequestBody  Map<String,String> body) {
		Message message = messageService.getMessageById(messageMapper.mapUUIDtoObjectID(uuid));
		message.setMessageContent(body.get("message"));
		try {
			return new ResponseEntity<>(messageMapper.toDTO(messageService.updateMessage(message)), HttpStatus.OK);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/message/{uuid}")
	public ResponseEntity<MessageDTO> getMessageById(@PathVariable String uuid) {
		try {
			return new ResponseEntity<>(messageMapper.toDTO(messageService.getMessageById(messageMapper.mapUUIDtoObjectID(uuid))), HttpStatus.OK);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/messages")
	public ResponseEntity<List<MessageDTO>> getALlMessagesbyConversationId(@RequestParam String conversationId) {
		List<Message> messageList = messageService.getAllMessagesByConversationId(messageMapper.mapUUIDtoObjectID(conversationId));
		List<MessageDTO> messageDTOList = messageList.stream()
			.map(message -> {
				try {
					System.out.println(message);
					return messageMapper.toDTO(message);
				} catch (NoSuchAlgorithmException exception) {
					return null;
				}
			})
			.collect(Collectors.toList());
		if (messageDTOList.contains(null)) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(messageDTOList, HttpStatus.OK);
	}
}
