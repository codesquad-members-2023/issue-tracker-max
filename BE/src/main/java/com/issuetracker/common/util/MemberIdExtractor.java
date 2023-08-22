package com.issuetracker.common.util;

import javax.servlet.http.HttpServletRequest;

public class MemberIdExtractor {

	public static Long extractMemberId(HttpServletRequest request) {
		return Long.valueOf(String.valueOf(request.getAttribute("memberId")));
	}
}
