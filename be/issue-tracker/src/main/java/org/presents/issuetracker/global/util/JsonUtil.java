package org.presents.issuetracker.global.util;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String toStr(Map<String, Object> map) {
		try {
			return objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static Map<String, Object> toMap(String jsonStr) {
		try {
			return objectMapper.readValue(jsonStr, LinkedHashMap.class);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
