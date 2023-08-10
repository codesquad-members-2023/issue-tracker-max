package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.request.LabelRequest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.response.LabelDetailsResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller.response.LabelPageResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LabelController {

    private final LabelService labelService;

    @GetMapping("/api/labels")
    public LabelPageResponse readLabelPage() {
        return LabelPageResponse.from(labelService.readLabelPage());
    }

    @PostMapping("/api/labels")
    public ApiResponse create(@RequestBody LabelRequest request) {
        labelService.create(LabelRequest.to(request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @GetMapping("/api/labels/{labelId}")
    public LabelDetailsResponse read(@PathVariable Long labelId) {
        return LabelDetailsResponse.from(labelService.read(labelId));
    }

    @PatchMapping("/api/labels/{labelId}")
    public ApiResponse update(@PathVariable Long labelId, @RequestBody LabelRequest request) {
        labelService.update(LabelRequest.to(labelId, request));
        return ApiResponse.success(HttpStatus.OK);
    }

    @DeleteMapping("/api/labels/{labelId}")
    public ApiResponse delete(@PathVariable Long labelId) {
        labelService.delete(labelId);
        return ApiResponse.success(HttpStatus.OK);
    }
}
