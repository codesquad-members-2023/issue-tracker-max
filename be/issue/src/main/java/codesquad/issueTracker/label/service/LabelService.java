package codesquad.issueTracker.label.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {
	private final LabelRepository labelRepository;
}
