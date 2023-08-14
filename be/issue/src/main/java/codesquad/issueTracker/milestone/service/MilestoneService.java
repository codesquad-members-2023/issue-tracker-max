package codesquad.issueTracker.milestone.service;

import codesquad.issueTracker.issue.vo.IssueMilestoneVo;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.milestone.domain.Milestone;
import codesquad.issueTracker.milestone.dto.MileStoneStatusDto;
import codesquad.issueTracker.milestone.dto.MilestoneResponseDto;
import codesquad.issueTracker.milestone.dto.ModifyMilestoneRequestDto;
import codesquad.issueTracker.milestone.repository.MilestoneRepository;
import codesquad.issueTracker.milestone.vo.MilestoneVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MilestoneService {

	private final Log log = LogFactory.getLog(MilestoneService.class);

	private final MilestoneRepository milestoneRepository;

	@Transactional
	public Long save(ModifyMilestoneRequestDto request) {
		Milestone milestone = ModifyMilestoneRequestDto.toEntity(request);
		milestone.validateDate();
		return milestoneRepository.insert(milestone);
	}

	@Transactional
	public Long update(Long id, ModifyMilestoneRequestDto request) {
		existValidate(id);
		Milestone milestone = ModifyMilestoneRequestDto.toEntity(request);
		milestone.validateDate();
		return milestoneRepository.update(id, milestone);
	}

	@Transactional
	public Long delete(Long id) {
		existValidate(id);
		return milestoneRepository.delete(id);
	}

	@Transactional
	public Long updateStatus(Long id, MileStoneStatusDto request) {
		existValidate(id);
		Boolean status = Status.from(request.getStatus()).getStatus();
		return milestoneRepository.updateStatus(id, status);
	}

	private void existValidate(Long id) {
		if (!milestoneRepository.isExist(id)) {
			throw new CustomException(ErrorCode.NOT_FOUND_MILESTONE);
		}
	}

	public MilestoneResponseDto findAll(MileStoneStatusDto request) {
		Boolean status = Status.from(request.getStatus()).getStatus();
		List<MilestoneVo> milestones = findMilestonesByStatus(status);
		int labelCount = milestoneRepository.getLabelCount();
		int anotherCount = milestoneRepository.getAnotherCount(!status);
		MilestoneResponseDto milestoneResponseDto = new MilestoneResponseDto(labelCount, anotherCount, milestones);
		return milestoneResponseDto;
	}

	public void isExistMilestone(Long id) {
		if (!milestoneRepository.isExist(id)) {
			throw new CustomException(ErrorCode.NOT_FOUND_MILESTONE);
		}
	}

	public List<MilestoneVo> findMilestonesByStatus(Boolean status) {
		return milestoneRepository.findAll(status);
	}

	public IssueMilestoneVo findByIssueId(Long issueId) {
		MilestoneVo milestoneVo = milestoneRepository.findByIssueId(issueId)
				.orElseGet(() -> MilestoneVo.builder().build());
		return IssueMilestoneVo.from(milestoneVo);
	}
}
