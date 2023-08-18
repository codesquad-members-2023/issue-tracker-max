package codesquad.issueTracker.issue.service;

import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.issue.domain.Issue;
import codesquad.issueTracker.issue.domain.IssueRead;
import codesquad.issueTracker.issue.domain.IssueSearch;
import codesquad.issueTracker.issue.dto.*;
import codesquad.issueTracker.issue.dto.filter.IssueFilteredResponseDto;
import codesquad.issueTracker.issue.repository.IssueMapperRepository;
import codesquad.issueTracker.issue.repository.IssueRepository;
import codesquad.issueTracker.issue.vo.*;
import codesquad.issueTracker.label.dto.LabelResponseDto;
import codesquad.issueTracker.label.service.LabelService;
import codesquad.issueTracker.milestone.service.MilestoneService;
import codesquad.issueTracker.milestone.vo.MilestoneVo;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.service.UserService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class IssueService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final IssueRepository issueRepository;
    private final LabelService labelService;
    private final UserService userService;
    private final MilestoneService milestoneService;
    private final AmazonS3Client amazonS3Client;
    private final IssueMapperRepository issueMapperRepository;

    public IssueFilteredResponseDto findByFilter(IssueSearch issueSearch) {
        // 1. count 가져오는 로직도 하나 필요
        IssueCountResponseDto issueCountResponseDto = issueRepository.findIssueCounts();
        // 2. issueReads 가져오기
        List<IssueRead> issueReads = issueMapperRepository.findFilteredIssue(issueSearch);
        List<IssueFilteredVo> issueFilteredVo = IssueFilteredVo.of(issueReads);
        // response에서 1,2를 묶어주는 메서드 필요
        return IssueFilteredResponseDto.of(issueCountResponseDto, issueFilteredVo);
    }

    @Transactional
    public Long save(IssueWriteRequestDto request, Long id) {
        if (request.getMilestoneId() != null) {
            milestoneService.isExistMilestone(request.getMilestoneId());
        }
        List<Long> labels = request.getLabels();
        List<Long> assignees = request.getAssignees();
        Issue issue = IssueWriteRequestDto.toEntity(request, id);
        Long savedIssueId = issueRepository.insert(issue);

        // 라벨 리스트가 null 이 아니면 해당 라벨이 존재하는지 검증 후  라벨과 이슈 연결 테이블에 insert
        if (labels != null) {
            duplicatedId(labels);
            labels.stream()
                    .map(labelId -> labelService.validateLabelsId(labelId))
                    .map(existLabel -> issueRepository.insertLabels(savedIssueId, existLabel.getId()))
                    .collect(Collectors.toList());
        }

        // assignee 리스트가 null 이 아니면 assignees( 유저 id )가  존재하는지 검증 후  assignees 테이블에 insert
        if (assignees != null) {
            duplicatedId(assignees);
            assignees.stream()
                    .map(assigneesId -> userService.validateUserId(assigneesId))
                    .map(existUser -> issueRepository.insertAssignees(savedIssueId, existUser.getId()))
                    .collect(Collectors.toList());
        }
        return savedIssueId;
    }

    private void duplicatedId(List<Long> list) {
        Set<Long> set = new HashSet<>();
        for (Long temp : list) {
            if (!set.add(temp)) {
                throw new CustomException(ErrorCode.DUPLICATE_OBJECT_FOUND);
            }
        }
    }

    public IssueLabelResponseDto getIssueLabels() {
        LabelResponseDto allLabels = labelService.findAll();
        List<IssueLabelVo> labels = allLabels.getLabels().stream()
                .map(IssueLabelVo::from)
                .collect(Collectors.toList());
        return IssueLabelResponseDto.from(labels);
    }

    public IssueMilestoneResponseDto getIssueMilestones() {
        List<MilestoneVo> milestones = milestoneService.findMilestonesByStatus(Status.OPEN.getStatus());
        List<IssueMileStoneDetailVo> milestoneVos = milestones.stream()
                .map(IssueMileStoneDetailVo::from)
                .collect(Collectors.toList());
        return IssueMilestoneResponseDto.from(milestoneVos);
    }

    public IssueUserResponseDto getIssueUsers() {
        List<User> users = userService.getUsers();
        List<IssueUserVo> participants = users.stream()
                .map(IssueUserVo::from)
                .collect(Collectors.toList());
        return IssueUserResponseDto.from(participants);
    }

    public IssueResponseDto getIssueById(Long issueId) {
        validateExistIssue(issueId);
        Issue issue = validateActiveIssueById(issueId);
        User user = userService.validateUserId(issue.getUserId());
        return IssueResponseDto.from(issue, IssueUserVo.from(user));
    }

    private Issue validateActiveIssueById(Long issueId) {
        return issueRepository.findActiveIssueById(issueId)
                .orElseThrow(() -> new CustomException(ErrorCode.ALREADY_DELETED_ISSUE));
    }

    public IssueOptionResponseDto getIssueOptions(Long issueId) {
        validateExistIssue(issueId);
        validateActiveIssueById(issueId);

        List<AssigneeVo> assignees = issueRepository.findAssigneesById(issueId);
        List<IssueLabelVo> labels = labelService.findByIssueId(issueId);
        IssueMilestoneVo milestone = milestoneService.findByIssueId(issueId);

        if (milestone.getIssueMileStoneDetailVo().getId() != null) {
            int closeCount = issueRepository.findCountByStatusAndMilestone(Status.CLOSED.getStatus(), milestone);
            int openCount = issueRepository.findCountByStatusAndMilestone(Status.OPEN.getStatus(), milestone);
            return IssueOptionResponseDto.of(assignees, labels, milestone.getMilestoneWithRatio(openCount, closeCount));
        }

        return IssueOptionResponseDto.of(assignees, labels, milestone);
    }

    @Transactional
    public List<Long> modifyIssueStatus(ModifyIssueStatusRequestDto request) {
        List<Long> issueIds = request.getIssueIds();
        Boolean status = Status.from(request.getStatus()).getStatus();
        if (issueIds != null) {
            duplicatedId(issueIds);
            issueIds.stream()
                    .map(issueId -> validateExistActiveIssue(issueId))
                    .map(existIssue -> issueRepository.modifyStatus(existIssue.getId(), status))
                    .collect(Collectors.toList());
        }
        return issueIds;
    }

    @Transactional
    public Long modifyIssueStatusInDetail(Long id, ModifyIssueStatusRequestDto request) {
        Boolean status = Status.from(request.getStatus()).getStatus();
        validateExistActiveIssue(id);
        return issueRepository.modifyStatus(id, status);
    }

    private Issue validateExistActiveIssue(Long issuesIds) {
        return issueRepository.findActiveIssueById(issuesIds)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ISSUES));
    }

    private void validateExistIssue(Long issueId) {
        issueRepository.findById(issueId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ISSUE));
    }

    @Transactional
    public ModifyIssueContentResponseDto modifyIssueContent(Long id, ModifyIssueContentRequestDto request) {
        validateExistActiveIssue(id);
        String modifiedContent = request.getContent();
        issueRepository.updateContent(id, modifiedContent);
        return new ModifyIssueContentResponseDto(modifiedContent);
    }

    @Transactional
    public ModifyIssueTitleResponse modifyIssueTitle(Long id, ModifyIssueTitleRequest request) {
        validateExistActiveIssue(id);
        String modifiedTitle = request.getTitle();
        issueRepository.updateTitle(id, modifiedTitle);
        return new ModifyIssueTitleResponse(modifiedTitle);
    }

    @Transactional
    public Long delete(Long id) {
        validateExistActiveIssue(id);
        Long deletedId = issueRepository.delete(id);
        return deletedId;

    }

    @Transactional
    public Long modifyAssignees(Long id, ModifyAssigneeRequestDto request) {
        validateExistActiveIssue(id);
        List<Long> assignees = request.getAssignees();

        if (assignees != null) {
            duplicatedId(assignees);
            for (Long assigneeId : assignees) {
                userService.validateUserId(assigneeId);
            }
            issueRepository.resetAssignees(id);
            for (Long assigneeId : assignees) {
                issueRepository.insertAssignees(id, assigneeId);
            }
            return id;
        }
        issueRepository.resetAssignees(id);
        return id;
    }

    @Transactional
    public Long modifyLabels(Long id, ModifyLabelRequestDto request) {
        validateExistActiveIssue(id);
        List<Long> labels = request.getLabels();

        if (labels != null) {
            duplicatedId(labels);
            for (Long labelId : labels) {
                labelService.validateLabelsId(labelId);
            }
            labelService.resetLabels(id);
            for (Long labelId : labels) {
                labelService.insertIssuesLabels(id, labelId);
            }
            return id;
        }
        labelService.resetLabels(id);
        return id;
    }

    @Transactional
    public Long modifyMilestone(Long id, ModifyIssueMilestoneDto request) {
        validateExistActiveIssue(id);
        Long milestoneId = request.getMilestone();
        if (milestoneId != null) {
            milestoneService.isExistMilestone(milestoneId);
        }
        return issueRepository.updateMilestone(id, milestoneId);
    }

    public IssueFileResponseDto uploadImg(MultipartFile multipartFile) {
        validateFileExists(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FAILED_UPLOAD_FILE);
        }
        String url = amazonS3Client.getUrl(bucketName, fileName).toString();
        IssueFileResponseDto response = new IssueFileResponseDto(url);
        return response;
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }
    }
}
