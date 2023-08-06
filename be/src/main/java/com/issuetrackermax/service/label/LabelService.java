package com.issuetrackermax.service.label;

import org.springframework.stereotype.Service;

import com.issuetrackermax.domain.label.LabelRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {
	private final LabelRepository labelRepository;

}
