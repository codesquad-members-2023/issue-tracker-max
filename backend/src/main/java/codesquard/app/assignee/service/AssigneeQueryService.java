package codesquard.app.assignee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.assignee.service.response.AssigneeReadServiceResponse;
import codesquard.app.user.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class AssigneeQueryService {

	private final UserRepository userRepository;

	public AssigneeQueryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<AssigneeReadServiceResponse> findAll() {
		return userRepository.findAll()
			.stream()
			.map(AssigneeReadServiceResponse::fromWithNoSelected)
			.collect(Collectors.toUnmodifiableList());
	}
}
