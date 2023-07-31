package com.issuetrackermax.controller.assignee;

import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.service.assignee.AssigneeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AssigneeController {
	private final AssigneeService assigneeService;
}
