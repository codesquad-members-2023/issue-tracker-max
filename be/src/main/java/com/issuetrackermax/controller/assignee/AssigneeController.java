package com.issuetrackermax.controller.assignee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.service.assignee.AssigneeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AssigneeController {
	private final AssigneeService assigneeService;
}
