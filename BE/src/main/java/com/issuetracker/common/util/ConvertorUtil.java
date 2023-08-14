package com.issuetracker.common.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConvertorUtil {

	public static List<Long> converterToNonNullList(List<Long> ids) {
		if (Objects.isNull(ids)) {
			ids = Collections.emptyList();
		}

		return ids.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}
