package com.issuetrackermax.config;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.issuetrackermax.controller.history.dto.HistoryRequest;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.service.history.HistoryService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class HistoryAspect {
	private final HistoryService historyService;
	private final MemberRepository memberRepository;

	@Around("execution(* com.issuetrackermax.service.issue.IssueService.post(..))")
	public Object save(ProceedingJoinPoint joinPoint) throws Throwable {
		Long issueId = (Long)joinPoint.proceed();
		Long writerId = (Long)joinPoint.getArgs()[1];

		historyService.save(HistoryRequest.builder()
			.issueId(issueId)
			.editor(memberRepository.findById(writerId).get().getNickName())
			.issueIsOpen(true)
			.build());
		return issueId;
	}

	@Around("execution(* com.issuetrackermax.service.issue.IssueService.openIssue(..))")
	public void changeStatusOpen(ProceedingJoinPoint joinPoint) throws
		Throwable {
		joinPoint.proceed();
		List<Long> ids = (List<Long>)joinPoint.getArgs()[0];
		Long memberId = (Long)joinPoint.getArgs()[1];
		String editor = memberRepository.findById(memberId).get().getNickName();
		for (Long id : ids) {
			historyService.save(HistoryRequest.builder()
				.issueId(id)
				.editor(editor)
				.issueIsOpen(true)
				.build());
		}
	}

	@Around("execution(* com.issuetrackermax.service.issue.IssueService.closeIssue(..))")
	public void changeStatusClosed(ProceedingJoinPoint joinPoint) throws
		Throwable {
		joinPoint.proceed();
		List<Long> ids = (List<Long>)joinPoint.getArgs()[0];
		Long memberId = (Long)joinPoint.getArgs()[1];
		String editor = memberRepository.findById(memberId).get().getNickName();
		for (Long id : ids) {
			historyService.save(HistoryRequest.builder()
				.issueId(id)
				.editor(editor)
				.issueIsOpen(false)
				.build());
		}
	}
	
}
