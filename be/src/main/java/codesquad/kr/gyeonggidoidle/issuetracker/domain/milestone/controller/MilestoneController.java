package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request.MilestoneRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.request.MilestoneStatusRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller.response.MilestonePageResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.MilestoneService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping("/api/milestones/open")
    public MilestonePageResponse readOpenMilestones() {
        return MilestonePageResponse.from(milestoneService.readOpenMilestonePage());
    }

    @GetMapping("/api/milestones/closed")
    public MilestonePageResponse readClosedMilestones() {
        return MilestonePageResponse.from(milestoneService.readClosedMilestonePage());
    }

    @PostMapping("/api/milestones")
    public ApiResponse create(@RequestBody MilestoneRequest request) {
        milestoneService.create(MilestoneRequest.to(request));
        return ApiResponse.success(HttpStatus.OK);
    }
    @PutMapping("/api/milestones/{milestoneId}")
    public ApiResponse update(@PathVariable Long milestoneId, @RequestBody @Valid MilestoneRequest request) {
        milestoneService.update(MilestoneRequest.to(milestoneId, request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @DeleteMapping("/api/milestones/{milestoneId}")
    public ApiResponse delete(@PathVariable Long milestoneId) {
        milestoneService.delete(milestoneId);
        return ApiResponse.success(HttpStatus.OK);
    }

    @PatchMapping("/api/milestones/{milestoneId}")
    public ApiResponse updateStatus(@PathVariable Long milestoneId, @RequestBody @Valid MilestoneStatusRequest request) {
        milestoneService.updateStatus(milestoneId, request.isOpen());
        return ApiResponse.success(HttpStatus.OK);
    }
}
