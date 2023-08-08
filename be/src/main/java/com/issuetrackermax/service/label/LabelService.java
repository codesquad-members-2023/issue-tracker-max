package com.issuetrackermax.service.label;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.label.dto.request.LabelModifyRequest;
import com.issuetrackermax.controller.label.dto.request.LabelPostRequest;
import com.issuetrackermax.controller.label.dto.response.LabelDetailResponse;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.label.entity.Label;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {
	private final LabelRepository labelRepository;

	public List<LabelDetailResponse> getLabelList() {
		List<Label> labels = labelRepository.getLabels();
		return labels.stream().map(LabelDetailResponse::from).collect(Collectors.toList());
	}

	@Transactional
	public Long save(LabelPostRequest labelPostRequest) {
		return labelRepository.save(Label.from(labelPostRequest));
	}

	@Transactional
	public void update(Long id, LabelModifyRequest labelModifyRequest) {
		labelRepository.update(id, Label.from(labelModifyRequest));
	}

	@Transactional
	public void delete(Long id) {
		int count = labelRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Label findById(Long id) {
		return labelRepository.findById(id);
	}

	public Boolean existByIds(List<Long> ids) {
		return labelRepository.existByIds(ids);
	}
}
