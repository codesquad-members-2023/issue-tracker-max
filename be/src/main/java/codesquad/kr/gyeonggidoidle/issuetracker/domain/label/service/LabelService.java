package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
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
}
