package com.issuetracker.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;

public class ConvertorUtil {

	public static List<Long> converterToNonNullList(List<Long> ids) {
		if (Objects.isNull(ids)) {
			ids = Collections.emptyList();
		}

		return ids.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}

	public static List<String> converterToNotBlankList(String[] strings) {
		if (Objects.isNull(strings)) {
			return Collections.emptyList();
		}

		return Arrays.stream(strings)
			.filter(Strings::isNotBlank)
			.collect(Collectors.toList());
	}
}
