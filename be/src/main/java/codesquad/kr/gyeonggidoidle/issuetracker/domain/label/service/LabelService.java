package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition.LabelCreateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.condition.LabelUpdateCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelDetailsInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelPageInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LabelService {

    private final StatRepository statRepository;
    private final LabelRepository labelRepository;

    public LabelPageInformation readLabelPage() {
        StatVO statVO = statRepository.countLabelStats();
        List<LabelDetailsVO> labelDetailsVOs = labelRepository.findAll();
        return LabelPageInformation.from(statVO, labelDetailsVOs);
    }

    public boolean create(LabelCreateCondition condition) {
        return labelRepository.save(LabelCreateCondition.to(condition));
    }

    public LabelDetailsInformation read(Long labelId) {
        return LabelDetailsInformation.from(labelRepository.findById(labelId));
    }

    public boolean update(LabelUpdateCondition condition) {
        return labelRepository.update(LabelUpdateCondition.to(condition));
    }

    public boolean delete(Long labelId) {
        return labelRepository.delete(labelId);
    }
}
