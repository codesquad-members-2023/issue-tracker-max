package com.issuetrackermax.config;

import static com.issuetrackermax.domain.issue.IssueStatus.*;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.issuetrackermax.controller.history.dto.HistoryRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssuePostResponse;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.service.history.HistoryService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class HistoryAspect {
	private final HistoryService historyService;
	private final MemberRepository memberRepository;

	@Around("execution(* com.issuetrackermax.service.issue.IssueService.post(..)) && args(request,writerId)")
	public Object save(ProceedingJoinPoint joinPoint, IssuePostRequest request, Long writerId) throws Throwable {
		IssuePostResponse issuePostResponse = (IssuePostResponse)joinPoint.proceed();
		Long issueId = issuePostResponse.getId();

		HistoryRequest request1 = HistoryRequest.builder()
			.issueId(issueId)
			.editor(memberRepository.findById(writerId).getNickName())
			.issueIsOpen(true)
			.build();

		historyService.save(request1);
		return issuePostResponse;
	}

	@Around("execution(* com.issuetrackermax.service.issue.IssueService.updateStatus(..)) && args(request, memberId)")
	public void changeStatus(ProceedingJoinPoint joinPoint, IssuesStatusRequest request, Long memberId) throws
		Throwable {
		joinPoint.proceed();
		List<Long> ids = request.getIssueIds();
		String status = request.getIssueStatus();
		String editor = memberRepository.findById(memberId).getNickName();
		ids.forEach(id -> historyService.save(
			HistoryRequest.builder()
				.issueId(id)
				.editor(editor)
				.issueIsOpen(status.equals(OPEN_ISSUE.getStatus()))
				.build()));
	}

}
