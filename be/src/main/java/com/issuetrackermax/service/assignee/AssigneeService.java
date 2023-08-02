package com.issuetrackermax.service.assignee;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.assignee.AssigneeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssigneeService {
	private final AssigneeRepository assigneeRepository;
}
