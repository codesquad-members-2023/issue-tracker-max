package com.issuetrackermax.service.history;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.history.HistoryRepository;
import com.issuetrackermax.domain.history.entity.History;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HistoryService {
	private final HistoryRepository historyRepository;

	public History findLatestByIssueId(Long issueId) {
		return historyRepository.findLatestByIssueId(issueId);
	}
}
