package com.example.messagingservice.mapper;

import com.example.messagingservice.dto.BaseDTO;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public abstract class Mapper<T,E extends BaseDTO> {
	abstract public T toEntity(E dto);
	abstract public E toDTO(T model) throws NoSuchAlgorithmException;

	 public String mapObjectIdToUUID(String resource,String objectId) throws NoSuchAlgorithmException {
			 MessageDigest hasher = MessageDigest.getInstance("SHA-1");
			 hasher.reset();
			 hasher.update(("messagingService" + "/" + resource).getBytes(StandardCharsets.UTF_8));

			 String resourceHash = byteToHex(hasher.digest()).substring(0, 6);

			 String timestamp = objectId.substring(0, 8);
			 String machine = objectId.substring(8, 14);
			 String pid = objectId.substring(14, 18);
			 String increment = objectId.substring(18);

			 String uuid = resourceHash + machine + pid + "70" + timestamp + increment;
			 return uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
	 }

	public String mapUUIDtoObjectID(String uuid){
		String strippedUuid = uuid.replaceAll("-", "");

		String timestamp = strippedUuid.substring(18, 26);
		String machine = strippedUuid.substring(6, 12);
		String pid = strippedUuid.substring(12, 16);
		String increment = strippedUuid.substring(26);

		return (timestamp + machine + pid + increment);
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
