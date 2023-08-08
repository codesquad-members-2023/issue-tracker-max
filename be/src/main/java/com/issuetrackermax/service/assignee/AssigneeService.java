package com.issuetrackermax.service.assignee;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.assignee.AssigneeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssigneeService {
	private final AssigneeRepository assigneeRepository;

	@Transactional(readOnly = true)
	public Boolean existByIds(List<Long> ids) {
		return assigneeRepository.existByIds(ids);
	}
}
