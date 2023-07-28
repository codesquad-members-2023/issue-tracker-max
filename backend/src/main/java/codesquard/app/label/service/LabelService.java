package codesquard.app.label.service;

import org.springframework.stereotype.Service;

import codesquard.app.label.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {
	
	private final LabelRepository labelRepository;
}
