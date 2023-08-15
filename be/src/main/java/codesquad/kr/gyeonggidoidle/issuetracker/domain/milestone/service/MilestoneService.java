package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.MilestoneRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition.MilestoneCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.condition.MilestoneUpdateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestonePageInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.MilestoneStatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final StatRepository statRepository;
    private final MilestoneRepository milestoneRepository;

    public MilestonePageInformation readOpenMilestonePage() {
        MilestoneStatVO milestoneStatVO = statRepository.countMilestoneStats();
        List<MilestoneDetailsVO> milestoneDetailsVOs = milestoneRepository.findOpenMilestones();
        List<Long> milestoneIds = getMilestoneIds(milestoneDetailsVOs);
        Map<Long, IssueByMilestoneVO> issueByMilestoneVOs = statRepository.countIssuesByMilestoneIds(
                milestoneIds);
        return MilestonePageInformation.from(milestoneStatVO, milestoneDetailsVOs, issueByMilestoneVOs);
    }

    public MilestonePageInformation readClosedMilestonePage() {
        MilestoneStatVO milestoneStatVO = statRepository.countMilestoneStats();
        List<MilestoneDetailsVO> milestoneDetailsVOs = milestoneRepository.findClosedMilestones();
        List<Long> milestoneIds = getMilestoneIds(milestoneDetailsVOs);
        Map<Long, IssueByMilestoneVO> issueByMilestoneVOs = statRepository.countIssuesByMilestoneIds(
                milestoneIds);
        return MilestonePageInformation.from(milestoneStatVO, milestoneDetailsVOs, issueByMilestoneVOs);
    }

    public boolean create(MilestoneCreateCondition condition) {
        return milestoneRepository.save(MilestoneCreateCondition.to(condition));
    }

    public boolean update(MilestoneUpdateCondition condition) {
        return milestoneRepository.update(MilestoneUpdateCondition.to(condition));
    }

    public boolean delete(Long milestoneId) {
        return milestoneRepository.delete(milestoneId);
    }

    public boolean updateStatus(Long milestoneId, boolean isOpen) {
        return milestoneRepository.updateStatus(milestoneId, isOpen);
    }

    private List<Long> getMilestoneIds(List<MilestoneDetailsVO> milestoneDetailsVOs) {
        return milestoneDetailsVOs.stream()
                .map(MilestoneDetailsVO::getId)
                .collect(Collectors.toUnmodifiableList());
    }
}
