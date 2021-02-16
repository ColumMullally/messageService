package com.example.messagingservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

class MapperTest {

	Mapper mapper = new UserMapper();

	private final String uuid = "a99ab016-5cb6-db79-705d-971ba9edcebb";
	private final String objectId = "5d971ba9165cb6db79edcebb";
	private final String uuidPattern = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";

	@Test void objectIdToValidUuidSuccess() {
		String result = null;
		try {
			result = mapper.mapObjectIdToUUID("test", objectId);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		assertThat(result).matches(uuidPattern).isEqualTo(uuid);
	}

	@Test void givenADifferentResourceUuidNotEqual() {
		String result = null;
		try {
			result = mapper.mapObjectIdToUUID("not-test", objectId);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		assertThat(result).matches(uuidPattern).isNotEqualTo(uuid);
	}

	@Test void uuidToValidObjectIdSuccess() {
		String result = mapper.mapUUIDtoObjectID(uuid);
		assertThat(result).isEqualTo(objectId);
	}

	@Test void mapObjectIdToUUIDAndBackSuccess() throws NoSuchAlgorithmException {
		String generatedObjectID = new ObjectId().toString();
		String uuid = mapper.mapObjectIdToUUID("test", generatedObjectID);
		assertThat(uuid).matches(uuidPattern);
		String result = mapper.mapUUIDtoObjectID(uuid);
		assertThat(result).isEqualTo(generatedObjectID);
		String uuidResult = mapper.mapObjectIdToUUID("test", result);
		assertThat(uuid).isEqualTo(uuidResult);
	}
}