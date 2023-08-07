package com.issuetracker.config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.issuetracker.config.exception.QueryStringKeyNotMatchException;
import com.issuetracker.issue.ui.dto.IssueSearchRequest;

@Component
public class IssueSearchArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return IssueSearchRequest.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		List<String> issueSearchRequestVariableNames = Arrays.stream(IssueSearchRequest.class.getDeclaredFields())
			.map(Field::getName)
			.collect(Collectors.toUnmodifiableList());

		webRequest.getParameterMap()
			.keySet()
			.forEach(parameterName -> {
				if (issueSearchRequestVariableNames.stream()
					.noneMatch(variableName -> variableName.equals(parameterName))) {
					throw new QueryStringKeyNotMatchException();
				}
		});

		return new IssueSearchRequest(
			Boolean.valueOf(webRequest.getParameter("isOpen")),
			converterListLong(webRequest.getParameterValues("assigneeIds")),
			converterListLong(webRequest.getParameterValues("labelIds")),
			converterLong(webRequest.getParameter("milestoneId")),
			converterLong(webRequest.getParameter("authorId")),
			Boolean.valueOf(webRequest.getParameter("isCommentedByMe"))
		);
	}

	private List<Long> converterListLong(String[] strings) {
		if (strings == null || Arrays.stream(strings).allMatch(s -> s.equals(""))) {
			return Collections.emptyList();
		}

		return Arrays.stream(strings)
			.map(this::converterLong)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private Long converterLong(String parameter) {
		if (parameter == null || parameter.equals("")) {
			return null;
		}

		return Long.valueOf(parameter);
	}
}
