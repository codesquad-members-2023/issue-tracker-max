package com.issuetrackermax.service.hitsory;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.history.HistoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HistoryService {
	private final HistoryRepository historyRepository;
}
