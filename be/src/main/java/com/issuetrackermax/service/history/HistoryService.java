package com.issuetrackermax.service.history;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.history.dto.HistoryRequest;
import com.issuetrackermax.domain.history.HistoryRepository;
import com.issuetrackermax.domain.history.entity.History;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HistoryService {
	private final HistoryRepository historyRepository;

	@Transactional
	public Long save(HistoryRequest historyRequest) {
		return historyRepository.save(History.from(historyRequest));
	}

	public History findLatestByIssueId(Long issueId) {
		return historyRepository.findLatestByIssueId(issueId);
	}
}
